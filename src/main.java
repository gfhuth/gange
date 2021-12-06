import java.util.Scanner;
import gange.*;

public class main {
	public static void main(String[] args) {
		ConnectionManager c = new ConnectionManager();
		Menu m = new Menu();

		switch(m.login(c)) {
		case 1:
			switch(m.askSuggestion()) {
			case 1:
				System.out.println("oi");
				break;
			case 2:
				System.out.println("thcau");
				break;
			default: break;
			}
		case 0:
		default:
			System.out.println("au revoir");
			c.close();
			break;
		}
	}
}
