CREATE TABLE produit (
	id_p integer NOT NULL, 
	nom varchar(10), 
	prixCourant DOUBLE, 
	description varchar(13), 
	urlPhoto varchar(15), 
	categorie varchar(12), 
	PRIMARY KEY (id_p)
);
INSERT INTO produit VALUES (
	'1', 
	'oi', 
	'1.9', 
	'teste', 
	'www.com', 
	'casa'
);
