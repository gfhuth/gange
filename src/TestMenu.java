import gange.*;

public class TestMenu {

	public static void main(String[] args) {
		ConnectionManager Connected = new ConnectionManager();
		Menu menuTesting = new Menu();
		menuTesting.bienvenu();
		menuTesting.login(Connected);
	}

}
