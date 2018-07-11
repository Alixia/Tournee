package algoGrandVoisinage;
import java.security.AlgorithmConstraints;

import outilDeBase.*;

public class Test {
	
	public static int nbDestruction = 4;
	public static double probaRandom = 0.6;
	
	public static float tempsMax = 20;
	public static double r = 1;
	public static long tempsDeb;
	
	//sim (shaw)
	public static double phi = 1;
	public static double xi = 1;
	public static double psy = 1;
	public static double omega = 1;
	
	public static int indice =0;
	
	public static boolean timeout(){
		float tempsEnCours = ((float)(System.currentTimeMillis()-tempsDeb)/1000f);
		if(tempsEnCours>indice) {
			System.out.println(tempsEnCours);
			indice++;
		}
		return !(tempsEnCours<tempsMax);
	}
	
	public static void initialiser(String chemin){
		ReadData.lancerLecture(chemin);
		InitialiserModel.initialiser();
		DestructionShaw.intialiser(nbDestruction, probaRandom);
		DestructionRandom.intialiser(nbDestruction, probaRandom);
		DestructionPireRegret.intialiser(nbDestruction, probaRandom);
		Similarite.intialiser(phi, xi, psy, omega);
	}

	public static void main(String[] args){
		String chemin ="40-40-10/inst1/";
		initialiser(chemin);
		
		System.out.println("nbr Tache:"+ReadData.tache+"; nbrTech:"+ReadData.tech+"; nbrDepot:"+ReadData.depot);
		
		tempsDeb = System.currentTimeMillis();
		Solution s = SolutionGreedy.solutionInitiale();
		GrandVoisinage gv = new GrandVoisinage(s, r);
		Solution s2 = gv.lancer();
		float tempsEnCours = ((float)(System.currentTimeMillis()-tempsDeb)/1000f);
		System.out.println("temps total : " + tempsEnCours + "s");
		gv.afficherMeilleurSolution(true);
		System.out.println(InitialiserModel.tacheAFaire.size());
		System.out.println(s2.costsol);
	}
	
}
