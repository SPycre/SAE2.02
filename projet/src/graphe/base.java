package graphe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class base {

    public static void main(String[] args) {

        Graphe Villes = new Graphe();
        Villes.importGraph();
        System.out.println(Villes);

    }

}
