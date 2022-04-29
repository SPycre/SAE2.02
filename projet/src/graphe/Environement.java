package graphe;
import java.util.Scanner;
import java.util.*;

public class Environement {
    public Double[][] pheromone;
    public Double[][] LeChoixDuChemin;
    public int nbrFourmi=20;
    private int [][] infoChoisit=new int[][]{};
    double meilleursommet = 10000000.0;
    int[] meilleurtour=new int[]{};
    int alpha;
    int beta;
    public  fourmi[]fourmis;
    Graphe Graphes;
    Graphe[] graphes = new Graphe[]{Graphes};

    public Environement(Graphe Graphes) {
        this.graphes = graphes;
        graphes[0]=Graphes;

    }
    public void EntrerLesValeurs(){
        System.out.println(" --------------------------------------------------------------  ");
        Scanner scan = new Scanner(System.in);
        System.out.println(" Veuillez Entrer une valeur d'alpha supérieur à 0");
            alpha=scan.nextInt();
        System.out.println(" Veuillez Entrer une valeur de beta supérieur à 0");
            beta=scan.nextInt();
        scan.close();
    }
    //return une fourmi
    public fourmi[] getFourmis() {
        return fourmis;
    }

    public int taille_graphe() {
        int taille = 0;
        taille=graphes[0].getSommets().size();
        return taille;
    }

    //cette methode permet de crée k fourmis qui vont chercher une solution dans l'environement
    public void PopulationFourmi(){
        this.fourmis=new fourmi[nbrFourmi];
        for (int k=0;k<nbrFourmi;k++){
            fourmis[k]=new fourmi(taille_graphe(),this);

        }
    }
    //Stocke dans le tableau les voisin les plus proche
    public void VoisinPlusProche() {
        this.infoChoisit = new int[taille_graphe()][taille_graphe()-1];
        int[] sommet = new int[taille_graphe()];
        for(int i = 0; i < taille_graphe(); i++) {
            for(int j = 0; j < taille_graphe(); j++) {
                sommet[j] = j;
            }
            for(int r = 0; r < taille_graphe()-1; r++) {
                infoChoisit[i][r] = sommet[r];
            }
        }
    }
   // Retourne le plus proche voisin de la position de l'indice de rang

    public int getinfoChoisit(int sommet , int index) {
        return infoChoisit[sommet][index];
    }



//  Calculer la probabilité proportionnelle d'une fourmi
//  en se basant sur le coût de l'arête entre un sommet et son voisin  et
//  et la quantité de phéromone de l'arête
    public void CalculerlaProbabilite() {

        for (int i = 0; i < taille_graphe(); i++) {
            for (int j = 0; j < taille_graphe()-1; j++) {
               double x=(1.0 / (graphes[0].getPoids(i, j)));
                LeChoixDuChemin[i][j] = (Math.pow(pheromone[i][j], (alpha)) * Math.pow(x, beta));
                LeChoixDuChemin[j][i] = LeChoixDuChemin[i][j];
            }
        }
    }

    // Pour générer l'environnement, la phéromone est initialisée en tenant compte du coût de la tournée du plus proche voisin.
    public void LePheromoneInitial(){
        pheromone= new Double[taille_graphe()][taille_graphe()];
        LeChoixDuChemin= new Double[taille_graphe()][taille_graphe()];
        Double QuantiteInitialPheromone=1.0/(fourmis[0].LePoidsDuCheminParcourutParLaFourmi());
        for(int i=0;i<taille_graphe();i++){
            for(int j=i;j<taille_graphe();j++)
            {
                pheromone[i][j]=QuantiteInitialPheromone;
                pheromone[j][i]=QuantiteInitialPheromone;
            }
        }
        this.CalculerlaProbabilite();

    }
    public void Evaporation_Pheromone(){
        for(int i=0;i<taille_graphe();i++){
            for(int j=i;j< taille_graphe();j++){
                pheromone[j][i]=pheromone[i][j];
            }
        }
    }
    // Pour la fourmi, déposer la quantité de phéromone dans toutes les arêtes utilisées par la fourmi où
    //  la quantité de phéromone déposée est proportionnelle à la qualité de la solution
    public void  depospheromone(fourmi F1){
        double to;
        to=1.0/F1.taille_graphe();
        for(int i=0;i<taille_graphe();i++){
            int a = F1.gettour(i);
            int b=F1.gettour(i+1);
            pheromone[a][b]=pheromone[a][b]+to;
            pheromone[b][a]=pheromone[a][b];
        }
    }
    // permet de mettre à jour la phéromone en tenant compte de la qualité des solutions construites
     // par les fourmis et du taux d'évaporation
    public void MiseAJourDuPheromone(){
        this.Evaporation_Pheromone();
        for(int k=0;k<nbrFourmi;k++){
            depospheromone(fourmis[k]);
        }
        this.CalculerlaProbabilite();
    }




// permet de construire la solution par les fourmis
    public void LaSolutionConstruitParLaFourmi(){
        int etape=0;
        for(int k=0;k<nbrFourmi;k++){
            fourmis[k].SupprimerLesSommetsvisiter();
            fourmis[k].commencerLeTour(etape);
        }
        //Toutes les fourmis choisissent le prochain sommet non visité en se basant sur les pistes de phéromones
        while(etape<taille_graphe()-1){
            etape++;
            for(int k=0;k<nbrFourmi;k++){
                fourmis[k].AllerAuVoisinChoisit(etape);
            }
        }
        for(int k=0;k<nbrFourmi;k++){
            fourmis[k].FinDuTour();
        }
    }




//permet d'afficher le plus court chemin et le poids du tour
    public void affichage(int etape) {
        double x = -1.0;
        double tour = 0.0;
        fourmi MeilleurFourmi = null;
        for (fourmi Fourmiss : getFourmis()) {
            MeilleurFourmi = Fourmiss;
            tour = tour +Fourmiss.taille_graphe();
        }
           if (x < meilleursommet) {
                meilleursommet = x;
                meilleurtour = MeilleurFourmi.getTour();
                String affichage = " le plus cours chemin est : [" + meilleurtour[0];
                for (int i = 1; i < meilleurtour.length; i++) {
                    affichage = affichage + "-" + meilleurtour[i];
                }
                affichage = affichage + "]";
                System.out.println(affichage + "\n");
                System.out.println(" Le poids du tour parcourus par les fourmis est :" + tour);
                System.out.println(" -------------------------------------------------------------- ");
            }





    }

}
