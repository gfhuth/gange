package gange;

import java.sql.*;
import java.util.LinkedList;
//import java.util.LinkedList;
import java.util.Scanner;

public class ConnectionManager {

	static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
	static final String user = "blockmel"; // A remplacer pour votre compte, sinon genere une exception
	static final String password = "blockmel";
	public Connection connection;

	public ConnectionManager() {
		try {
			// Enregistrement du driver Oracle
			System.out.print("Loading Oracle driver... "); 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("loaded");
			// Etablissement de la connection
			System.out.print("Connecting to the database... "); 
			this.connection = DriverManager.getConnection(CONN_URL, user, password);
			System.out.println("connected");

		}catch (SQLException e){
			System.err.println("Connection failed");
			e.printStackTrace(System.err);
		}  
	}

	public void close() {
		if(this.connection != null){
			try{
				this.connection.close();
			} catch (SQLException e){/*we can ignore this exception*/
				System.err.println("failed");
				e.printStackTrace(System.err);
			}

		}

	}

	public ResultSet exec(String s) {
		try {
			// Creation de la requete
			Statement stmt = this.connection.createStatement();
			// Execution de la requete
			ResultSet rset = stmt.executeQuery(s);
			//			stmt.close();
			return rset;

		} catch (SQLException e) {
			System.err.println("failed");
			e.printStackTrace();
		}

		return null;
	}

