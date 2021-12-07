import gange.*;

public class TestEncheres {
    public static void main(String[] args) {
		ConnectionManager Connected = new ConnectionManager();
		Menu menuTesting = new Menu();
		menuTesting.gange();
        //float prix =  10.25F;
        String email = "thais.bourret@grenoble-inp.org";

        /* Test de la modification de prix. Tout semble marcher correctement
        boolean verif = Connected.updateProduct(1, prix);
        System.out.println(verif);*/

        /*Test de la création d'offres. Tout se passe bien.
        boolean verif = Connected.newOffer(1, prix, 2, 1);
        System.out.println(verif);*/

        /*Test du récupérateur de Ids
        int verif = Connected.retrieveUserID(email);
        System.out.println(verif);*/

        /*Test de la fonction makebid
        boolean verif = Connected.makeBid(1, email);
        System.out.println(verif);*/

        menuTesting.faireEnchere(Connected, 4, email);
	}
    
}
