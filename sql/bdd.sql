CREATE TABLE Utilisateur
(
id_u INT NOT NULL,
PRIMARY KEY(id_u)
);

CREATE TABLE Client
(
email	       varchar(255) NOT NULL,
id_u	       REFERENCES UTILISATEUR(id_u),
nom            varchar (30) NOT NULL,
prenom         varchar (30) NOT NULL,
adresse        varchar(255) NOT NULL,
Mot_de_passe   varchar (30) NOT NULL,
PRIMARY KEY (email)
);

CREATE TABLE CATEGORIE (
nom_cat varchar(255) NOT NULL,
cat_mere varchar(255) NOT NULL,
PRIMARY KEY (nom_cat)
);

CREATE TABLE PRODUIT
(
id_p	       INT NOT NULL,
nom_cat        REFERENCES CATEGORIE(nom_cat),
intitulé       varchar (30) NOT NULL,
prix_courant   FLOAT NOT NULL,
description    varchar(255) NOT NULL,
url_photo      varchar(255) NOT NULL,
PRIMARY KEY (id_p)
);

CREATE TABLE DONNEES
(
Caracteristique varchar(255) NOT NULL,
id_p            REFERENCES PRODUIT(id_p),
valeur          varchar(255) NOT NULL,
PRIMARY KEY (Caracteristique)
);

-- faute d'orthographe dans datee car sinon erreur date ne peut pas être utilisé comme nom de colonne !
CREATE TABLE OFFRE
(
id_p          REFERENCES PRODUIT(id_p),
datee         varchar(255) NOT NULL,
heure         varchar(255) NOT NULL,
id_u          REFERENCES UTILISATEUR(id_u),
prix_proposé  FLOAT NOT NULL,
num_offre     INT NOT NULL,
PRIMARY KEY(id_p, datee, heure),
CONSTRAINT   prix_proposé  CHECK   (prix_proposé > 0),
CONSTRAINT   num_offre     CHECK   (num_offre < 6)
);
