package algoGrandVoisinage;

import java.util.Vector;

import outilDeBase.InitialiserModel;
import outilDeBase.ReadData;
import outilDeBase.Route;
import outilDeBase.Solution;
import outilDeBase.SolutionGreedy;
import outilDeBase.Tache;

public class ConstructionRegret implements AlgoReconstruction {

	@Override
	public Solution reconstruit(Solution soluc) {
		boolean premierTour = true;
		Vector<coupleTmp> FeasRoute= new Vector<>();
		while(!FeasRoute.isEmpty() && !premierTour){
			int i=0;
			Tache tacheMeilleur = null;
			int indiceMeilleur = 0;
			premierTour = false;
			while(i<InitialiserModel.tacheAFaire.size()){
				Tache tache = InitialiserModel.tacheAFaire.get(i);
				Vector<Route> FeasRouteTmp= new Vector<Route>();
				for (int k=0; k<soluc.sol.size();k++){
					if(ReadData.competence[soluc.sol.get(k).tech.nom][tache.nom]==1){
						Route r= soluc.sol.get(k);
						Vector <Route> routess = SolutionGreedy.GetPossibleInsertion(tache, r);
						Route.add2(FeasRouteTmp,routess);
					}
				}
				boolean meilleur = addRegret(FeasRoute, FeasRouteTmp, tache);
				if(meilleur){
					tacheMeilleur = InitialiserModel.tacheAFaire.get(i).clone();
					indiceMeilleur = i;
				}
				i++;
			}
			
			boolean trouv=false;
			int z=0;
			if(!FeasRoute.isEmpty()){
				InitialiserModel.tacheFaite.add(tacheMeilleur);
				InitialiserModel.tacheAFaire.remove(indiceMeilleur);
			}
			while(z<soluc.sol.size() && !trouv && !FeasRoute.isEmpty()){
				if (soluc.sol.get(z).tech.nom==FeasRoute.get(0).feasRoute.tech.nom){
					trouv=true;
					soluc.sol.remove(z);
					soluc.sol.add(z,FeasRoute.get(0).feasRoute);
				}
				z++;
			}
		}
		
		
		
		SolutionGreedy.CalculerLesCouts(soluc);
		return soluc;
	}

	private boolean addRegret(Vector<coupleTmp> feasRoute, Vector<Route> feasRouteTmp, Tache t) {
		// TODO Auto-generated method stub
		boolean meilleur = true;
				
		if(!feasRouteTmp.isEmpty()){
			double cout = 0;
			if(feasRouteTmp.size()==1){
				cout = -t.prio;
			}else{
				cout = feasRouteTmp.get(1).cost-feasRouteTmp.get(0).cost;
			}
			
			int i=0;
			while(i<feasRoute.size() && feasRoute.get(i).cost < cout){
				meilleur = false;
				i++;
			}
			feasRoute.add(i,new coupleTmp(feasRouteTmp.get(0),cout));
		}else{
			meilleur = false;
		}
		
		return meilleur;
	}
	
	private class coupleTmp{
		Route feasRoute;
		double cost;
		public coupleTmp(Route feasRoute, double cost){
			this.feasRoute = feasRoute;
			this.cost = cost;
		}
	}

}
