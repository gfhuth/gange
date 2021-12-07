import java.sql.ResultSet;
import java.sql.SQLException;

import gange.*;

public class TestMenu {

	public static void main(String[] args) {
<<<<<<< HEAD
<<<<<<< HEAD
		ConnectionManager c = new ConnectionManager();
		Menu m = new Menu(c);
		switch(m.askSuggestion()) {
		case 1:
			System.out.println("oi");
			break;
		case 2:
			try {
				m.listProducts("SELECT INTITULÉ, URL_PHOTO, PRIX_COURANT, ID_P FROM PRODUIT");
			} catch (SQLException e) {e.printStackTrace();}
			break;
		default: break;
		}
		
=======
=======
>>>>>>> RGPDFromMaster
		ConnectionManager Connected = new ConnectionManager();
		Menu menuTesting = new Menu();
		//menuTesting.bienvenu();
		menuTesting.login(Connected);
<<<<<<< HEAD
>>>>>>> RGPDFromMaster
=======
>>>>>>> RGPDFromMaster
	}

}
