package algoGrandVoisinage;

import java.util.Vector;

import Tests.TestInit;
import outilDeBase.InitialiserModel;
import outilDeBase.ReadData;
import outilDeBase.Route;
import outilDeBase.Solution;
import outilDeBase.SolutionGreedy;
import outilDeBase.Tache;

public class ConstructionRegret {

	public static Solution reconstruit(Solution soluc, int nbRegret) {
		boolean premierTour = true;
		while(!InitialiserModel.tacheAFaire.isEmpty() && premierTour && !TestInit.timeout()){
			Vector<coupleTmp> FeasRoute= new Vector<>();
			int i=0;
			Tache tacheMeilleur = null;
			int indiceMeilleur = 0;
			// on cherche lecart de regret le plus important
			while(i<InitialiserModel.tacheAFaire.size()){
				Tache tache = InitialiserModel.tacheAFaire.get(i);
				Vector<Solution> FeasRouteTmp= new Vector<Solution>();
				for (int k=0; k<soluc.sol.size();k++){
					if(ReadData.competence[soluc.sol.get(k).tech.nom][tache.nom]==1){
						Route r= soluc.sol.get(k);
						Vector <Route> routess = SolutionGreedy.GetPossibleInsertion(tache, r);
						Vector <Solution> routess2 = new Vector<>();
						if(nbRegret == 1) {
							routess2 = SolutionGreedy.creerSolutions(soluc, routess, k, 1);
							Solution.add1(FeasRouteTmp,routess2);
						}if(nbRegret == 2) {
							routess2 = SolutionGreedy.creerSolutions(soluc, routess, k, 2);
							Solution.add2(FeasRouteTmp,routess2);
						}if(nbRegret == 3) {
							routess2 = SolutionGreedy.creerSolutions(soluc, routess, k, 3);
							Solution.add3(FeasRouteTmp,routess2);
						}
					}
				}
				boolean meilleur = true;
				if(nbRegret == 1) {
					meilleur = addRegret1(FeasRoute, FeasRouteTmp, tache);
				}if(nbRegret == 2) {
					meilleur = addRegret2(FeasRoute, FeasRouteTmp, tache);
				}if(nbRegret == 3) {
					meilleur = addRegret3(FeasRoute, FeasRouteTmp, tache);
				}
				if(meilleur){
					tacheMeilleur = InitialiserModel.tacheAFaire.get(i).clone();
					indiceMeilleur = i;
				}
				i++;
			}
			
			if(!FeasRoute.isEmpty()){
				InitialiserModel.tacheFaite.add(tacheMeilleur);
				InitialiserModel.tacheAFaire.remove(indiceMeilleur);
				soluc = FeasRoute.get(0).feasRoute;
			}else {
				// on arrete si on ne trouve pas de solution a inserer
				premierTour = false;
			}
			
			soluc.Calcul_costsol();
		}
		
		
		
		SolutionGreedy.CalculerLesCouts(soluc);
		return soluc;
	}

	private static boolean addRegret2(Vector<coupleTmp> feasRoute, Vector<Solution> feasRouteTmp, Tache t) {
		// TODO Auto-generated method stub
		boolean meilleur = true;
				
		if(!feasRouteTmp.isEmpty()){
			double cout = 0;
			if(feasRouteTmp.size()==1){
				cout = -t.prio*100;
			}else{
				cout = feasRouteTmp.get(1).costsol-feasRouteTmp.get(0).costsol;
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
	
	private static boolean addRegret1(Vector<coupleTmp> feasRoute, Vector<Solution> feasRouteTmp, Tache t) {
		// TODO Auto-generated method stub
		boolean meilleur = true;
				
		if(!feasRouteTmp.isEmpty()){
			double cout = 0;
			if(feasRouteTmp.size()==1){
				cout = -t.prio*1000;
			}else{
				cout = feasRouteTmp.get(0).costsol/100;
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
	
	private static boolean addRegret3(Vector<coupleTmp> feasRoute, Vector<Solution> feasRouteTmp, Tache t) {
		// TODO Auto-generated method stub
		boolean meilleur = true;
				
		if(!feasRouteTmp.isEmpty()){
			double cout = 0;
			if(feasRouteTmp.size()==1){
				cout = -t.prio*1000;
			}else if(feasRouteTmp.size()==2){
				cout = feasRouteTmp.get(1).costsol-feasRouteTmp.get(0).costsol;
			}else {
				cout = feasRouteTmp.get(2).costsol-feasRouteTmp.get(0).costsol;
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
	
	private static class coupleTmp{
		Solution feasRoute;
		double cost;
		public coupleTmp(Solution feasRoute, double cost){
			this.feasRoute = feasRoute;
			this.cost = cost;
		}
	}

}
