import java.sql.*;
import gange.*;
public class TestConnection {
	static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
	static final String USER = "katofy"; // A remplacer pour votre compte
	static final String PASSWD = "katofy";
	static final String STMT = "select * from CLIENT";

	public TestConnection() {
		try {
			// Enregistrement du driver Oracle
			Conn c = new Conn();
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
	private void dumpResultSet(ResultSet rset) throws SQLException {
		ResultSetMetaData rsetmd = rset.getMetaData();
		System.out.println("teste");
		int i = rsetmd.getColumnCount();
		for (int k=1;k<=i;k++)
			System.out.print(rsetmd.getColumnName(k) + "\t");
		System.out.println();
		while (rset.next()) {
			for (int j = 1; j <= i; j++) {
				System.out.print(rset.getString(j) + "\t");
			}
			System.out.println();
		}
		System.out.println("finish dump, why dont dump the table?");
	}
	public static void main(String args[]) {
		new TestConnection();
	}
}
