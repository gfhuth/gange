CREATE TABLE produit (
	ID_P integer NOT NULL, 
	NOM_CAT varchar(18), 
	INTITULÉ varchar(23), 
	PRIX_COURANT integer, 
	DESCRIPTION varchar(127), 
	URL_PHOTO varchar(99), 
	PRIMARY KEY (ID_P)
);
INSERT INTO produit VALUES (
	'1', 
	'Chaussures', 
	'Chaussures noir', 
	'5', 
	' Dimensions du produit(L x l x h): ‎40.6 x 17.8 x 12.7 cm; 311.84 grammes Date de mise en ligne sur : ‎ 1 juillet 2015 ', 
	'https://images-eu.ssl-images-amazon.com/images/I/8125fvykgXL.__AC_SY395_SX395_QL70_ML2_.jpg'
);
