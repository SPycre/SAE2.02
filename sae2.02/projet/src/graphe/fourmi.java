package graphe;

public class fourmi {

    //la taille du tour représente la taille de tout les arrets du graphes
    public double tailleDuTour;
    // le tableau tour stocke tout les sommets parcourus par la fourmi
    public int[] tour;
    // le tableau visited indique quelle sommets a été visiter ou pas
    public boolean[] visited;

    Environement environements;

    public fourmi(int size,Environement environements) {
        this.environements = environements;
        this.tour=new int[size+1];
        this.visited=new boolean[size];
    }
    // retourne la taille du graphe
    public int taille_graphe() {
        int taille = 0;
        taille=environements.graphes[0].getSommets().size();
        return taille;
    }
   // Retourner le sommet pour une étape spécifique du tour
    public int gettour(int etape){
        return tour[etape];
    }
    //retourner le tour
    public int[] getTour() {
        return tour;
    }
    //permet de supprimer tout les sommet visiter
    public void SupprimerLesSommetsvisiter() {
        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }
    }
    //permet de commencer le tour a partir d'un sommet random
    public void commencerLeTour(int etape) {
        tour[etape]= (int) (Math.random() * taille_graphe());
        visited[tour[etape]] = true;
    }
    //permet d'aller au voisin avec la plus petite distance
    public void AllerAuSommetPlusProche(int etape){
        // Commencer à considérer le dernier mouvement
        int SommetProchain=taille_graphe();
        // prendre le sommet actuel
        int SommetActuel=tour[etape-1];
        // commencer avec une valeur qu'une arrete ateindra jamais
        double DistanceMinimum=10000.0;
        // Pour chaque sommet non visité, si le coût est inférieur à la distance minimale, sélectionnez-le.
        for (int SommetDestination=0;SommetDestination<taille_graphe();SommetDestination++){
            if (visited[SommetDestination]==false&& environements.graphes[0].getPoids(SommetActuel,SommetDestination)<DistanceMinimum){
                SommetProchain=SommetDestination;
                DistanceMinimum=environements.graphes[0].getPoids(SommetActuel,SommetDestination);
            }
        }
        // aller au prochain sommet
        tour[etape]=SommetProchain;
        visited[SommetProchain]=true;
    }
    //permet de compter le tour parcourus par la fourmis
    public double compterLeTour(){
        double tailletour=0.0;
        for(int i=0;i<taille_graphe();i++){
            tailletour = tailletour +environements.graphes[0].getPoids(tour[i], tour[i + 1]);
        }
        return tailletour ;
    }
// permet de finir le tour avec le sommet du début
    public void FinDuTour(){
        tour[taille_graphe()]=tour[0];
       // tailleDuTour=compterLeTour();
    }

    // calcul le poids du chemin parcourut par la fourmi en commençant par un sommet aléatoire ensuite la fourmi choisi le voisin le plus proche
   public double LePoidsDuCheminParcourutParLaFourmi(){
        int etape=0;
        this.SupprimerLesSommetsvisiter();
        this.commencerLeTour(etape);
        while(etape<taille_graphe()-1){
            etape++;
            this.AllerAuSommetPlusProche(etape);
        }
        this.FinDuTour();
        this.SupprimerLesSommetsvisiter();
        return this.tailleDuTour;
    }
    //Sélectionner le meilleur voisin non visité du sommet actuel
    public void AllerAuMeilleurVoisin(int etape){
        int sommet;
        int sommetprochain=taille_graphe();
        int sommetActuel=this.tour[etape-1];
        double meilleurvaleur=-1.0;
        //double x;
        //selection le voisin non visiter avec le meilleur cout
        for (int i=0;i<taille_graphe()-1;i++){
           sommet=environements.getChoixinfo(sommetActuel,i);
            if(visited[sommet]==false){
                environements.graphes[0].getPoids(sommetActuel,sommet);
                sommetprochain=sommet;
                tour[etape]=sommetprochain;
                visited[this.tour[etape]]=true;

              /*  if(x>meilleurvaleur){
                    meilleurvaleur=x;

                }*/
            }
        }
        /*if(sommetprochain==taille_graphe()){
            AllerAuSommetPlusProche(etape);
        }*/
     //   else {

      //  }
    }
    //Calculer le prochain voisin en sélectionnant de manière probabiliste l'arret avec plus de phéromone et une plus petite distance,pour cela on
    //essai de touvé un sommet  à déplacer dans la liste des voisins les plus proches, mais si tous les somemt ont été visités
    // alors on sélectionne le meilleur dans tous les voisins restants.

    public void AllerAuVoisinChoisit(int etape) {
        int SommetActuel = this.tour[etape - 1];
        Double SommeProbabilite = 0.0;
        double[] selectionnerProbabilite = new double[taille_graphe()];
        // Pour chaque sommet voisin le plus proche qui n'a pas encore été visité l'ajouter dans le tableau
        for (int i = 0; i < taille_graphe() - 1; i++) {
            if (visited[environements.getChoixinfo(SommetActuel, i)] == true) {
                selectionnerProbabilite[i] = 0.0;
            } else {
                selectionnerProbabilite[i] = environements.graphes[0].getPoids(SommetActuel, environements.getChoixinfo(SommetActuel, i));
                SommeProbabilite = SommeProbabilite + selectionnerProbabilite[i];
            }
        }
        // Si tous les voisins les plus proches ont été visités, sélectionnez le voisin le plus proche des  voisins restants.
        if (SommeProbabilite <= 0) {
            AllerAuSommetPlusProche(etape);
        } else {
            double random = Math.random() * SommeProbabilite;
            int i = 0;
            double proba = selectionnerProbabilite[i];
            // Sélectionne le voisin correspondant à la probabilité proportionnelle aléatoire.
            while (proba <= random) {
                i++;
                proba = proba + selectionnerProbabilite[i];
            }
            if (i == taille_graphe() - 1) {
                AllerAuMeilleurVoisin(etape);
            }

            //visiter le voisin selectionner
            tour[etape] = environements.getChoixinfo(SommetActuel, i);
            visited[this.tour[etape]] = true;
        }
    }
    }













