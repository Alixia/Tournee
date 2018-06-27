package algoGrandVoisinage;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;

import outilDeBase.InitialiserModel;
import outilDeBase.ReadData;
import outilDeBase.Route;
import outilDeBase.Solution;
import outilDeBase.SolutionGreedy;
import outilDeBase.Tache;

public class Test2 {
	
	public static void main(String[] args){
		String chemin ="inst1/";
		ReadData.lancerLecture(chemin);
		System.out.println("nbr Tache:"+ReadData.tache+"; nbrTech:"+ReadData.tech+"; nbrDepot:"+ReadData.depot);
		InitialiserModel.initialiser();
		Solution s = new Solution();
		int i =0;
		while(InitialiserModel.tacheAFaire.get(i).nom != 4) {
			i++;
		}
		Tache tache = InitialiserModel.tacheAFaire.get(i);
		Route r = s.sol.get(1);
		Route rnew = SolutionGreedy.evaluerInsertion (r, tache, 2).get(0);
		s.sol.remove(1);
		s.sol.add(1, rnew);
		System.out.println(s.toString());
		i = 0;
		while(InitialiserModel.tacheAFaire.get(i).nom != 6) {
			i++;
		}
		tache = InitialiserModel.tacheAFaire.get(i);
		r = s.sol.get(1);
		//rnew = SolutionGreedy.evaluerInsertion (r, tache, 3).get(0);
		Vector <Route> rs = SolutionGreedy.GetPossibleInsertion(tache, r);
		for (Route route : rs) {
			System.out.println("*******");
			System.out.println(route.toString());
		}
	}
}
