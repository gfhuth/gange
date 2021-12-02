import java.sql.*;
import gange.*;
public class TestConnection {
	public TestConnection() {
		try {
			// Enregistrement du driver Oracle
			Conn c = new Conn();
			Menu m = new Menu();

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
