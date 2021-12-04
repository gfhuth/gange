CREATE TABLE client (
	email varchar(46) NOT NULL, 
	nom varchar(19), 
	prenom varchar(17), 
	adresse varchar(54), 
	mdp varchar(16), 
	PRIMARY KEY (email)
);
INSERT INTO client VALUES (
	'thais.Bourret@grenoble-inp.org', 
	'Bourret ', 
	'Thais', 
	'681, Rue de la Passerelle, Saint Martin Dheres', 
	'123456'
);
INSERT INTO client VALUES (
	'guilherme.Faccin-Huth@grenoble-inp.org', 
	'Faccin huth', 
	'Guilherme', 
	'681, Rue de la Passerelle, Saint Martin Dheres', 
	'qwerty'
);
INSERT INTO client VALUES (
	'lucas.Block-Medin@grenoble-inp.org', 
	'Block Medin', 
	'Lucas', 
	'681, Rue de la Passerelle, Saint Martin Dheres', 
	'password'
);
INSERT INTO client VALUES (
	'Yanis.Bouhjoura@grenoble-inp.org', 
	'Bouhjoura', 
	'Yanis', 
	'681, Rue de la Passerelle, Saint Martin Dheres', 
	'p455w0rd'
);
INSERT INTO client VALUES (
	'yassine.Katof@grenoble-inp.org', 
	'Katof', 
	'Yassine', 
	'681, Rue de la Passerelle, Saint Martin Dheres', 
	'312654'
);
