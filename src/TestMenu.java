import gange.*;

public class TestMenu {

	public static void main(String[] args) {
		ConnectionManager c = new ConnectionManager();
		Menu m = new Menu();
		switch(m.askSuggestion()) {
		case 1:
			System.out.println("oi");
			break;
		case 2:
			System.out.println("thcau");
			break;
		default: break;
		}
		
	}

}
