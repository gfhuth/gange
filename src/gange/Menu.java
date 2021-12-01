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
        while(verifyEmail(email)){
            System.out.print("S'il vous plait rentrez une adresse mail valide:"); 
            String email = scan.next();
        }
        System.out.print("password: "); 
        String password = scan.next();        
        scan.close(); 
        // verify login and if it is right set email and password

    }

    public boolean verifyEmail(String email){
        try{
            //On établit la connection avec la BDD
            Conn conn= new Conn();
            //Creation de la requete
            PreparedStatement verifyEmail = conn.connection.prepareStatement("select * from  client where email = ?");
            verifyEmail.setString(1, email); 
            ResultSet rset = stmt.executeQuery();
            // Fermeture 
            rset.close();
            verifyEmail.close();
            conn.close();
            return true;
        }catch(Exception e){
            System.err.println("Cette adresse mail n'existe pas");
            return false;
        }
    }

    
}
