package graphe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Formats de graphe pris en compte
Matrice d'adjacence (importGraphfromAdjacence):
3 -- Nombre de sommets
0 0 0 -- Matrice
0 0 0
0 0 0

Liste de sommet avec coordonnées (importGraphfromVertex):
3 -- Nombre de sommets
0 0 -- Coordonnées X Y
0 0
0 0
 */

public class base {

    public static void main(String[] args) {

        Graphe Villes = new Graphe();
        Villes.importGraphfromAdjacence("Fichier/Villes.txt");

        System.out.println("Best Insertion : ");
        System.out.println(greedyBestInsertion.calculTSP(Villes) + "\n");

        System.out.println("Nearest Insertion : ");
        System.out.println(greedyNearestInsertion.calculTSP(Villes) + "\n");

        System.out.println("Nearest Neighbor & OPT-2 : ");
        NearestNeighbor.nearestNeighbour(5, (HashMap<Integer, Map<Integer, Integer>>) Villes.getListeAdjacence());

        System.out.println("Fourmis : ");
        Environement e1=new Environement(Villes);
        Lancement b1=new Lancement(e1);
        b1.lancement();

    }

}
