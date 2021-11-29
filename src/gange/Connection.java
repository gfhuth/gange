package gange;

import java.sql.*;

public class Connection {

    static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    private String user = "faccinhg"; // A remplacer pour votre compte, sinon genere une exception
    private String password = "faccing";
    public Connection conn;
    
    public Connection() {
        try {
        // Enregistrement du driver Oracle
        System.out.print("Loading Oracle driver... "); 
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        System.out.println("loaded");
        // Etablissement de la connection
        System.out.print("Connecting to the database... "); 
        this.conn = DriverManager.getConnection(CONN_URL, user, password);
        System.out.println("connected");
        }catch (SQLException e){
            System.err.println("Connection failed");
            e.printStackTrace(System.err);
        }  
    }
    
    public close() {
    	
    }
    
}