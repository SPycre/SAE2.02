package graphe;

import java.io.*;
import java.util.*;

public class Graphe {
    private final boolean orientation;
    private final Map<Integer,Map<Integer,Integer>> listeAdjacence; // Map<SommetOrigine,Map<SommetVoisin,poid>>

    public Graphe(boolean orientation){
        this.orientation = orientation;
        this.listeAdjacence = new HashMap();
    }

    public Graphe() {
        this(false);
    }

    public void ajoutSommet(int sommet) {
        if (!this.listeAdjacence.containsKey(sommet)) {
            this.listeAdjacence.put(sommet,new HashMap<>());
        }
    }

    public void ajoutArete(int sommetSource, int sommetDestination, int poid) {
        this.ajoutSommet(sommetSource);
        this.ajoutSommet(sommetDestination);
        this.listeAdjacence.get(sommetSource).put(sommetDestination,poid);
        if (!this.orientation) {
            this.listeAdjacence.get(sommetDestination).put(sommetSource,poid);
        }
    }

    public int getPoids(int sommetSource,int sommetDestination) {
        if (this.listeAdjacence.containsKey(sommetSource) && this.listeAdjacence.get(sommetSource).containsKey(sommetDestination)) {
            return this.listeAdjacence.get(sommetSource).get(sommetDestination);
        } else {
            return -1;
        }
    }

    public Set<Integer> getSommets() {
        return this.listeAdjacence.keySet();
    }

    public Set<Integer> getVoisins(int sommet) {
        if (this.listeAdjacence.containsKey(sommet)) {
            return this.listeAdjacence.get(sommet).keySet();
        } else {
            return null;
        }
    }

    public void exportGraph() {
        int id = 0;

        try {
            File fichier = new File("Fichier/test.txt");
            String text = "";

            text += this.listeAdjacence.size() + "\n";
            for (int idCourant = 0;idCourant<this.listeAdjacence.size();idCourant++) {
                for (int idVoisin = 0;idVoisin<this.listeAdjacence.size();idVoisin++) {
                    if (idVoisin == idCourant) {
                        text += "0 ";
                    } else if (this.listeAdjacence.get(idCourant).containsKey(idVoisin)) {
                        text += this.listeAdjacence.get(idCourant).get(idVoisin) + " ";
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
                String[] voisins = ligne.split(" ");
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
        for (int sommetCourant : this.listeAdjacence.keySet()) {
            for (int sommetVoisin : this.getVoisins(sommetCourant)) {
                result += sommetCourant + " " + dir + " " + sommetVoisin + " [label=\"" + this.getPoids(sommetCourant,sommetVoisin) + "\"]\n";
            }
        }
        result+="}";
        return  result;
    }
}
