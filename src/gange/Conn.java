package gange;

import java.sql.*;

public class Conn{

	static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
	static final String user = "katofy"; // A remplacer pour votre compte, sinon genere une exception
	static final String password = "katofy";
	static final String STMT = "select * from CLIENT";
	public Connection conn;

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
}