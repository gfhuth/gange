package gange;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Menu {
	ConnectionManager conn;
	Scanner scan = new Scanner(System.in);
	public Menu(ConnectionManager conn) {
		this.conn = conn;
	}


	public int login(User user){
		gange("Bienvenu dans Gange vos enchères de confiance"); 

		System.out.print("e-mail: ");   
		String email = scan.next();
		//verify if email exist
		//if doesnt exist ask again to user to put a valid email
		int errorCount = 0;
		int quitFlag = 0 ;
		while(!this.conn.verifyEmail(email)){
			if (errorCount >= 2){
				System.out.println("Voulez-vous fermer l'application ?");
				System.out.println("\t\t\t     Continuer.............[1]");
				System.out.println("\t\t\t     Fermer................[2]");
				if (scan.nextInt() == 2) {
					quitFlag = 1;
					break;
				}
			}
			errorCount++;
			System.out.println("S'il vous plait rentrez une adresse mail valide:");
			System.out.print("e-mail: "); 
			email = scan.next();
		}
		if (quitFlag == 0) {
			System.out.print("password: ");
			String password = scan.next();
			errorCount = 0;
			while(!this.conn.verifyPassword(email, password)){
				if (errorCount >= 2){
					System.out.println("Voulez-vous fermer l'application ?");
					System.out.println("\t\t\t     Continuer.............[1]");
					System.out.println("\t\t\t     Fermer................[2]");
					if (scan.nextInt() == 2) {
						quitFlag = 1;
						break;
					}
				}
				errorCount++;
				System.out.println("S'il vous plait rentrez un autre mot de passe:");
				System.out.print("password: ");
				password = scan.next();
			}
		}
		else {}
		user.setEmail(email);
		return 1 - quitFlag; //	invert quitFlag
	}

	public void close() {
		scan.close();
		this.conn.close();
	}

	public void clear() {
		final String os = System.getProperty("os.name");
		if (os.contains("Windows")){
			try {
				Runtime.getRuntime().exec("cls");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.print("\033\143");	
			System.out.flush();
		}
	}

	public void header(String string){
		int tam = (80-string.length())/2;
		for(int i = 0; i < tam; i++)
			System.out.print("=");
		System.out.print("| " + string + " |");
		for(int i = 0; i < tam; i++)
			System.out.print("=");
		System.out.println();
	}

	public void gange(String s) {
		clear();
		System.out.println("\t\t   ████████                                    ");
		System.out.println("\t\t  ██░░░░░░██                     █████         ");
		System.out.println("\t\t ██      ░░   ██████   ███████  ██░░░██  █████ ");
		System.out.println("\t\t░██          ░░░░░░██ ░░██░░░██░██  ░██ ██░░░██");
		System.out.println("\t\t░██    █████  ███████  ░██  ░██░░██████░███████");
		System.out.println("\t\t░░██  ░░░░██ ██░░░░██  ░██  ░██ ░░░░░██░██░░░░ ");
		System.out.println("\t\t ░░████████ ░░████████ ███  ░██  █████ ░░██████");
		System.out.println("\t\t  ░░░░░░░░   ░░░░░░░░ ░░░   ░░  ░░░░░   ░░░░░░ ");
		System.out.println();
		header(s);
	}

	/**
	 * Cette fonction va servir à gérer un Client qui veut éliminer son compte
	 * 
	 * TODO : Il serait intéréssant de stocker le mail du client lors du login,parceque ça n'as pas trop de sens de le redemander.
	 * 
	 * @param c
	 * @param email
	 */
	public void eliminerClient(String email) {
		System.out.print("Bonjour, êtes vous sur de vouloir éliminer votre compte? [oui/non]");   
		String confirmation = scan.next();
		System.out.println(confirmation);
		if(confirmation.equals("oui")){
			if(conn.delClient(email)){
				System.out.println("Votre compte a ete elimine. On va vous déconnecter");
			}else{
				System.out.println("Il y a eu une erreur. On n'as pas pu vous éliminer.");
			}	
		}else{
			System.out.println("On reviens vers votre page de recommandations");
			System.out.println("Si c'est ps ce que vous vouliez,veuillez reéssayer en écrivant oui en toutes lettres");
		}

	}

	/**
	 * Fonction qui permet de créer un compte en demandant les informations au client
	 * 
	 * TODO : Il serait intéressant de vérifier les coords fournies. Vérification de si un compte existe déjà
	 * Et vérification que les coords sont logiques
	 * 
	 * @param c
	 */
	public void creationCompte(){
		LinkedList<String> coord = new LinkedList<String>();
		//On recupère les coords
		System.out.print("On va procéder à la création d'un compte veuillez répondre aux questions suivantes\n");
		System.out.println("Email:");
		String email=scan.next();
		coord.add(email);
		System.out.println("Nom:");
		String nom=scan.nextLine();
		coord.add(nom);
		System.out.println("Prenom:");
		String prenom=scan.nextLine();
		coord.add(prenom);
		System.out.println("Adresse:");
		String adresse=scan.nextLine();
		coord.add(adresse);
		System.out.println("Mot de passe:");
		String motDePasse=scan.nextLine();
		coord.add(motDePasse);

		//On execute la demande de création
		if(this.conn.createClient(coord)){
			System.out.println("Votre compte a été crée correctement. Maintenant vous êtes connecté");
		}else{
			System.out.println("Une erreur est survenue, le compte n'as pas été créé");
		}

		//On ferme le scan

	}

	/**
	 * Fonction pour lancer une nouvelle enchère.
	 * À adapter selon son intégration avec le reste du système
	 * Pour moi les enchères devraient être faites au sein de la page produit
	 * @param id_p
	 * @param email
	 */
	public void faireEnchere(int id_p, String email){
		this.conn.makeBid(id_p, email);
	}

	public int accuiel() {
		int option;
		do {

			gange("Souhaitez-vous recevoir des suggestions basées sur vos antécédents ?"); 
			System.out.println();
			System.out.println("\t\t\t     Oui...................[1]");
			System.out.println("\t\t\t     Non...................[2]");
			System.out.println("\t\t\t     Supprimer ma compte ..[3]");
			System.out.println("\t\t\t     Quitter...............[0]");
			System.out.println();
			option = scan.nextInt();
		}while(option < 0 || option > 3);

		return option;
	}



	public int listProducts(String s){
		ResultSet rset = conn.exec(s);
		int option;
		gange("votre meilleur choix");
		ResultSetMetaData rsetmd;
		try {
			rsetmd = rset.getMetaData();

			System.out.print("\t\t" + 
					rsetmd.getColumnName(1) + "\t\t");
			System.out.print(rsetmd.getColumnName(2) + "\t");
			System.out.print(rsetmd.getColumnName(3) + "\t");
			System.out.print(rsetmd.getColumnName(4));
			System.out.println();
			header("Choisissez votre produit");
			while (rset.next()) {

				System.out.printf(
						"%30.30s\t%20.20s\t%.5s\t%10.5s", 
						rset.getString(1),
						rset.getString(2),
						rset.getString(3),
						rset.getString(4));
				System.out.println();
			}
			header("Quel produit vous intéresse?  Ou ");
			System.out.print("password: ");
		} catch (SQLException e) {e.printStackTrace();}
		option = scan.nextInt();
		return option;
	}

	//	public int listCategories() {
	//		int catNum;
	//		LinkedList<String> cat = new LinkedList<String>();
	//		ResultSet rset = conn.exec("SELECT NOM_CAT FROM CATEGORIE");
	//		gange("votre meilleur choix");//Choisissez une catégorie
	//		ResultSetMetaData rsetmd = null;
	//		for (rset; i++)
	//			cat.add()
	//			System.out.println();
	//		try {
	//			rsetmd = rset.getMetaData();
	//			System.out.print("\t\t\t\t");
	//
	//			System.out.print(rsetmd.getColumnName(1));
	//		} catch (SQLException e) {e.printStackTrace();}
	//		header("Choisissez votre produit");
	//		catNum = scan.nextInt();
	//		
	//		return catNum;
	//	}

	public int loginOrSignUp(){
		int option;
		do {
			gange("Bienvenue, voulez-vous vous inscrire ou vous connecter avec votre compte ?"); 
			System.out.println();
			System.out.println("\t\t\t     Mon compte............[1]");
			System.out.println("\t\t\t     Enregistrez...........[2]");
			System.out.println("\t\t\t     Quitter...............[0]");
			System.out.println();
			option = scan.nextInt();
		}while(option < 0 || option > 2);

		return option;
	}
	/**
	 * J'ai du ajouter email en parametre pour pouvoir faire appel à faire une enchere apres
	 * @param c
	 * @param idProd
	 * @param email
	 */
	public void displayProductInfo(ConnectionManager c, int idProd, String email){
		//On commence par récupérer toutes les données
		LinkedList<String> informations = c.getProductInfo(idProd);
		System.out.print("Produit : "+informations.get(0)+"\n");
		System.out.print("Prix : "+informations.get(1)+"\n");
		System.out.print("Description : "+informations.get(2)+"\n");
		int n = informations.size();
		System.out.print("Caractéristiques : \n");
		for (int i=3; i<n; i++){
			System.out.print(informations.get(i)+"\n");
		}
		Scanner scan = new Scanner(System.in);
		System.out.print("Voulez-vous faire une enchère? oui/non");
		String confirmation = scan.nextLine();
		if (confirmation.equals("oui")){
			this.faireEnchere(idProd, email);
			scan.close();
		}else{
			scan.close();
			return ;
		}
	}

	public void listProductsFromList(LinkedList<String> products){
		gange("votre meilleur choix");
		System.out.print("\t\t" + 
						 "INTITULÉ" + "\t\t");
		System.out.print("URL_PHOTO" + "\t");
		System.out.print("PRIX COURANT" + "\t");
		System.out.print("ID_P");
		System.out.println();
		header("Gange");
		for(int i = 0; i<products.size();i+=4) {
			
			System.out.printf(
					"%30.30s\t%20.20s\t%.5s\t%10.5s", 
					products.get(i),
					products.get(i+1),
					products.get(i+2),
					products.get(i+3));
			System.out.println();
		}
		header("Quel produit vous intéresse?  Ou ");
	}

	public void listCategories(LinkedList<String> categories){
		gange("votre meilleur choix");
		System.out.print("\t\t" + 
						 "Catégories" + "\t\t");
		System.out.println();
		header("Gange");
		int display = categories.size()>10?10:categories.size();
		for(int i = 0; i<display ;i++) { 
			
			System.out.printf(
					"%30.30s\t", 
					categories.get(i));
			System.out.println();
		}
		header("Quel catégorie vous intéresse?  Ou ");
	}


	public void displayRecommendedCategories(String email){
		//Firstly we get the recommended options
		LinkedList<String> Recommended = conn.getRecommended(email);
		//Then we print them out
		listCategories(Recommended);
		//Then we create a loop to navigate categories
		System.out.println("Quelle catégorie voulez vous regarder?");
		Scanner scan = new Scanner(System.in);
		String nomCat = scan.nextLine();
		try{
			LinkedList<String> SousCat = conn.getNextCategory(nomCat);
			while(!SousCat.isEmpty()){
				listCategories(SousCat);
				System.out.println("Quelle catégorie voulez vous regarder?");
				nomCat = scan.nextLine();
				SousCat = conn.getNextCategory(nomCat);
		}
		}catch(Exception e){
			//When there are no cat left we show the products
			System.out.println("Les produits de cette catégorie sont : \n");
			LinkedList<String> ProdFromCat = conn.getProductsFromCategory(nomCat);
			listProductsFromList(ProdFromCat);
			
			//On propose de faire une enchère
			System.out.println("Voulez vous voir un produit en détail? [oui/non] ");
			String confirmation = scan.next();
			if(confirmation.equals("oui")){
				System.out.println("Vous voulez voir quel produit? [Donner l'ID_P] ");
				int idProd = scan.nextInt();
				displayProductInfo(conn, idProd, email);
			}
			scan.close();
		}
		//When there are no cat left we show the products
		System.out.println("Les produits de cette catégorie sont : \n");
		LinkedList<String> ProdFromCat = conn.getProductsFromCategory(nomCat);
		listProductsFromList(ProdFromCat);
		
		//On propose de faire une enchère
		System.out.println("Voulez vous voir un produit en détail? [oui/non] ");
		String confirmation = scan.next();
		if(confirmation.equals("oui")){
			System.out.println("Vous voulez voir quel produit? [Donner l'ID_P] ");
			int idProd = scan.nextInt();
			displayProductInfo(conn, idProd, email);
		}
		scan.close();
	}

	public void displayAnyCategories(String email){
		//Firstly we get the recommended options
		LinkedList<String> Recommended = conn.getAnyCategory();
		//Then we print them out
		listCategories(Recommended);
		//Then we create a loop to navigate categories
		System.out.println("Quelle catégorie voulez vous regarder?");
		Scanner scan = new Scanner(System.in);
		String nomCat = scan.nextLine();
		try{
		LinkedList<String> SousCat = conn.getNextCategory(nomCat);
		while(!SousCat.isEmpty()){
			listCategories(SousCat);
			System.out.println("Quelle catégorie voulez vous regarder?");
			nomCat = scan.nextLine();
			SousCat = conn.getNextCategory(nomCat);
		}
		}catch(Exception e){
			//When there are no cat left we show the products
			System.out.println("Les produits de cette catégorie sont : \n");
			LinkedList<String> ProdFromCat = conn.getProductsFromCategory(nomCat);
			listProductsFromList(ProdFromCat);
			
			//On propose de voir un pdt en détail
			System.out.println("Voulez vous voir un produit en détail? [oui/non] ");
			String confirmation = scan.next();
			if(confirmation.equals("oui")){
				System.out.println("Vous voulez voir quel produit? [Donner l'ID_P] ");
				int idProd = scan.nextInt();
				displayProductInfo(conn, idProd, email);
			}
			scan.close();
		}
		
		//When there are no cat left we show the products
		System.out.println("Les produits de cette catégorie sont : \n");
		LinkedList<String> ProdFromCat = conn.getProductsFromCategory(nomCat);
		listProductsFromList(ProdFromCat);
		
		//On propose de voir un pdt en détail
		System.out.println("Voulez vous voir un produit en détail? [oui/non] ");
		String confirmation = scan.next();
		if(confirmation.equals("oui")){
			System.out.println("Vous voulez voir quel produit? [Donner l'ID_P] ");
			int idProd = scan.nextInt();
			displayProductInfo(conn, idProd, email);
		}
		scan.close();
	}


}


//	guilherme.faccin-huth@grenoble-inp.org
