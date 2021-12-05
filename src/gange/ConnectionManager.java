package gange;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
//import java.util.LinkedList;

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
			String sqlDelete = "DELETE FROM CLIENT WHERE email=?";
			PreparedStatement stmtDelete = this.connection.prepareStatement(sqlDelete);
			stmtDelete.setString(1, email);
			// Execution de la requete
			stmtDelete.executeUpdate();
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
}
	