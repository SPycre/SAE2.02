package graphe;

import java.util.*;

/*
Programme TSP utilisant la méthode greedy Best Insertion. Le sommet de départ est choisi random, le second sommet est le sommet le plus proche.
Le sommet de départ détermine la longueur du chemin final, deux même sommet donneront toujours la même longueur finale.
*/
public class greedyBestInsertion {

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

    private static int cheapestInsert(int id1,int id2,Graphe graph,Map<Integer,Boolean> sommetsVisites) {
        int insertLePlusProche = -1;
        int poidLePlusProche = -1;
        for (int idVoisin : sommetsVisites.keySet()) {
                int poid = graph.getPoids(id1,idVoisin)+graph.getPoids(id2,idVoisin)-graph.getPoids(id1,id2);
                if (insertLePlusProche == -1 ||
                        (poid < poidLePlusProche)
                ) {
                    insertLePlusProche = idVoisin;
                    poidLePlusProche = poid;
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
        int sommetLePlusProche = greedyBestInsertion.closestVertex(graph, sommetsVisites,depart);
        cheminTSP.add(sommetLePlusProche);
        sommetsVisites.remove(sommetLePlusProche);
        poidTotal += graph.getPoids(depart,sommetLePlusProche);

        while (sommetsVisites.size() > 0) {
            int indexLePlusProche = -1;
            int poidLePlusProche = -1;
            int voisinLePlusProche = -1;
            for (int index = 0;index<cheminTSP.size();index++) {

                int idCourant = cheminTSP.get(index);
                int idSuivant = (index < cheminTSP.size()-1 ? cheminTSP.get(index+1) : cheminTSP.get(0) );

                int meilleurVoisin = greedyBestInsertion.cheapestInsert(idCourant,idSuivant,graph,sommetsVisites);
                int poid = (graph.getPoids(idCourant,meilleurVoisin)+graph.getPoids(idSuivant,meilleurVoisin)-graph.getPoids(idCourant,idSuivant));

                if (indexLePlusProche == -1 || poid < poidLePlusProche) {
                    if (cheminTSP.size() < 3) {
                        poidLePlusProche = poid + graph.getPoids(idCourant,idSuivant);
                    } else {
                        poidLePlusProche = poid;
                    }
                    indexLePlusProche = index + 1;
                    voisinLePlusProche = meilleurVoisin;
                }
            }
            cheminTSP.add(indexLePlusProche,voisinLePlusProche);
            sommetsVisites.remove(voisinLePlusProche);
            poidTotal+=poidLePlusProche;
        }

        poidTotal = 0;
        System.out.println(poidTotal);
        return cheminTSP;
    }

}
