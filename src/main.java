import java.util.Scanner;
import gange.*;

public class main {
	public static void main(String[] args) {
		ConnectionManager conn = new ConnectionManager();
		Menu m = new Menu(conn);
		User user = new User();

		switch(m.loginOrSignUp()) {
		case 1://m.loginOrSignUp()
			switch(m.login(user)) {
			case 1:
				switch(m.accuiel()) {
				case 1:
					System.out.println("oi"); 	//with suggestion
					break;
				case 2:
					System.out.println("thcau"); // without suggestion
					break;
				case 3:
					m.eliminerClient(user.getEmail()); // without suggestion
					break;

				default: break;
				}//m.askSuggestion()
				break;//case 1 m.login(user)
			case 0: break;
			default: break;
			}//m.login()
			break;//case 1 m.loginOrSignUp()
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