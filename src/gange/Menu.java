package gange;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.util.LinkedList;

public class Menu {
	
	public boolean login(ConnectionManager c){

		clear();
		gange();
		header("Bienvenu dans Gange vos enchères de confiance");
		Scanner scan = new Scanner(System.in);  
		System.out.print("e-mail: ");   
		String email = scan.next();
		//verify if email exist
	    //if doesnt exist ask again to user to put a valid email
		while(!c.verifyEmail(email)){
			System.out.println("S'il vous plait rentrez une adresse mail valide:");
			System.out.print("e-mail: "); 
			email = scan.next();
			
		}

		System.out.print("password: ");
		String password = scan.next();
		while(!c.verifyPassword(email, password)){
			System.out.println("S'il vous plait rentrez un autre mot de passe:");
			System.out.print("password: ");
			password = scan.next();
		}

		System.out.println("saiu");
		scan.close();
		return true;
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

	public void gange() {
		System.out.println("\t\t   ████████                                    ");
		System.out.println("\t\t  ██░░░░░░██                     █████         ");
		System.out.println("\t\t ██      ░░   ██████   ███████  ██░░░██  █████ ");
		System.out.println("\t\t░██          ░░░░░░██ ░░██░░░██░██  ░██ ██░░░██");
		System.out.println("\t\t░██    █████  ███████  ░██  ░██░░██████░███████");
		System.out.println("\t\t░░██  ░░░░██ ██░░░░██  ░██  ░██ ░░░░░██░██░░░░ ");
		System.out.println("\t\t ░░████████ ░░████████ ███  ░██  █████ ░░██████");
		System.out.println("\t\t  ░░░░░░░░   ░░░░░░░░ ░░░   ░░  ░░░░░   ░░░░░░ ");

	}

	/**
	 * Cette fonction va servir à gérer un Client qui veut éliminer son compte
	 * 
	 * TODO : Il serait intéréssant de stocker le mail du client lors du login,parceque ça n'as pas trop de sens de le redemander.
	 * 
	 * @param c
	 * @param email
	 */
	public void eliminerClient(ConnectionManager c, String email) {
		Scanner scan = new Scanner(System.in);  
		System.out.print("Bonjour, êtes vous sur de vouloir éliminer votre compte? [oui/non]");   
		String confirmation = scan.nextLine();
		System.out.println(confirmation);
		if(confirmation.equals("oui")){
			if(c.delClient(email)){
				System.out.println("Votre compte a ete elimine. On va vous déconnecter");
				c.close();
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
	public void creationCompte(ConnectionManager c){
		LinkedList<String> coord = new LinkedList<String>();
		Scanner scan = new Scanner(System.in); 

		//On recupère les coords
		System.out.print("On va procéder à la création d'un compte veuillez répondre aux questions suivantes\n");
		System.out.println("Email:");
		String email=scan.nextLine();
		System.out.println(email);
		coord.add(email);
		System.out.println("Nom:");
		String nom=scan.nextLine();
		System.out.println(nom);
		coord.add(nom);
		System.out.println("Prenom:");
		String prenom=scan.nextLine();
		System.out.println(prenom);
		coord.add(prenom);
		System.out.println("Adresse:");
		String adresse=scan.nextLine();
		System.out.println(adresse);
		coord.add(adresse);
		System.out.println("Mot de passe:");
		String motDePasse=scan.nextLine();
		System.out.println(motDePasse);
		coord.add(motDePasse);

		//On execute la demande de création
		if(c.createClient(coord)){
			System.out.println("Votre compte a été crée correctement. Maintenant vous êtes connecté");
		}else{
			System.out.println("Une erreur est survenue, le compte n'as pas été créé");
		}

		//On ferme le scan
		scan.close();
	}
}

