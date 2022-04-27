package graphe;

import java.util.Scanner;

public class base {

    public static void main(String[] args) {
        Graphe Villes = new Graphe();
        Villes.importGraph();
        Environement e1=new Environement(Villes);
        Scanner scan = new Scanner(System.in);
        System.out.println(" Veuillez Entrer une valeur d'alpha plus grande que 0");
        e1.alpha=scan.nextDouble();

        System.out.println(" Veuillez Entrer une valeur beta plus grande que 0");
        e1.beta=scan.nextDouble();
        assert (e1.beta<=0 ): " Veuiller rentrer une valeur suppérieur à 0";
        assert (e1.alpha<=0 ): " Veuiller rentrer une valeur suppérieur à 0";


        scan.close();

             Lancement b1=new Lancement(e1);
             b1.lancement();


    }


}









