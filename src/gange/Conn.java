package gange;

import java.sql.*;

public class Conn {

    static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    private String user = "blockmel"; // A remplacer pour votre compte, sinon genere une exception
    private String password = "blockmel";
    public Connection connection;
    
    public Conn() {
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
    
    public void close() {
        if(conn != null){
            try{
                conn.close();
            }catch (SQLException e){/*we can ignore this exception*/}
        }
    	
    }
    
}