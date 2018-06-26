package algoGrandVoisinage;
import java.security.AlgorithmConstraints;

import outilDeBase.*;

public class Test {
	
	public static int nbDestruction = 4;

	public static void main(String[] args){
		String chemin ="inst1/";
		ReadData D = new ReadData(chemin);
		D.lancerLecture();
		System.out.println("nbr Tache:"+D.tache+"; nbrTech:"+D.tech+"; nbrDepot:"+D.depot);
		
		InitialiserModel.initialiser();
		InitialiserModel.afficher();
		Similarite.intialiser(1, 1, 1, 1);
		
		Solution s = SolutionGreedy.solutionInitiale();
		System.out.println(s.toString());
		InitialiserModel.afficher();
		//AlgoDestruction dr = new DestructionRandom(4);
		//AlgoDestruction dr = new DestructionPireRegret(4, 0);
		AlgoDestruction dr = new DestructionShaw(4, 1);
		AlgoReconstruction rg = new ConstructionRegret();
		Solution soluc = dr.detruit(s);
		System.out.println("*******************");
		System.out.println(soluc.toString());
		InitialiserModel.afficher();
		System.out.println("*******************");
//		Solution soluc2 = rg.reconstruit(soluc);
//		System.out.println(soluc2.toString());
//		System.out.println("*******************");
//		System.out.println("*******************");
//		InitialiserModel.afficher();
	}
	
}