	public ResultSet execSetString(String s, LinkedList<String> args) {
		try {
			PreparedStatement pstmt = this.connection.prepareStatement(s);
			
			for(int i = 1; i <= args.size(); i++){
				pstmt.setString(i, args.get(i-1)); // ieme parametre
			}
			
			return pstmt.executeQuery();

		} catch (SQLException e) {
			System.err.println("failed");
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultSet execSetString(String s, String arg) {
		try {
			PreparedStatement pstmt = this.connection.prepareStatement(s);
				pstmt.setString(1, arg); // 1eme parametre
			return pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.err.println("failed");
			e.printStackTrace();
		}
		return null;
	}

	public boolean verifyEmail(String email){
		try{
			
			ResultSet rset = execSetString(
				"select email from  client where email = ?",
				email);

			return rset.next();
		}catch(Exception e){
			return false;
		}
	}

	public boolean verifyPassword(String email, String password){
		try{
			LinkedList<String> credentials = new LinkedList<String>(); 
			credentials.add(email);
			credentials.add(password);
			ResultSet rset = execSetString(
				"select prenom from client where email = ? and mot_de_passe = ?",
				credentials);
			return rset.next();
		}catch(Exception e){
			System.err.println("Le mot de passe n'est pas correct");
			return true;
		}
	}

	/**
	 * Cette fonction sert à envoyer la requete pour éliminer une ligne
	 * Je peux pas réutiliser tes fonctions parceque on utilise executeUpdate et pas Query.
	 * 
	 * @param email
	 * @return
	 */
	public boolean delClient(String email){
		try{
			int idUtilisateur = retrieveUserID(email);
			//On élimine les données
			String sqlDelete = "DELETE FROM CLIENT WHERE email=?";
			PreparedStatement stmtDelete = this.connection.prepareStatement(sqlDelete);
			stmtDelete.setString(1, email);
			// Execution de la requete
			stmtDelete.executeUpdate();

			//On recupère un nouveau id
			String fetchId = "SELECT MAX(id_u) FROM UTILISATEUR";
			ResultSet rSet = exec(fetchId);
			int newIdUtilisateur = rSet.next()?rSet.getInt(1)+1:idUtilisateur;

			//On intègre le nouveau id dans utilisateur
			String sqlAddID = "INSERT INTO UTILISATEUR VALUES(?)";
			PreparedStatement stmtAddId = this.connection.prepareStatement(sqlAddID);
			stmtAddId.setInt(1, newIdUtilisateur);
			stmtAddId.executeUpdate();

			//On modifie les id pour ttes les offres
			String sqlChangeId = "UPDATE Offre SET id_u = ? WHERE Offre.id_u = ?";
			PreparedStatement stmtChangeId = this.connection.prepareStatement(sqlChangeId);
			stmtChangeId.setInt(1, newIdUtilisateur);
			stmtChangeId.setInt(2, idUtilisateur);
			stmtChangeId.executeUpdate();

			System.out.println(idUtilisateur);
			System.out.println(newIdUtilisateur);
			return true;
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Cette fonction permet de rajouter un Client à la base de données à partir de ses coordonnées
	 * Pour choisir un id utilisateur on recupère la valeur la plus haute et on rajoute 1
	 * 
	 * TODO: On devrait éliminer la nouvelle id si on n'arrive pas à créer le Client
	 * 
	 * @param coords
	 * @return
	 */
	public boolean createClient(LinkedList<String> coords){
		try{
			//On recupère la nouvelle ID Client, en cherche l'id le plus élevé précédent et on rajoute 1
			String fetchId = "SELECT MAX(id_u) FROM UTILISATEUR";
			ResultSet rSet = exec(fetchId);
			int id_u = rSet.next()?rSet.getInt(1)+1:1; //Si il y a des id_u on ajoute 1 sinon c la premiere
			System.out.println(id_u);

			//Création de l'id_u dans utilisateur
			String sqlAddID = "INSERT INTO UTILISATEUR VALUES(?)";
			PreparedStatement stmtAddId = this.connection.prepareStatement(sqlAddID);
			stmtAddId.setInt(1, id_u);
			stmtAddId.executeUpdate();

			//Création de la requete
			final String SQL_INSERT = "INSERT INTO CLIENT VALUES (?,?,?,?,?,?)";
			PreparedStatement stmtAddClient = this.connection.prepareStatement(SQL_INSERT);
			stmtAddClient.setString(1, coords.get(0));
			stmtAddClient.setInt(2, id_u);
			for (int i = 1; i < 5; i++){
				stmtAddClient.setString(i+2, coords.get(i));
			}
			// Execution de la requete
			stmtAddClient.executeUpdate();
			return true;
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Cette première fonction s'occupe uniquement de la création d'une nouvelle offre
	 * Elle ne fait ni vérification, ni récupération de données
	 * Cela sera fait par une fonction enchères de ConnectionManager qui arrive ensuite
	 * 
	 * @param idProd
	 * @param prix
	 * @param idUtilisateur
	 * @param numOffre
	 * @return
	 */
	public boolean newOffer(int idProd, float prix, int idUtilisateur, int numOffre){
		try{
			//On commence par récupérer la date et l'heure
			String Date = java.time.LocalDate.now().toString();
			String Time = java.time.LocalTime.now().toString();
			//Ensuite on crée notre requete de création
			String sqlNewOffer = "INSERT INTO OFFRE VALUES(?,?,?,?,?,?) ";
			PreparedStatement stmtAddOffer = this.connection.prepareStatement(sqlNewOffer);
			stmtAddOffer.setInt(1, idProd);
			stmtAddOffer.setString(2, Date);
			stmtAddOffer.setString(3, Time);
			stmtAddOffer.setInt(4, idUtilisateur);
			stmtAddOffer.setFloat(5, prix);
			stmtAddOffer.setInt(6, numOffre);
			// Execution de la requete
			stmtAddOffer.executeUpdate();
			return true;
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Cette fonction sert à modifier le prix d'un produit
	 * Tout comme avant elle ne s'occupe pas de faire des vérifications
	 * @param idProd
	 * @param Prix
	 * @return
	 */
	public boolean updateProduct(int idProd, float Prix){
		String sqlUpdate = "SELECT PRIX_COURANT FROM PRODUIT WHERE ID_P =" + idProd;
		//On crée un Statement spécial qui permet de faire des modifications a la BDD (normalment on est en lecture seule)
		try(Statement stmtUpdate = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)){
			// Execution de la requete
			ResultSet rset = stmtUpdate.executeQuery(sqlUpdate);
			while (rset.next()){
				rset.updateFloat("PRIX_COURANT", Prix);
				rset.updateRow();
			}
			return true;
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Fonction très simple qui execute une requete pour avoir l'id_u à partir du mail d'un utilisateur
	 * Dans l'éventualité où il y en a plusieurs, elle rendra le premier. Mais on devrait pas avoir plusieurs.
	 * @param email
	 * @return
	 */
	public int retrieveUserID(String email){
		try{
			String sqlRetrieve = "SELECT ID_U FROM CLIENT WHERE EMAIL=?";
			PreparedStatement stmtRetrieve = this.connection.prepareStatement(sqlRetrieve);
			stmtRetrieve.setString(1, email);
			// Execution de la requete
			ResultSet rset = stmtRetrieve.executeQuery();
			int userId = 0; //Si on a 0 on sait qu'il y a une erreur. Pourtant cela ne devrait pas être possible
							//En effet, l'email a été vérifié au login.
			while(rset.next()){
				userId = rset.getInt("ID_U");
			}
			return userId;
		}catch (SQLException e){
			e.printStackTrace();
			return 10000;
		}
	}

	/**
	 * Fonction qui recupère le numéro d'offre de la dernière offre faite sur un produit.
	 * @param idProd
	 * @return
	 */
	public int retrieveOfferNumber(int idProd){
		try{
			String sqlRetrieve = "SELECT MAX(NUM_OFFRE) FROM OFFRE WHERE ID_P=?";
			PreparedStatement stmtRetrieve = this.connection.prepareStatement(sqlRetrieve);
			stmtRetrieve.setInt(1, idProd);
			// Execution de la requete
			ResultSet rset = stmtRetrieve.executeQuery();
			int numOffre = rset.next()? rset.getInt(1):0;
			return numOffre;
		}catch (SQLException e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Fonction qui vérifie si le prix proposé est supérieur au prix actuel.
	 * Retourne true si c'est supérieur.
	 * @param idProd
	 * @param prix
	 * @return
	 */
	public boolean verifyPrix(int idProd, float prix){
		try{
			//On recupère le prix
			String sqlVerifyPrix = "SELECT PRIX_COURANT FROM PRODUIT WHERE ID_P="+idProd;
			ResultSet rset = exec(sqlVerifyPrix);
			if (rset.next()){//On vérifie qu'il y a eu un retour à la requete
				float prixCourant = rset.getFloat(1);
				if(prix>prixCourant){
					return true;
				}
				System.out.println("Votre offre doit être supérieur à :"+prixCourant);
				return false;
			}
			System.out.println("Le produit n'existe pas");
			return false;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * Fonction qui permet de faire une enchère
	 * Il crée une nouvelle offre si elle remplit les conditions
	 * Puis elle fait l'update du prix courant du produit
	 * @param idProd
	 * @param email
	 * @return
	 */
	public boolean makeBid(int idProd, String email){
		try{
			//On recupère l'Id Utilisateur du Client
			int idUtilisateur = retrieveUserID(email);

			//On commence par vérifier que le Client veut faire l'enchère
			String sqlGetProdName = "SELECT Intitulé FROM PRODUIT WHERE ID_P = "+idProd;
			ResultSet rsetNomProduit = exec(sqlGetProdName);
			rsetNomProduit.next();
			String NomProduit = rsetNomProduit.getString("Intitulé");
			Scanner scan = new Scanner(System.in); 
			System.out.println("Êtes vous sur de vouloir enchérir "+NomProduit+"?[oui/non]");
			String confirmation = scan.next();

			if(confirmation.equals("oui")){
				//Ensuite on vérifie que l'enchère est faisable
				int numOffre = retrieveOfferNumber(idProd);

				if (numOffre<0 || numOffre>=5){
					System.out.println("Le produit n'est plus disponible");
					scan.close();
					return false;
				}else{
					//Finalment on vérifie que le prix offert par le Client est validé
					System.out.println("Rentrez le prix que vous voulez payer, prix :");
					float prix = scan.nextFloat();

					while (!verifyPrix(idProd, prix)){
						System.out.println("Rentrez votre nouvelle offre :");
						prix = scan.nextFloat();
					}
					//Tout s'est bien passé, donc on crée l'offre et on update le produit
					newOffer(idProd, prix, idUtilisateur, numOffre+1);
					updateProduct(idProd, prix);
					if (numOffre+1 == 5){
						System.out.println("Félicitations ! Vous avez gagné le produit "+NomProduit);
					}
				}
			}else{
				System.out.println("L'enchère a été annulée");
				scan.close();
				return false;
			}
			scan.close();
			return true;
		}catch (SQLException e){
			e.printStackTrace();
			return false;
		}

	}

	public LinkedList<String> getProductInfo(int idProd){
		try{
			//On crée une Liste pour stocker les infos
			LinkedList<String> infosProduit = new LinkedList<String>();
			//On recupère les infos basiques du produit
			String sqlInfos = "SELECT INTITULÉ, PRIX_COURANT, DESCRIPTION FROM PRODUIT WHERE ID_P = "+idProd;
			ResultSet rsetInfos = exec(sqlInfos);
			while (rsetInfos.next()){
				infosProduit.add(rsetInfos.getString("INTITULÉ"));
				infosProduit.add((String.valueOf(rsetInfos.getFloat("PRIX_COURANT"))));
				infosProduit.add(rsetInfos.getString("DESCRIPTION"));
			}
			//Ensuite on recupère les données
			String sqlDonnees = "SELECT CARACTERISTIQUE,VALEUR FROM DONNEES WHERE ID_P = "+idProd;
			ResultSet rsetDonnees = exec(sqlDonnees);
			while (rsetDonnees.next()){
				String ligneDeCaract = rsetDonnees.getString("CARACTERISTIQUE") + "="+ rsetDonnees.getString("VALEUR");
				infosProduit.add(ligneDeCaract);
			}
			System.out.print(infosProduit);
			return infosProduit;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public LinkedList<String> getRecommended(String email){
		try{
		//On recupère le idUtilisateur
		int idUtilisateur = retrieveUserID(email);
		LinkedList<String> Recommends = new LinkedList<String>();
		//On récupère les catégories recommandées
		String sqlRecommends = "SELECT id_u, nom_cat, COUNT(nom_cat) as nb FROM PRODUIT INNER JOIN OFFRE on Produit.ID_P = OFFRE.ID_P Where offre.id_u = "+idUtilisateur+" GROUP BY nom_cat, id_u ORDER BY nb DESC, nom_cat";
		ResultSet rsetInfos = exec(sqlRecommends);
		while(rsetInfos.next()){
			Recommends.add(rsetInfos.getString(2));
		}
		//On recupere les recommendations generales
		String sqlRecommends2 = "SELECT nom_cat, COUNT(nom_cat) as nb FROM PRODUIT INNER JOIN OFFRE on Produit.ID_P = OFFRE.ID_P GROUP BY nom_cat ORDER BY nb DESC, nom_cat";
		ResultSet rsetInfos2 = exec(sqlRecommends2);
		while(rsetInfos2.next()){
			if(!Recommends.contains(rsetInfos2.getString(1))){
			Recommends.add(rsetInfos2.getString(1));
			}
		}
		return Recommends;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	public LinkedList<String> getNextCategory(String nomCat){
		try{
			LinkedList<String> Categories = new LinkedList<String>();
			//On récupère les catégories recommandées
			String sqlCategory = "SELECT NOM_CAT FROM CATEGORIE WHERE CAT_MERE ="+"'"+nomCat+"'";
			ResultSet rsetCategory = exec(sqlCategory);
			while(rsetCategory.next()){
				Categories.add(rsetCategory.getString(1));
			}
			return Categories;
		}catch(SQLException e){
				e.printStackTrace();
				return null;
		}
	}

	public LinkedList<String> getProductsFromCategory(String nomCat){
		try{
			LinkedList<String> Products = new LinkedList<String>();
			//On récupère les catégories recommandées
			String sqlProduct = "SELECT INTITULÉ, URL_PHOTO, PRIX_COURANT, ID_P FROM PRODUIT WHERE ID_P IN (SELECT id_p FROM OFFRE HAVING COUNT(num_offre) < 5 GROUP BY id_p ) AND NOM_CAT ="+"'"+nomCat+"'";
			ResultSet rsetProduct = exec(sqlProduct);
			while(rsetProduct.next()){
				Products.add(rsetProduct.getString(1));
				Products.add(rsetProduct.getString(2));
				Products.add((String.valueOf(rsetProduct.getFloat(3))));
				Products.add((String.valueOf(rsetProduct.getInt(4))));
			}
			return Products;
		}catch(SQLException e){
				e.printStackTrace();
				return null;
		}
	}

	public LinkedList<String> getAnyCategory(){
		try{
		LinkedList<String> Recommends = new LinkedList<String>();
		//On recupere les recommendations generales
		String sqlRecommends2 = "SELECT nom_cat, COUNT(nom_cat) as nb FROM PRODUIT INNER JOIN OFFRE on Produit.ID_P = OFFRE.ID_P GROUP BY nom_cat ORDER BY nb DESC, nom_cat";
		ResultSet rsetInfos2 = exec(sqlRecommends2);
		while(rsetInfos2.next()){
			Recommends.add(rsetInfos2.getString(1));
		}
		return Recommends;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}


	
}
