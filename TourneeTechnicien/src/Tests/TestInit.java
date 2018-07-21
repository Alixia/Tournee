package Tests;

import algoGrandVoisinage.DestructionPireRegret;
import algoGrandVoisinage.DestructionRandom;
import algoGrandVoisinage.DestructionShaw;
import algoGrandVoisinage.Similarite;
import outilDeBase.InitialiserModel;
import outilDeBase.ReadData;

public class TestInit {

	public static double probaRandom = 0.6;
	
	public static float tempsMax = 20;
	public static double r = 1;
	public static long tempsDeb;
	
	//sim (shaw)
	public static double phi = 1;
	public static double xi = 1;
	public static double psy = 1;
	public static double omega = 1;
	
	public static int nbDestr = 3;	// random/shaw/regret (0/1/2)
	public static int nbConstr = 5;	// greedy/regret1/regret2/regret3/greedy2 (0/1/2/3/4)
	
	public static int indice = 0;
	
	public static boolean timeout(){
		float tempsEnCours = ((float)(System.currentTimeMillis()-tempsDeb)/1000f);
		if(tempsEnCours>indice) {
			//System.out.println(tempsEnCours);
			indice++;
		}
		return !(tempsEnCours<tempsMax);
	}
	
	public static void initialiser(String chemin){
		ReadData.lancerLecture(chemin);
		InitialiserModel.initialiser();
		DestructionShaw.intialiser(ReadData.tache, probaRandom);
		DestructionRandom.intialiser(ReadData.tache, probaRandom);
		DestructionPireRegret.intialiser(ReadData.tache, probaRandom);
		Similarite.intialiser(phi, xi, psy, omega);
	}
}
