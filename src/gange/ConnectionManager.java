package gange;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
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

		public ResultSet execSetString(String s, LinkedList<String> args) {
			try {
				PreparedStatement pstmt = this.connection.prepareStatement(s);
				
				for(int i = 1; i <= args.size(); i++){
					pstmt.setString(i, args.get(i-1)); // ieme parametre
				}
				
				return pstmt.executeQuery();
	
			} catch (SQLException e) {
				System.err.println("failed");
				e.printStackTrace();
			}
			return null;
		}
		
		public ResultSet execSetString(String s, String arg) {
			try {
				PreparedStatement pstmt = this.connection.prepareStatement(s);
					pstmt.setString(1, arg); // 1eme parametre
				return pstmt.executeQuery();
				
			} catch (SQLException e) {
				System.err.println("failed");
				e.printStackTrace();
			}
			return null;
		}

	public boolean verifyEmail(String email){
		try{
			
			ResultSet rset = execSetString(
				"select email from  client where email = ?",
				email);

			return rset.next();
		}catch(Exception e){
			return false;
		}
	}

	public boolean verifyPassword(String email, String password){
		try{
			LinkedList<String> credentials = new LinkedList<String>(); 
			credentials.add(email);
			credentials.add(password);
			ResultSet rset = execSetString(
				"select prenom from client where email = ? and mot_de_passe = ?",
				credentials);
			return rset.next();
		}catch(Exception e){
			System.err.println("Le mot de passe n'est pas correct");
			return true;
		}
	}
}
	