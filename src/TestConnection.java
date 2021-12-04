import java.sql.*;
import gange.*;
public class TestConnection {
	public TestConnection() {
		try {
			// Enregistrement du driver Oracle
			ConnectionManager c = new ConnectionManager();
			// Creation de la requete
			Statement stmt = c.connection.createStatement();
			// Execution de la requete
			ResultSet rset = stmt.executeQuery(STMT);
			// Affichage du resultat
			System.out.println("Results:");
			dumpResultSet(rset);
			System.out.println();
			// Fermeture
			rset.close();
			stmt.close();
			c.connection.close();
		} catch (SQLException e) {
			System.err.println("failed");
			e.printStackTrace(System.err);
		}
	}
	public static void main(String args[]) {
		new TestConnection();
	}
}
