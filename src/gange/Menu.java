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
    
    /**
     * Gestion du Login de la personne, 
     * On lui demande son mail et mot de passe, puis on vérifie qu'ils existent
     * Et se correspondent.
     * @param conn
     */
    public static void login(ConnectionManager conn){
        Scanner scan = new Scanner(System.in);  
        System.out.print("e-mail: "); 
        String email = scan.next();
        //verify if email exist
        //if doesnt exist ask again to user to put a valid email
        while(!verifyEmail(email, conn)){
            System.out.print("S'il vous plait rentrez une adresse mail valide:"); 
            email = scan.next();
            
        }
        System.out.print("password: "); 
        String password = scan.next();
        // verify login and if it is right set email and password
        while(!verifyPassword(email, password, conn)){
            System.out.print("S'il vous plait rentrez un autre mot de passe:"); 
            password = scan.next();
        }
        scan.close(); 

    }

    public static boolean verifyEmail(String email, ConnectionManager conn){
        try{
            //Creation de la requete
            PreparedStatement verifyEmail = conn.connection.prepareStatement
            ("select * from client where email = ? ");
            verifyEmail.setString(1, email); 
            //conn.exec("select * from  client where email = ?"); I commented cause i don't really understand the function
            ResultSet rset = verifyEmail.executeQuery();
            // Fermeture 

            rset.close();
            verifyEmail.close();
            return true;
        }catch(Exception e){
            System.err.println("Cette adresse mail n'existe pas");
            return false;
        }
    }

    public static boolean verifyPassword(String email, String passwd, ConnectionManager conn){
        try{
            //Creation de la requete
            PreparedStatement verifyPasswd = conn.connection.prepareStatement
            ("select prenom from client where email = ? and mot_de_passe = ? ");
            verifyPasswd.setString(1, email); 
            verifyPasswd.setString(2, passwd);
            ResultSet rset = verifyPasswd.executeQuery();
            // Fermeture 
            while(rset.next()){
                System.out.println("Bienvenu "+rset.getString(1));
            }
            rset.close();
            verifyPasswd.close();
            return true; 
        }catch(SQLException e){
            System.err.println("Erreur");
            e.printStackTrace();
            return false;
        }
    }
    
}
