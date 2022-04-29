package graphe;

import java.util.HashMap;
import java.util.Map;

import static graphe.Opt2.opt2;

public class NearestNeighbor {

    public static boolean parcoursFini(boolean monTableau[]){ //Fonction pour le nearest qui vérifie si tous les sommets ont été visités
        int sontVisites = 0;
        for(int k = 0; k < monTableau.length; k++){
            if(monTableau[k])
                sontVisites++;
        }
        return sontVisites == monTableau.length;
    }

    public static int nearestNeighbour(int sommetDepart, HashMap<Integer, Map<Integer, Integer>> monGraphe) { //Algorithme que j'ai réalisé afin de pouvoir tester mon 2opt dessus
        System.out.println(" ------Nearest Neighbour---------");
        boolean visited[] = new boolean[monGraphe.size()];
        int cheminSolution[] = new int[monGraphe.size()];
        int coutSolution = 0;
        int index = 0; //Nous servira d'index pour le tableau cheminSolution
        int memoire = 0; //Sauvegardera l'index du point désiré
        int sommetCourant = sommetDepart;
        while(!parcoursFini(visited)){
            visited[sommetCourant] = true;
            int min = 500;
            for(int j = 0; j < monGraphe.size(); j++){
                if(parcoursFini(visited)){ // Si tout les sommets ont été visités, on coupe la boucle afin d'éviter une mauvaise valeur
                    coutSolution += 0;
                    index++;
                    break;

                }
                if(monGraphe.get(sommetCourant).get(j) < min && !visited[j] && monGraphe.get(sommetCourant).get(j) != 0){ // Si on trouve une arête non visitée et qui ne va pas sur elle-même, le minimum prend sa valeur et on la garde en memoire
                    memoire = j;
                    min = monGraphe.get(sommetCourant).get(j);
                }
            }
            if(parcoursFini(visited)){ // Une fois le parcours terminé, on ajoute l'arête qui part du sommet final au sommet de départ
                coutSolution += monGraphe.get(sommetCourant).get(sommetDepart);
                cheminSolution[index - 1] = sommetCourant;


            }
            else{ // Sinon, on ajoute min(qui a pris la valeur de l'arête qui nous intéresse) à la longueur du chemin, on ajoute au tableau de la solution le sommet courant
                coutSolution += min;
                cheminSolution[index] = sommetCourant;
                sommetCourant = memoire;
                index++;
            }
        }
        System.out.print("Solution : [");
        for(int u = 0; u < cheminSolution.length; u++){ // Affichage de la solution trouvée

            System.out.print(" " +cheminSolution[u]);
        }
        System.out.println("]");
        System.out.println("Résultat du 2-opt : " + opt2(cheminSolution));
        System.out.print("Résultat du nearest neighbour : " + coutSolution);
        System.out.println("");
        System.out.println(" --------------------------------");
        return coutSolution; // On retourne la longueur du chemin de la solution
    }
}
