package graphe;

import java.io.*;
import java.util.*;

public class Graphe {
    private boolean orientation;
    private Map<String,Map<String,Integer>> listeAdjacence;

    public Graphe(boolean orientation){
        this.orientation = orientation;
        this.listeAdjacence = new HashMap();
    }

    public Graphe() {
        this(false);
    }

    public void ajoutSommet(String sommet) {
        if (!this.listeAdjacence.containsKey(sommet)) {
            this.listeAdjacence.put(sommet,new HashMap<>());
        }
    }

    public void ajoutArete(String sommetSource, String sommetDestination, int poid) {
        this.ajoutSommet(sommetSource);
        this.ajoutSommet(sommetDestination);
        this.listeAdjacence.get(sommetSource).put(sommetDestination,poid);
        if (!this.orientation) {
            this.listeAdjacence.get(sommetDestination).put(sommetSource,poid);
        }
    }

    public int getPoids(String sommetSource,String sommetDestination) {
        if (this.listeAdjacence.containsKey(sommetSource) && this.listeAdjacence.get(sommetSource).containsKey(sommetDestination)) {
            return this.listeAdjacence.get(sommetSource).get(sommetDestination);
        } else {
            return -1;
        }
    }

    public Set<String> getSommets() {
        return this.listeAdjacence.keySet();
    }

    public Set<String> getVoisins(String sommet) {
        if (this.listeAdjacence.containsKey(sommet)) {
            return this.listeAdjacence.get(sommet).keySet();
        } else {
            return null;
        }
    }

    public void exportGraph() {
        int id = 0;
        Map<Integer,String> identifiant= new HashMap<>();

        for (String sommetCourant : this.listeAdjacence.keySet()) { // Initialiser la table d'identifiants
            identifiant.put(id,sommetCourant);
            id+= 1;
        }

        try {
            File fichier = new File("Fichier/test.txt");
            String text = "";
            for ( int idSommet : identifiant.keySet()) {
                String sommetCourant = identifiant.get(idSommet);
                text += idSommet + "=" + "" + sommetCourant + ";";
            }
            text += "\n";
            BufferedWriter writer = new BufferedWriter(new FileWriter(fichier));
            text += this.listeAdjacence.size() + "\n";
            for (int idCourant : identifiant.keySet()) {
                String sommetCourant = identifiant.get(idCourant);
                for (int idVoisin : identifiant.keySet()) {
                    String sommetVoisin = identifiant.get(idVoisin);
                    if (idVoisin == idCourant) {
                        text += "0 ";
                    } else if (this.listeAdjacence.get(sommetCourant).containsKey(sommetVoisin)) {
                        text += this.listeAdjacence.get(sommetCourant).get(sommetVoisin) + " ";
                    } else {
                        text += "x ";
                    }
                }
                text += "\n";
            }
            writer.write(text);
            writer.close(); //Il faut absolument fermer le writer après utilisation
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importGraph() {
        Map<Integer,String> identifiant= new HashMap<>();

        try {
            File fichier = new File("Fichier/test.txt");
            BufferedReader reader = new BufferedReader(new FileReader(fichier));
            String tableIdentifiants = reader.readLine();
            String[] identifieurs = tableIdentifiants.split(";");
            for ( String identifieur : identifieurs) {
                String[] elements = identifieur.split("=");
                identifiant.put(Integer.parseInt(elements[0]),elements[1]);
            }
            int nombreSommet = Integer.parseInt(reader.readLine());
            for (int i = 0;i<8;i++) {
                String ligne = reader.readLine();
                String[] voisins =  ligne.split(" ");
                int idVoisin = 0;
                for (String poid : voisins ) {
                    if(!poid.equals("x")) {
                        this.ajoutArete(identifiant.get(i),identifiant.get(idVoisin),Integer.parseInt(poid));
                        idVoisin+=1;
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
        Map<String,Integer> identifiant= new HashMap<>();
        for (String sommetCourant : this.listeAdjacence.keySet()) {
            identifiant.put(sommetCourant,id);
            result+=id+"[label="+sommetCourant+"]\n";
            id+= 1;
        }
        for (String sommetCourant : this.listeAdjacence.keySet()) {
            for (String sommetVoisin : this.getVoisins(sommetCourant)) {
                result += identifiant.get(sommetCourant) + " " + dir + " " + identifiant.get(sommetVoisin) + " [label=\"" + this.getPoids(sommetCourant,sommetVoisin) + "\"]\n";
            }
        }
        result+="}";
        return  result;
    }
}
