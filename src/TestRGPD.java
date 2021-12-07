import gange.*;

public class TestRGPD {

    public static void main(String[] args) {
		ConnectionManager Connected = new ConnectionManager();
		Menu menuTesting = new Menu();
		menuTesting.gange();
        String email = "lucas.block@gmail.com"; //We should create this using 
		//menuTesting.creationCompte(Connected);
        menuTesting.eliminerClient(Connected, email);
	}
}
