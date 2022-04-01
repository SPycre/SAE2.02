package graphe;

import java.util.List;

public class base {

    public static void main(String[] args) {

        Graphe Villes = new Graphe();
        Villes.importGraphfromAdjacence();
       List<Integer> chemin = greedyBestInsertion.calculTSP(Villes);
       //List<Integer> chemin2 = greedyNearestInsertion.calculTSP(Villes);
       System.out.println(chemin);
       //System.out.println(chemin2);

    }

}
