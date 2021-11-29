CREATE TABLE client (
	e-mail varchar(46) NOT NULL, 
	nom varchar(19), 
	prenom varchar(17), 
	adresse varchar(55), 
	mdp varchar(16), 
	PRIMARY KEY (e-mail)
);
INSERT INTO client VALUES (
	'thais.Bourret@grenoble-inp.org', 
	'Bourret ', 
	'Thais', 
	'681, Rue de la Passerelle, Saint Martin D'heres', 
	'123456'
);
INSERT INTO client VALUES (
	'guilherme.Faccin-Huth@grenoble-inp.org', 
	'Faccin huth', 
	'Guilherme', 
	'681, Rue de la Passerelle, Saint Martin D'heres', 
	'qwerty'
);
INSERT INTO client VALUES (
	'lucas.Block-Medin@grenoble-inp.org', 
	'Block Medin', 
	'Lucas', 
	'681, Rue de la Passerelle, Saint Martin D'heres', 
	'password'
);
INSERT INTO client VALUES (
	'Yanis.Bouhjoura@grenoble-inp.org', 
	'Bouhjoura', 
	'Yanis', 
	'681, Rue de la Passerelle, Saint Martin D'heres', 
	'p455w0rd'
);
INSERT INTO client VALUES (
	'yassine.Katof@grenoble-inp.org', 
	'Katof', 
	'Yassine', 
	'681, Rue de la Passerelle, Saint Martin D'heres', 
	'312654'
);
