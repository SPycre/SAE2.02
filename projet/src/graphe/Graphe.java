package graphe;

import java.io.*;
import java.util.*;

public class Graphe {
    private final boolean orientation;
    static Map<Integer,Map<Integer,Integer>> listeAdjacence;

    public Graphe(boolean orientation){
        this.orientation = orientation;
        this.listeAdjacence = new HashMap();
    }

    public Graphe() {
        this(false);
    }

    public void ajoutSommet(int sommet) {
        if (!listeAdjacence.containsKey(sommet)) {
            listeAdjacence.put(sommet,new HashMap<>());
        }
    }

    public void ajoutArete(int sommetSource, int sommetDestination, int poid) {
        this.ajoutSommet(sommetSource);
        this.ajoutSommet(sommetDestination);
        listeAdjacence.get(sommetSource).put(sommetDestination,poid);
        if (!this.orientation) {
            listeAdjacence.get(sommetDestination).put(sommetSource,poid);
        }
    }

    public int getPoids(int sommetSource,int sommetDestination) {
        if (listeAdjacence.containsKey(sommetSource) && listeAdjacence.get(sommetSource).containsKey(sommetDestination)) {
            return listeAdjacence.get(sommetSource).get(sommetDestination);
        } else {
            return -1;
        }
    }

    public Set<Integer> getSommets() {
        return listeAdjacence.keySet();
    }

    public Set<Integer> getVoisins(int sommet) {
        if (listeAdjacence.containsKey(sommet)) {
            return listeAdjacence.get(sommet).keySet();
        } else {
            return null;
        }
    }

    public void exportGraph() {
        int id = 0;

        try {
            File fichier = new File("Fichier/test.txt");
            String text = "";

            text += listeAdjacence.size() + "\n";
            for (int idCourant = 0;idCourant<listeAdjacence.size();idCourant++) {
                for (int idVoisin = 0;idVoisin<listeAdjacence.size();idVoisin++) {
                    if (idVoisin == idCourant) {
                        text += "0 ";
                    } else if (listeAdjacence.get(idCourant).containsKey(idVoisin)) {
                        text += listeAdjacence.get(idCourant).get(idVoisin) + " ";
                    } else {
                        text += "x ";
                    }
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(fichier));
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importGraph() {
        Map<Integer,String> identifiant= new HashMap<>();

        try {
            File fichier = new File("Fichier/test.txt");
            BufferedReader reader = new BufferedReader(new FileReader(fichier));
            int nombreSommet = Integer.parseInt(reader.readLine());
            for (int idCourant = 0;idCourant<nombreSommet;idCourant++) {
                String ligne = reader.readLine();
                String[] voisins =  ligne.split(" ");
                for (int idVoisin = 0;idVoisin<nombreSommet;idVoisin++) {
                    if (!voisins[idVoisin].equals("x")) {
                        this.ajoutArete(idCourant, idVoisin, Integer.parseInt(voisins[idVoisin]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String result;
        String dir;
        if (this.orientation) {
            result = "digraph {\n";
            dir = "-->";
        } else { result = "strict graph {\n"; dir="--"; }
        int id = 0;
        for (int sommetCourant : listeAdjacence.keySet()) {
            for (int sommetVoisin : this.getVoisins(sommetCourant)) {
                result += sommetCourant + " " + dir + " " + sommetVoisin + " [label=\"" + this.getPoids(sommetCourant,sommetVoisin) + "\"]\n";
            }
        }
        result+="}";
        return  result;
    }

    public Map<Integer, Map<Integer, Integer>> getListeAdjacence() {
        return listeAdjacence;
    }

    /*--------------------------- Partie TSP -----------------------------*/

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

    /*public boolean parcoursFini(boolean monTableau[]){ //Fonction pour le nearest qui vérifie si tous les sommets ont été visités
        int sontVisites = 0;
        for(int k = 0; k < monTableau.length; k++){
            if(monTableau[k])
                sontVisites++;
        }
        return sontVisites == monTableau.length;
    }*/

    /*public int nearestNeighbour(int sommetDepart) { //Algorithme que j'ai réalisé afin de pouvoir tester mon 2opt dessus
        System.out.println(" ------Nearest Neighbour---------");
        boolean visited[] = new boolean[listeAdjacence.size()];
        int cheminSolution[] = new int[listeAdjacence.size()];
        int coutSolution = 0;
        int index = 0; //Nous servira d'index pour le tableau cheminSolution
        int memoire = 0; //Sauvegardera l'index du point désiré
        int sommetCourant = sommetDepart;
        while(!parcoursFini(visited)){
            visited[sommetCourant] = true;
            int min = 500;
            for(int j = 0; j < listeAdjacence.size(); j++){
                if(parcoursFini(visited)){ // Si tout les sommets ont été visités, on coupe la boucle afin d'éviter une mauvaise valeur
                    coutSolution += 0;
                    index++;
                    break;

                }
                if(listeAdjacence.get(sommetCourant).get(j) < min && !visited[j] && listeAdjacence.get(sommetCourant).get(j) != 0){ // Si on trouve une arête non visitée et qui ne va pas sur elle-même, le minimum prend sa valeur et on la garde en memoire
                    memoire = j;
                    min = listeAdjacence.get(sommetCourant).get(j);
                }
            }
            if(parcoursFini(visited)){ // Une fois le parcours terminé, on ajoute l'arête qui part du sommet final au sommet de départ
                coutSolution += listeAdjacence.get(sommetCourant).get(sommetDepart);
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
    }*/


    public int cheminEntreDeuxVilles(int sommetDepart, int sommetDestination) { //Fonction qui cherche la longueur du plus petit chemin entre deux points
        int coutChemin = 0;
        boolean trouve = false;
        int min = 500;
        int memoire = 0;
        for(int i = 0; i < listeAdjacence.size(); i++){
            if(trouve){
                break;
            }
            for(int j = 0; j < listeAdjacence.size(); j++){
                if(listeAdjacence.get(i).get(j) == listeAdjacence.get(sommetDestination).get(sommetDepart)){ // Si on trouve l'arête recherchée
                    trouve = true;
                    coutChemin += listeAdjacence.get(i).get(j);
                    break;
                }
                if(listeAdjacence.get(i).get(j) < min && listeAdjacence.get(i).get(j) > 0){
                    min = listeAdjacence.get(i).get(j);
                    memoire = j;
                }
            }
            if(!trouve){
                coutChemin += listeAdjacence.get(i).get(memoire);
            }
        }
        return coutChemin;
    }

    public static double opt2(int sommetsSolution[]) { //Algorithme 2opt que j'applique avec le nearest neighbour
        boolean amelioration = true;
        while(amelioration){ //Tant que l'on peut améliorer la solution
            amelioration = false;
            for(int indi1 = 0; indi1 < sommetsSolution.length; indi1++) { // On parcourt la solution
                for (int indi2 = 2; indi2 < sommetsSolution.length - 1; indi2++) {
                    if(comparerSolutions(indi1,indi2,sommetsSolution)){ //Si on constate une amélioration, on échange les arêtes, voir ligne 146
                        amelioration = true;
                        echangerAretes(indi1,indi2,sommetsSolution); //voir ligne 125
                    }
                }
            }
        }
        System.out.print("Solution 2opt : [");
        for(int i : sommetsSolution){ //On affiche la nouvelle solution
            System.out.print(" " + sommetsSolution[i]);
        }
        System.out.println(" ]");
        return parcours(sommetsSolution); // On retourne la longueur du nouveau chemin, voir ligne 136
    }
    
}
