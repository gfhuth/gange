package gange;

import java.sql.*;

public class Conn {

	static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
	static final String user = "blockmel"; // A remplacer pour votre compte, sinon genere une exception
	static final String password = "blockmel";
	public Connection connection;

	public Conn() {
		try {
			// Enregistrement du driver Oracle
			System.out.print("Loading Oracle driver... "); 
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("loaded");
			// Etablissement de la connection
			System.out.print("Connecting to the database... "); 
			this.connection = DriverManager.getConnection(CONN_URL, user, password);
			System.out.println("connected");
		}catch (SQLException e){
			System.err.println("Connection failed");
			e.printStackTrace(System.err);
		}  
	}

	public void close() {
		if(this.connection != null){
			try{
				this.connection.close();
			} catch (SQLException e){/*we can ignore this exception*/
				System.err.println("failed");
				e.printStackTrace(System.err);
			}
				
		}

	}

	public ResultSet exec(String s) {
		try {
			// Creation de la requete
			Statement stmt = this.connection.createStatement();
			// Execution de la requete
			ResultSet rset = stmt.executeQuery(s);
			//			stmt.close();
			return rset;

		} catch (SQLException e) {
			System.err.println("failed");
			e.printStackTrace();
		}

		return null;
	}

	//    public ResultSet execSetString(String s, list<String>) {
	//    	try {
	//			PreparedStatement stmt = this.connection.prepareStatement(s);
	//			for(int i = 0; i<len(list); i++){
	//    			stmt.setString(2, list[i]); // 2eme parametre
	//			}
	//			ResultSet rset = stmt.executeQuery();
	//			rset.close();
	//
	//		} catch (SQLException e) {
	//			System.err.println("failed");
	//			e.printStackTrace();
	//		}
	//    	
	//    	
	//    }

}