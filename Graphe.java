package TP6;

import javax.swing.*;
import java.util.*;

public class Graphe<E> {
    private boolean orientation;
    private Map<E,Map<E,Integer>> listeAdjacence;

    public Graphe(boolean orientation){
        this.orientation = orientation;
        this.listeAdjacence = new HashMap<>();
    }

    public Graphe() {
        this(false);
    }

    public void ajoutSommet(E sommet) {
        if (!this.listeAdjacence.containsKey(sommet)) {
            this.listeAdjacence.put(sommet,new HashMap<>());
        }
    }

    public void ajoutArete(E sommetSource, E sommetDestination, int poid) {
        this.ajoutSommet(sommetSource);
        this.ajoutSommet(sommetDestination);
        this.listeAdjacence.get(sommetSource).put(sommetDestination,poid);
        if (!this.orientation) {
            this.listeAdjacence.get(sommetDestination).put(sommetSource,poid);
        }
    }

    public int getPoids(E sommetSource,E sommetDestination) {
        if (this.listeAdjacence.containsKey(sommetSource) && this.listeAdjacence.get(sommetSource).containsKey(sommetDestination)) {
            return this.listeAdjacence.get(sommetSource).get(sommetDestination);
        } else {
            return -1;
        }
    }

    public Set<E> getSommets() {
        return this.listeAdjacence.keySet();
    }

    public Set<E> getVoisins(E sommet) {
        if (this.listeAdjacence.containsKey(sommet)) {
            return this.listeAdjacence.get(sommet).keySet();
        } else {
            return null;
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
        Map<E,Integer> identifiant= new HashMap<>();
        for (E sommetCourant : this.listeAdjacence.keySet()) {
            identifiant.put(sommetCourant,id);
            result+=id+"[label="+sommetCourant+"]\n";
            id+= 1;
        }
        for (E sommetCourant : this.listeAdjacence.keySet()) {
            for (E sommetVoisin : this.getVoisins(sommetCourant)) {
                result += identifiant.get(sommetCourant) + " " + dir + " " + identifiant.get(sommetVoisin) + " [label=\"" + this.getPoids(sommetCourant,sommetVoisin) + "\"]\n";
            }
        }
        result+="}";
        return  result;
    }
}
