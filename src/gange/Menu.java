package gange;
import java.sql.*;
import java.util.Scanner;

public class Menu {

    public static void bienvenu(){
        System.out.println("Bienvenu dans Gange vos enchères de confiance");
    }
    
    public static void login(){
        Scanner scan = new Scanner(System.in);  
        System.out.print("e-mail: "); 
        String email = scan.next();
        //verify if email exist
        //if doesnt exist ask again to user to put a valid email
        System.out.print("password: "); 
        String password = scan.next();        
        scan.close(); 
        // verify login and if it is right set email and password
    }

    
}
