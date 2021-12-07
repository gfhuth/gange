 --Affiche le nom de la catégorie et le nombre d'offre qui ont été faites dans cette catégorie par ordre decroissant
 
 
SELECT nom_cat, COUNT(nom_cat) as nb
FROM PRODUIT
INNER JOIN OFFRE on Produit.ID_P = OFFRE.ID_P 
GROUP BY nom_cat
ORDER BY nb DESC, nom_cat;


--Affiche combien d'offre l'utilsiateur numero 1 a effectué dans toutes les catégeries par ordre décroissant (prend aussi compte de l'ordre alphabétique)
 

SELECT id_u, nom_cat, COUNT(nom_cat) as nb
FROM PRODUIT
INNER JOIN OFFRE on Produit.ID_P = OFFRE.ID_P
Where offre.id_u = '1'
GROUP BY nom_cat, id_u
ORDER BY nb DESC, nom_cat;
