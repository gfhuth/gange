import java.sql.*;
import gange.*;
public class TestConnection {
	public TestConnection() {
		try {
			// Enregistrement du driver Oracle
			ConnectionManager c = new ConnectionManager();
			Menu m = new Menu(c);
			m.login();

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
