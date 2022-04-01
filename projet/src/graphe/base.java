package graphe;

import java.util.List;

public class base {

    public static void main(String[] args) {

        Graphe Villes = new Graphe();
        Villes.importGraphfromAdjacence("Fichier/Villes.txt");

        for (int i = 0;i<100;i++) {
            List<Integer> chemin2 = greedyNearestInsertion.calculTSP(Villes);
            System.out.println(chemin2);
        }

    }

}
