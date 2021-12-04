import java.sql.*;
import gange.*;
public class TestConnection {
	public TestConnection() {
		try {
			// Enregistrement du driver Oracle
			ConnectionManager c = new ConnectionManager();
			Menu m = new Menu();
			m.clear();
			m.login(c);

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
