package algoGrandVoisinage;

import java.util.Vector;

import outilDeBase.InitialiserModel;
import outilDeBase.ReadData;
import outilDeBase.Route;
import outilDeBase.Solution;
import outilDeBase.SolutionGreedy;
import outilDeBase.Tache;
import outilGeneral.GestionTableau;

public class ConstructionGreedy2 {
	
	public static Solution reconstruit(Solution soluc) {
		Vector<Tache> trieTache = trierTache();
		int i=0;
		while(i<trieTache.size()){
			Tache tache = trieTache.get(i);
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
				InitialiserModel.tacheAFaire = GestionTableau.removeNom(InitialiserModel.tacheAFaire, tache.nom);
				soluc = FeasRoute.get(0);
				trieTache.remove(i);
				i--;
			}
			i++;
			soluc.Calcul_costsol();
		}
		
		SolutionGreedy.CalculerLesCouts(soluc);
		return soluc;
	}

	private static Vector<Tache> trierTache() {

		Vector<Tache> retour = new Vector<>();
		for (Tache tache : InitialiserModel.tacheAFaire) {
			InitialiserModel.addCompetence(retour, tache);
		}

		return retour;
	}


}
