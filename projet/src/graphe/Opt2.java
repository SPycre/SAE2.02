package graphe;

import static graphe.Graphe.listeAdjacence;

public class Opt2 {

    public static void echangerAretes(int indi1, int indi2, int maSolution[]) { // On échange notre chemin actuel par le chemin trouvé dans comparerSolutions
        int compteur = 0;
        for(indi1 = indi1; indi1 < indi2; indi1++){
            int memory = maSolution[indi1];
            maSolution[indi1] = maSolution[indi2 - compteur];
            maSolution[indi2 - compteur] = memory;
            compteur++;
        }

    }

    public static int parcours(int maSolution[]){ // Fonction qui mesure la longueur d'une solution
        int coutChemin = 0;
        for(int i = 0; i < maSolution.length - 1; i++){
            coutChemin += listeAdjacence.get(maSolution[i]).get(maSolution[i + 1]);
        }
        coutChemin += listeAdjacence.get(maSolution[maSolution.length - 1]).get(maSolution[0]);
        return coutChemin;
    }

    public static boolean comparerSolutions(int indi1, int indi2, int maSolution[]){ //Fonction qui compare la solution actuelle avec une nouvelle
        int monAutreSolution[] = maSolution.clone();
        int compteur = 0;
        for(indi1 = indi1; indi1 < indi2; indi1++){ // On inverse les points entre les index pour voir si cela nous donne un meilleur chemin
            int memory = monAutreSolution[indi1];
            monAutreSolution[indi1] = monAutreSolution[indi2 - compteur];
            monAutreSolution[indi2 - compteur] = memory;
            compteur++;

        }
        return parcours(monAutreSolution) < parcours(maSolution); //Si la longueur du nouveau chemin est plus petite que la longueur du chemin actuel, on échangera le chemin actuel par le nouveau
    }

    public static double opt2(int sommetsSolution[]) { //Algorithme 2opt que j'applique avec le nearest neighbour
        boolean amelioration = true;
        while(amelioration){ //Tant que l'on peut améliorer la solution
            amelioration = false;
            for(int indi1 = 0; indi1 < sommetsSolution.length; indi1++) { // On parcourt la solution
                for (int indi2 = 2; indi2 < sommetsSolution.length - 1; indi2++) {
                    if(comparerSolutions(indi1,indi2,sommetsSolution)){ //Si on constate une amélioration, on échange les arêtes
                        amelioration = true;
                        echangerAretes(indi1,indi2,sommetsSolution);
                    }
                }
            }
        }
        System.out.print("Solution 2opt : [");
        for(int i = 0; i <  sommetsSolution.length; i++){ //On affiche la nouvelle solution
            System.out.print(" " + sommetsSolution[i]);
        }
        System.out.println(" ]");
        return parcours(sommetsSolution); // On retourne la longueur du nouveau chemin
    }
}
