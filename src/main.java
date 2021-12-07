import java.util.Scanner;
import gange.*;

public class main {
	public static void main(String[] args) {
		ConnectionManager conn = new ConnectionManager();
		Menu m = new Menu(conn);

		switch(m.loginOrSignUp()) {
		case 1://m.loginOrSignUp()
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
				}//m.askSuggestion()
				break;
			case 0: break;
			default:
				System.out.println("au revoir");
				conn.close();
				break;
			}//m.login()
		case 2://m.loginOrSignUp()
			m.creationCompte();
			break;
		default://m.loginOrSignUp()
			m.close();
			System.out.println("au revoir");
			conn.close();
			break;
		}//m.loginOrSignUp()
	}
}