package algoGrandVoisinage;

import java.util.Vector;

import outilDeBase.InitialiserModel;
import outilDeBase.ReadData;
import outilDeBase.Route;
import outilDeBase.Solution;
import outilDeBase.SolutionGreedy;
import outilDeBase.Tache;

public class ConstructionGreedy {
	
	public static Solution reconstruit(Solution soluc) {
		int i=0;
		while(i<InitialiserModel.tacheAFaire.size()){
			Tache tache = InitialiserModel.tacheAFaire.get(i);
			Vector<Solution> FeasRoute= new Vector<Solution>();
			for (int k=0; k<soluc.sol.size();k++){
				if(ReadData.competence[soluc.sol.get(k).tech.nom][tache.nom]==1){
					Route r = soluc.sol.get(k);
					Vector <Route> routess = SolutionGreedy.GetPossibleInsertion(tache, r);
					Vector <Solution> routess2 = SolutionGreedy.creerSolutions(soluc, routess, k, 1);
					Solution.add1(FeasRoute,routess2);
				}
			}
			
			if(!FeasRoute.isEmpty()){
				InitialiserModel.tacheFaite.add(tache);
				InitialiserModel.tacheAFaire.remove(i);
				soluc = FeasRoute.get(0);
				i--;
			}
			soluc.Calcul_costsol();
			i++;
		}
		
		SolutionGreedy.CalculerLesCouts(soluc);
		return soluc;
	}

}
