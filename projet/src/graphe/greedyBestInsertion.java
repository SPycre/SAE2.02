package graphe;

import java.util.*;

public class greedyBestInsertion {

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

    private static int bestInsert(int id1,int id2,Graphe graph,int nombreSommets,Map<Integer,Boolean> sommetsVisites) {
        int insertLePlusProche = -1;
        int poidLePlusProche = -1;
        for (int idVoisin : sommetsVisites.keySet()) {
                if (insertLePlusProche == -1 ||
                        (graph.getPoids(id1,idVoisin)+graph.getPoids(id2,idVoisin)-graph.getPoids(id1,id2) < poidLePlusProche)
                ) {
                    insertLePlusProche = idVoisin;
                    poidLePlusProche = graph.getPoids(id1,idVoisin)+graph.getPoids(id2,idVoisin)-graph.getPoids(id1,id2);
                }
        }
        return insertLePlusProche;
    }

    public static List<Integer> calculTSP(Graphe graph) {

        int poidTotal = 0;

        List<Integer> cheminTSP = new ArrayList(); // Initialisation des listes
        Map<Integer,Boolean> sommetsVisites = new HashMap<>();
        for (int sommet : graph.getSommets()) {
            sommetsVisites.put(sommet,true);
        }

        cheminTSP.add(0); // Initialisation des deux premiers sommets
        sommetsVisites.remove(0);
        int sommetLePlusProche = greedyBestInsertion.closestVertex(graph, sommetsVisites.size());
        cheminTSP.add(sommetLePlusProche);
        sommetsVisites.remove(sommetLePlusProche);
        poidTotal += graph.getPoids(0,sommetLePlusProche);

        while (sommetsVisites.size() > 0) {
            int indexLePlusProche = -1;
            int poidLePlusProche = -1;
            int voisinLePlusProche = -1;
            for (int index = 0;index<cheminTSP.size()-1;index++) {
                int idCourant = cheminTSP.get(index);
                int idSuivant = cheminTSP.get(index+1);
                int meilleurVoisin = greedyBestInsertion.bestInsert(idCourant,idSuivant,graph,sommetsVisites.size(),sommetsVisites);
                if (indexLePlusProche == -1 || (graph.getPoids(idCourant,meilleurVoisin)+graph.getPoids(idSuivant,meilleurVoisin)-graph.getPoids(idCourant,idSuivant)) < poidLePlusProche) {
                    poidLePlusProche = (graph.getPoids(idCourant,meilleurVoisin)+graph.getPoids(idSuivant,meilleurVoisin)-graph.getPoids(idCourant,idSuivant));
                    indexLePlusProche = index+1;
                    voisinLePlusProche = meilleurVoisin;
                }
            }
            cheminTSP.add(indexLePlusProche,voisinLePlusProche);
            sommetsVisites.remove(voisinLePlusProche);
            poidTotal+=poidLePlusProche;
        }

        System.out.println(poidTotal);

        return cheminTSP;
    }

}
