 --Affiche le nom de la catégorie et le nombre d'offre qui ont été faites dans cette catégorie par ordre decroissant
 --(prend aussi compte de l'ordre alphabétique)
 
SELECT nom_cat, COUNT(nom_cat) as nb
FROM PRODUIT
INNER JOIN OFFRE on Produit.ID_P = OFFRE.ID_P 
GROUP BY nom_cat
ORDER BY nb DESC, nom_cat;


--Affiche combien d'offre l'utilsiateur numero 1 a effectué dans toutes les catégeries par ordre décroissant 
--(prend aussi compte de l'ordre alphabétique si jamais des catégories ont le même nombre d'offres.)
 

SELECT id_u, nom_cat, COUNT(nom_cat) as nb
FROM PRODUIT
INNER JOIN OFFRE on Produit.ID_P = OFFRE.ID_P
Where offre.id_u = '1'
GROUP BY nom_cat, id_u
ORDER BY nb DESC, nom_cat;


-- Droit à l'oubli 
-- Par exemple le client dont id_u = 5 souhaite supprimmer ces données de la base

DELETE FROM CLIENT WHERE id_u = 5;
INSERT INTO UTILISATEUR VALUES(180);
UPDATE Offre SET id_u = '180' WHERE id_u = 5;
DELETE FROM UTILISATEUR WHERE id_u = 5;


-- Liste des produits où le client peut proposer une offre 

SELECT * FROM PRODUIT WHERE ID_P IN
(
SELECT id_p
FROM OFFRE
HAVING COUNT(num_offre) < 5
GROUP BY id_p
);


