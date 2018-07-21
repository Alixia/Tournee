package algoGrandVoisinage;

import Tests.TestInit;
import outilDeBase.*;

public class Test2 {

	public static void main(String[] args){
		String chemin ="40-40-10/inst1/";
		TestInit.initialiser(chemin);
		
		
		
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
		Solution soluc2 = ConstructionRegret.reconstruit(soluc, 3);
		
		System.out.println(soluc2.toString());
		System.out.println("*******************");
		System.out.println("*******************");
		//InitialiserModel.afficher();
		
	}
	
}
