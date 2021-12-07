package gange;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Menu {
	ConnectionManager conn;
	public Menu(ConnectionManager conn) {
		this.conn = conn;
	}

	
	public int login(){
		Scanner scan = new Scanner(System.in);
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
		//		scan.close();
		scan.close();
		return 1 - quitFlag; //	invert quitFlag
	}

	public void fermerApp() {

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
		Scanner scan = new Scanner(System.in);
		System.out.print("Bonjour, êtes vous sur de vouloir éliminer votre compte? [oui/non]");   
		String confirmation = scan.nextLine();
		System.out.println(confirmation);
		if(confirmation.equals("oui")){
			if(this.conn.delClient(email)){
				System.out.println("Votre compte a ete elimine. On va vous déconnecter");
				this.conn.close();
			}else{
				System.out.println("Il y a eu une erreur. On n'as pas pu vous éliminer.");
			}	
		}else{
			System.out.println("On reviens vers votre page de recommandations");
			System.out.println("Si c'est ps ce que vous vouliez,veuillez reéssayer en écrivant oui en toutes lettres");
		}
		scan.close();
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
		Scanner scan = new Scanner(System.in);
		//On recupère les coords
		System.out.print("On va procéder à la création d'un compte veuillez répondre aux questions suivantes\n");
		System.out.println("Email:");
		String email=scan.nextLine();
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
		scan.close();
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

	public int askSuggestion() {
		Scanner scan = new Scanner(System.in);
		int option;
		do {
			
			gange("Souhaitez-vous recevoir des suggestions basées sur vos antécédents ?"); 
			System.out.println();
			System.out.println("\t\t\t     Oui...................[1]");
			System.out.println("\t\t\t     Non...................[2]");
			System.out.println("\t\t\t     Quitter...............[0]");
			System.out.println();
			option = scan.nextInt();
		}while(option < 0 || option > 3);
		scan.close();
		return option;
	}

	public int listProducts(String s){
		Scanner scan = new Scanner(System.in);
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
		scan.close();
		return option;
	}
	
	public int listCategories() {
		int catNum;
		Scanner scan = new Scanner(System.in);
		LinkedList<String> cat = new LinkedList<String>();
		ResultSet rset = conn.exec("SELECT NOM_CAT FROM CATEGORIE");
		gange("votre meilleur choix");//Choisissez une catégorie
		ResultSetMetaData rsetmd = rset.getMetaData();
		System.out.print("\t\t\t\t");
		System.out.print(rsetmd.getColumnName(1));
		header("Choisissez votre produit");
		catNum = scan.nextInt();
		scan.close();
		return catNum;
	}

}
//	guilherme.faccin-huth@grenoble-inp.org
