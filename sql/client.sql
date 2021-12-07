CREATE TABLE client (
	EMAIL varchar(46) NOT NULL, 
	ID_U integer, 
	NOM varchar(19), 
	PRENOM varchar(17), 
	ADRESSE varchar(54), 
	MOT_DE_PASSE varchar(16), 
	PRIMARY KEY (EMAIL)
);
INSERT INTO client VALUES (
	'thais.Bourret@grenoble-inp.org', 
	'1', 
	'Bourret ', 
	'Thais', 
	'681, Rue de la Passerelle, Saint Martin Dheres', 
	'123456'
);
INSERT INTO client VALUES (
	'guilherme.Faccin-Huth@grenoble-inp.org', 
	'2', 
	'Faccin huth', 
	'Guilherme', 
	'681, Rue de la Passerelle, Saint Martin Dheres', 
	'qwerty'
);
INSERT INTO client VALUES (
	'lucas.Block-Medin@grenoble-inp.org', 
	'3', 
	'Block Medin', 
	'Lucas', 
	'681, Rue de la Passerelle, Saint Martin Dheres', 
	'password'
);
INSERT INTO client VALUES (
	'Yanis.Bouhjoura@grenoble-inp.org', 
	'4', 
	'Bouhjoura', 
	'Yanis', 
	'681, Rue de la Passerelle, Saint Martin Dheres', 
	'p455w0rd'
);
INSERT INTO client VALUES (
	'yassine.Katof@grenoble-inp.org', 
	'5', 
	'Katof', 
	'Yassine', 
	'681, Rue de la Passerelle, Saint Martin Dheres', 
	'312654'
);
