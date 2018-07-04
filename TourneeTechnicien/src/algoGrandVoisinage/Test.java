package algoGrandVoisinage;
import java.security.AlgorithmConstraints;

import outilDeBase.*;

public class Test {
	
	public static int nbDestruction = 4;
	public static int probaRandom = 0;
	
	//sim (shaw)
	public static double phi = 1;
	public static double xi = 1;
	public static double psy = 1;
	public static double omega = 1;
	
	
	public static void initialiser(String chemin){
		ReadData.lancerLecture(chemin);
		InitialiserModel.initialiser();
		DestructionShaw.intialiser(nbDestruction, probaRandom);
		DestructionRandom.intialiser(nbDestruction, probaRandom);
		DestructionPireRegret.intialiser(nbDestruction, probaRandom);
		Similarite.intialiser(phi, xi, psy, omega);
	}

	public static void main(String[] args){
		String chemin ="inst1/";
		initialiser(chemin);
		
		System.out.println("nbr Tache:"+ReadData.tache+"; nbrTech:"+ReadData.tech+"; nbrDepot:"+ReadData.depot);
		
		InitialiserModel.afficher();
		
		Solution s = SolutionGreedy.solutionInitiale();
		System.out.println(s.toString());
		InitialiserModel.afficher();
		
		//Solution soluc = DestructionPireRegret.detruit(s);
		//Solution soluc = DestructionRandom.detruit(s);
		Solution soluc = DestructionShaw.detruit(s);
		
		System.out.println("*******************");
		System.out.println(soluc.toString());
		InitialiserModel.afficher();
		System.out.println("*******************");
		
		//Solution soluc2 = ConstructionGreedy.reconstruit(soluc);
		Solution soluc2 = ConstructionRegret.reconstruit(soluc, 2);
		
		System.out.println(soluc2.toString());
		System.out.println("*******************");
		System.out.println("*******************");
		InitialiserModel.afficher();
	}
	
}
