package graphe;

import java.util.*;

/*
Programme TSP utilisant la méthode greedy Nearest Insertion. Les deux sommets de départ sont random.
La solution est déterminée par les deux premier sommets, les deux même sommets de départ donneront toujours la même solution.
*/
public class greedyNearestInsertion {

    private static int closestVertex(Graphe graph,Map<Integer,Boolean> sommetsVisites,int sommetdepart) {
        int sommetleplusProche = -1;
        int poidleplusproche = -1;
        for (int idVoisin : sommetsVisites.keySet()) {
            int poid = graph.getPoids(sommetdepart, idVoisin);
            if (sommetleplusProche == -1 || poid < poidleplusproche) {
                sommetleplusProche = idVoisin;
                poidleplusproche = poid;
            }
        }
        return sommetleplusProche;
    }

    private static int closestInsert(int id1,int id2,Graphe graph,Map<Integer,Boolean> sommetsVisites) {
        int insertLePlusProche = -1;
        int vertexLePlusProche = -1;
        for (int idVoisin : sommetsVisites.keySet()) {
                int poid = (graph.getPoids(id1,idVoisin)+graph.getPoids(id2,idVoisin))/2; // Distance moyenne
                if (insertLePlusProche == -1 ||
                        (poid < vertexLePlusProche)
                ) {
                    insertLePlusProche = idVoisin;
                    vertexLePlusProche = poid;
                }
        }
        return insertLePlusProche;
    }

    public static List<Integer> calculTSP(Graphe graph) {

        List<Integer> cheminTSP = new ArrayList<>(); // Initialisation des listes
        Map<Integer,Boolean> sommetsVisites = new HashMap<>();
        for (int sommet : graph.getSommets()) {
            sommetsVisites.put(sommet,true);
        }

        int depart = (int)(Math.random()*sommetsVisites.size()-1);// Initialisation des deux premiers sommets
        cheminTSP.add(depart);
        sommetsVisites.remove(depart);

        int sommetLePlusProche = (int)(Math.random()*sommetsVisites.size()-1);
        cheminTSP.add(sommetLePlusProche);
        sommetsVisites.remove(sommetLePlusProche);

        while (sommetsVisites.size() > 0) {
            int indexLePlusProche = -1; // Index du voisin le plus proche dans le keyset
            int vertexLePlusProche = -1; // Distance moyenne du voisin le plus proche
            int idLePlusProche = -1; // id du sommet voisin le plus proche
            for (int index = 0;index<cheminTSP.size();index++) {

                int idCourant = cheminTSP.get(index);
                int idSuivant = (index < cheminTSP.size()-1 ? cheminTSP.get(index+1) : cheminTSP.get(0) );

                int meilleurVoisin = greedyNearestInsertion.closestInsert(idCourant,idSuivant,graph,sommetsVisites);
                int poid = (graph.getPoids(idCourant,meilleurVoisin)+graph.getPoids(idSuivant,meilleurVoisin))/2;

                if (indexLePlusProche == -1 || poid < vertexLePlusProche) {
                    if (cheminTSP.size() < 3) {
                        vertexLePlusProche = poid + graph.getPoids(idCourant,idSuivant);
                    } else {
                        vertexLePlusProche = poid;
                    }
                    indexLePlusProche = index + 1;
                    idLePlusProche = meilleurVoisin;
                }
            }
            cheminTSP.add(indexLePlusProche,idLePlusProche);
            sommetsVisites.remove(idLePlusProche);
        }

        int poidTotal = 0;
        for (int i=0;i<cheminTSP.size();i++) {
            if (i<cheminTSP.size()-1) {
                poidTotal += (graph.getPoids(cheminTSP.get(i),cheminTSP.get(i+1)));
            } else {
                poidTotal += (graph.getPoids(cheminTSP.get(i),cheminTSP.get(0)));
            }
        }
        System.out.println(poidTotal);
        return cheminTSP;
    }

}
