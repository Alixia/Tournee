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
			Vector<Route> FeasRoute= new Vector<Route>();
			for (int k=0; k<soluc.sol.size();k++){
				if(ReadData.competence[soluc.sol.get(k).tech.nom][tache.nom]==1){
					Route r= soluc.sol.get(k);
					Vector <Route> routess = SolutionGreedy.GetPossibleInsertion(tache, r);
					Route.add1(FeasRoute,routess);
				}
			}
			
			boolean trouv=false;
			int z=0;
			if(!FeasRoute.isEmpty()){
				InitialiserModel.tacheFaite.add(tache);
				InitialiserModel.tacheAFaire.remove(i);
				i--;
			}
			while(z<soluc.sol.size() && !trouv && !FeasRoute.isEmpty()){
				if (soluc.sol.get(z).tech.nom==FeasRoute.get(0).tech.nom){
					trouv=true;
					soluc.sol.remove(z);
					soluc.sol.add(z,FeasRoute.get(0));
				}
				z++;
			}
			i++;
		}
		
		SolutionGreedy.CalculerLesCouts(soluc);
		return soluc;
	}

}
