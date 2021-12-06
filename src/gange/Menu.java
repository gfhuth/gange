package gange;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Menu {
	Scanner scan = new Scanner(System.in);
	public int login(ConnectionManager c){

		clear();
		gange();
		header("Bienvenu dans Gange vos enchères de confiance"); 
		System.out.print("e-mail: ");   
		String email = scan.next();
		//verify if email exist
		//if doesnt exist ask again to user to put a valid email
		int errorCount = 0;
		int quitFlag = 0 ;
		while(!c.verifyEmail(email)){
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
			while(!c.verifyPassword(email, password)){
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

	public void deletUser(String user) {
	}

	public int askSuggestion() {
		int option;
		
		
//		Scanner scan = new Scanner(System.in);  
		do {
			clear();
			gange();
			header("Bienvenu dans Gange vos enchères de confiance"); 
			System.out.println();
			System.out.println("Souhaitez-vous recevoir des suggestions basées sur vos antécédents ?");
			System.out.println("\t\t\t     Oui...................[1]");
			System.out.println("\t\t\t     Non...................[2]");
			System.out.println("\t\t\t     Quitter...............[0]");
			System.out.println();
			option = scan.nextInt();
		}while(option < 0 || option > 3);
//		scan.close();
		return option;
	}
}
//	guilherme.faccin-huth@grenoble-inp.org

