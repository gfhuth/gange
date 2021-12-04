package gange;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

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

	public void deletUser(String user) {
	}

}