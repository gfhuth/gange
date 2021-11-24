import java.sql.*;

public class Menu {

    static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    private String User; // A remplacer pour votre compte, sinon genere une exception
    private String Passwd;
    private Connection conn;

    public Menu(String user, String passwd) {
        this.User = user;
        this.Passwd = passwd;
        try {
        // Enregistrement du driver Oracle
        System.out.print("Loading Oracle driver... "); 
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        System.out.println("loaded");
        // Etablissement de la connection
        System.out.print("Connecting to the database... "); 
        this.conn = DriverManager.getConnection(CONN_URL, User, Passwd);
        System.out.println("connected");
        }catch (SQLException e){
            System.err.println("Connection failed");
            e.printStackTrace(System.err);
        }
    }

    public void Bienvenu(){
        System.out.println("Bienvenu dans Gange vos enchères de confiance");
    }
    
    public void Login(){
        
    }

    
}
