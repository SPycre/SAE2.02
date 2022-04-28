package graphe;

public class Lancement {
    public Environement environement ;
    public Lancement(Environement environement) {
        this.environement = environement;
    }

    public void lancement() {
        environement.EntrerLesValeurs();
        environement.VoisinPlusProche();
        environement.PopulationFourmi();
        environement.LePheromoneInitial();
        for(int i=0;i<800;i++){
            environement.LaSolutionConstruitParLaFourmi();
            environement.MiseAJourDuPheromone();
            environement.affichage(i);

        }


    }

}
