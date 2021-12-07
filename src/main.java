import java.util.Scanner;
import gange.*;

public class main {
	public static void main(String[] args) {
		ConnectionManager conn = new ConnectionManager();
		Menu m = new Menu(conn);

		switch(m.login()) {
		case 1:
			switch(m.askSuggestion()) {
			case 1:
				System.out.println("oi"); 	//with suggestion
				break;
			case 2:
				System.out.println("thcau"); // without suggestion
				
				
				break;
			default: break;
			}
		case 0:
		default:
			System.out.println("au revoir");
			conn.close();
			break;
		}
	}
}
