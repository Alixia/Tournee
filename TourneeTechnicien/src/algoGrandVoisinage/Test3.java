package algoGrandVoisinage;

import Tests.TestInit;
import outilDeBase.InitialiserModel;
import outilDeBase.ReadData;
import outilDeBase.Route;
import outilDeBase.Solution;
import outilDeBase.SolutionGreedy;
import outilDeBase.Tache;

public class Test3 {

	public static void main(String[] args){
		String chemin ="40-40-10/inst2/";
		TestInit.initialiser(chemin);
		
		System.out.println("nbr Tache:"+ReadData.tache+"; nbrTech:"+ReadData.tech+"; nbrDepot:"+ReadData.depot);
				
		Solution s = new Solution();
		
		Route Newr = s.sol.get(0).clone();
		s.sol.remove(0);
		Tache t = InitialiserModel.tacheAFaire.get(InitialiserModel.findTache(7));
		s.sol.add(0,SolutionGreedy.evaluerInsertion(Newr, t, 1).get(0));
				
		Newr = s.sol.get(0).clone();
		s.sol.remove(0);
		t = InitialiserModel.tacheAFaire.get(InitialiserModel.findTache(1));
		s.sol.add(0,SolutionGreedy.evaluerInsertion(Newr, t, 2).get(0));
				
		Newr = s.sol.get(0).clone();
		s.sol.remove(0);
		t = InitialiserModel.tacheAFaire.get(InitialiserModel.findTache(4));
		s.sol.add(0,SolutionGreedy.evaluerInsertion(Newr, t, 4).get(0));
		
		Newr = s.sol.get(0).clone();
		s.sol.remove(0);
		t = InitialiserModel.tacheAFaire.get(InitialiserModel.findTache(3));
		s.sol.add(0,SolutionGreedy.evaluerInsertion(Newr, t, 6).get(0));
		
		Newr = s.sol.get(0).clone();
		s.sol.remove(0);
		t = InitialiserModel.tacheAFaire.get(InitialiserModel.findTache(9));
		s.sol.add(0,SolutionGreedy.evaluerInsertion(Newr, t, 7).get(0));
		
		Newr = s.sol.get(0).clone();
		s.sol.remove(0);
		t = InitialiserModel.tacheAFaire.get(InitialiserModel.findTache(0));
		s.sol.add(0,SolutionGreedy.evaluerInsertion(Newr, t, 9).get(0));
		
		Newr = s.sol.get(1).clone();
		s.sol.remove(1);
		t = InitialiserModel.tacheAFaire.get(InitialiserModel.findTache(8));
		s.sol.add(1,SolutionGreedy.evaluerInsertion(Newr, t, 1).get(0));
		
		Newr = s.sol.get(1).clone();
		s.sol.remove(1);
		t = InitialiserModel.tacheAFaire.get(InitialiserModel.findTache(6));
		s.sol.add(1,SolutionGreedy.evaluerInsertion(Newr, t, 3).get(0));
		
		Newr = s.sol.get(1).clone();
		s.sol.remove(1);
		t = InitialiserModel.tacheAFaire.get(InitialiserModel.findTache(2));
		s.sol.add(1,SolutionGreedy.evaluerInsertion(Newr, t, 5).get(0));
		
		SolutionGreedy.CalculerLesCouts(s);
		
		System.out.println(s.toString());
		
	}
}
