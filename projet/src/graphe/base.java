package graphe;

import java.util.Scanner;

public class base {

    public static void main(String[] args) {
        Graphe Villes = new Graphe();
        Villes.importGraph();
        Environement e1=new Environement(Villes);
        Lancement b1=new Lancement(e1);
        b1.lancement();
    }


}









