package graphe;

import java.util.*;

/*
Programme TSP utilisant la méthode greedy Nearest Insertion. Le sommet de départ est choisi random, le second sommet est le sommet le plus proche.
Le sommet de départ détermine la longueur du chemin final, deux même sommet donneront toujours la même longueur finale.
*/
public class greedyNearestInsertion {

    private static int closestVertex(Graphe graph,int nombreSommets) {
        int sommetleplusProche = -1;
        int poidleplusproche = -1;
        for (int idVoisin = 1;idVoisin<nombreSommets;idVoisin++) {
            if (sommetleplusProche == -1 || graph.getPoids(0, idVoisin) < poidleplusproche) {
                sommetleplusProche = idVoisin;
                poidleplusproche = graph.getPoids(0, idVoisin);
            }
        }
        return sommetleplusProche;
    }

    private static int nearestInsert(int id1,int id2,Graphe graph,Map<Integer,Boolean> sommetsVisites) {
        int insertLePlusProche = -1;
        int poidLePlusProche = -1;
        for (int idVoisin : sommetsVisites.keySet()) {
            if (insertLePlusProche == -1 ||
                    ((graph.getPoids(id1,idVoisin)+graph.getPoids(id2,idVoisin))/2< poidLePlusProche)
            ) {
                insertLePlusProche = idVoisin;
                poidLePlusProche = (graph.getPoids(id1,idVoisin)+graph.getPoids(id2,idVoisin))/2;
            }
        }
        return insertLePlusProche;
    }

    public static List<Integer> calculTSP(Graphe graph) {


        int poidTotal = 0;

        List<Integer> cheminTSP = new ArrayList<>(); // Initialisation des listes
        Map<Integer,Boolean> sommetsVisites = new HashMap<>();
        for (int sommet : graph.getSommets()) {
            sommetsVisites.put(sommet,true);
        }

        int depart = (int)(Math.random()*sommetsVisites.size()-1);

        cheminTSP.add(depart); // Initialisation des deux premiers sommets
        sommetsVisites.remove(depart);
        int sommetLePlusProche = greedyNearestInsertion.closestVertex(graph, sommetsVisites.size());
        cheminTSP.add(sommetLePlusProche);
        sommetsVisites.remove(sommetLePlusProche);
        poidTotal += graph.getPoids(0,sommetLePlusProche);

        while (sommetsVisites.size() > 0) {
            int indexLePlusProche = -1;
            int poidLePlusProche = -1;
            int voisinLePlusProche = -1;
            for (int index = 0;index<cheminTSP.size()-1;index++) {
                int idCourant = cheminTSP.get(index);
                int idSuivant = cheminTSP.get(index + 1);
                int meilleurVoisin = greedyNearestInsertion.nearestInsert(idCourant, idSuivant, graph, sommetsVisites);
                if (indexLePlusProche == -1 || (graph.getPoids(idCourant, meilleurVoisin) + graph.getPoids(idSuivant, meilleurVoisin)) < poidLePlusProche) {
                    if (cheminTSP.size() == 2) {
                        poidLePlusProche = graph.getPoids(idCourant, meilleurVoisin) + graph.getPoids(idSuivant, meilleurVoisin) - graph.getPoids(idCourant, idSuivant);
                    } else {
                        poidLePlusProche = graph.getPoids(idCourant, meilleurVoisin) + graph.getPoids(idSuivant, meilleurVoisin) - graph.getPoids(idCourant, idSuivant);
                    }
                    indexLePlusProche = index + 1;
                    voisinLePlusProche = meilleurVoisin;
                }
            }
            cheminTSP.add(indexLePlusProche,voisinLePlusProche);
            sommetsVisites.remove(voisinLePlusProche);
            poidTotal+=poidLePlusProche;
        }

        System.out.println(poidTotal);
        poidTotal = 0;
        for (int i=0;i<cheminTSP.size();i++) {
            if (i < cheminTSP.size()-1) {
                poidTotal+=graph.getPoids(cheminTSP.get(i),cheminTSP.get(i+1));
            } else {
                poidTotal+=graph.getPoids(cheminTSP.get(i),cheminTSP.get(0));
            }
        }
        System.out.println(poidTotal);
        return cheminTSP;
    }

}
