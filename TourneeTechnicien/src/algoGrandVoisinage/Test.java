package algoGrandVoisinage;
import java.security.AlgorithmConstraints;

import Tests.TestInit;
import outilDeBase.*;

public class Test {
	
	public static void initialiser(String chemin){
		ReadData.lancerLecture(chemin);
		InitialiserModel.initialiser();
		DestructionShaw.intialiser(ReadData.tache, TestInit.probaRandom);
		DestructionRandom.intialiser(ReadData.tache, TestInit.probaRandom);
		DestructionPireRegret.intialiser(ReadData.tache, TestInit.probaRandom);
		Similarite.intialiser(TestInit.phi, TestInit.xi, TestInit.psy, TestInit.omega);
	}

	public static void main(String[] args){
		String chemin ="40-40-10/inst1/";
		initialiser(chemin);
		
		System.out.println("nbr Tache:"+ReadData.tache+"; nbrTech:"+ReadData.tech+"; nbrDepot:"+ReadData.depot);
		
		TestInit.tempsDeb = System.currentTimeMillis();
		Solution s = SolutionGreedy.solutionInitiale();
		GrandVoisinage gv = new GrandVoisinage(s, TestInit.r, 4, 3);
		Solution s2 = gv.lancer();
		float tempsEnCours = ((float)(System.currentTimeMillis()-TestInit.tempsDeb)/1000f);
		System.out.println("temps total : " + tempsEnCours + "s");
		gv.afficherMeilleurSolution(true);
		System.out.println(InitialiserModel.tacheAFaire.size());
		System.out.println(s2.costsol);
	}
	
}
