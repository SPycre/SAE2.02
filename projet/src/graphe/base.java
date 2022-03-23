package graphe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class base {

    public static void main(String[] args) {

        Graphe Villes = new Graphe();
        Villes.importGraph();
       List<Integer> chemin = greedyBestInsertion.calculTSP(Villes);
       System.out.println(chemin);

    }

}
