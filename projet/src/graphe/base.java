package graphe;

import java.util.List;

public class base {

    public static void main(String[] args) {

        Graphe Villes = new Graphe();
        Villes.importGraphfromVertex("Fichier/test.txt");
        System.out.println(Villes);

        Villes.exportGraph("Fichier/test.txt");

    }

}
