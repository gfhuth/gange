import java.sql.*;
import gange.*;
public class TestConnection {
	public static void main(String[] args) {
		Conn c = new Conn();
		c.conn.close();
	}
}
