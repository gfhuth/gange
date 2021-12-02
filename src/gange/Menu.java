package gange;
import java.sql.*;
import java.util.Scanner;

public class Menu {
	
	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}  
    public void bienvenu(){
        System.out.println("Bienvenu dans Gange vos enchères de confiance");
    }
    
    public void login(Conn connection){
        Scanner scan = new Scanner(System.in);  
        System.out.print("e-mail: "); 
        String email = scan.next();
        //verify if email exist
        //if doesnt exist ask again to user to put a valid email
        while(connection.verifyEmail(email)){
            System.out.print("S'il vous plait rentrez une adresse mail valide:"); 
            email = scan.next();
            
        }
        System.out.print("password: ");
        String password = scan.next();
        scan.close();
        // verify login and if it is right set email and password

    }

 

    
}
