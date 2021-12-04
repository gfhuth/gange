package gange;

import java.sql.*;
//import java.util.LinkedList;

public class ConnectionManager {

	static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
	static final String user = "katofy"; // A remplacer pour votre compte, sinon genere une exception
	static final String password = "katofy";
	public Connection connection;

	public ConnectionManager() {
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

//	public ResultSet execSetString(String s, String args) {
//		try {
//			PreparedStatement pstmt = this.connection.prepareStatement(s);
//
//			//			for(int i = 0; i<len(list); i++){
//			pstmt.setString(1, args); // 2eme parametre
//			//			}
//			ResultSet rset = pstmt.executeQuery();
//			return rset;
//
//		} catch (SQLException e) {
//			System.err.println("failed");
//			e.printStackTrace();
//		}
//		return null;
//	}

	// public boolean verifyEmail(String email){
	// 	try{
	// 		PreparedStatement pstmt = this.connection.prepareStatement(
	// 				"select email from  client where email = ?");
	// 		pstmt.setString(1, email); // 2eme parametre
	// 		ResultSet rset = pstmt.executeQuery();
	// 		boolean oi ;
	// 		oi = rset.next();
	// 		return !oi;
	// 	}catch(Exception e){
	// 		System.err.println("Cette adresse mail n'existe pas");
	// 		return true;
	// 	}
	// }

}