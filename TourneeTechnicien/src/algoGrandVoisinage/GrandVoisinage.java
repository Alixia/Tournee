package algoGrandVoisinage;

import java.util.Random;

import outilDeBase.InitialiserModel;
import outilDeBase.Solution;
import outilGeneral.HashagePerso;

public class GrandVoisinage {
	
	Solution meilleurSolution;
	int nbIteration;
	long timeDeb;
	HashagePerso hashP;
	double r;
	Choix constr;
	Choix destr;
	double sigma1 = 15;		// gain pour le choix des heuristique
	double sigma2 = 10;
	int nbSegment;
		
	public GrandVoisinage(Solution s, double facteurDeRegression) {
		meilleurSolution = s;
		hashP = new HashagePerso();
		hashP.addP(s.hashCode());
		r = facteurDeRegression;
		constr = new Choix(4); 	// greedy/regret1/regret2/regret3 (0/1/2/3)
		destr = new Choix(3); 	// random/shaw/regret (0/1/2)
		reinitialise();
		nbSegment = 0;
	}
	
	public Solution lancer() {
		
		timeDeb = System.currentTimeMillis();
		Solution s = meilleurSolution.clone();
		
		while(!Test.timeout()) {
			
			//lancement des heuristic const/dest selon les poids
			Couple c = lancerDestruction(s);
			int numHeuristicDest = c.numHeuristic;
			s = c.s;
			c = lancerConstruction(s);
			int numHeuristicConst = c.numHeuristic;
			s = c.s;
									
			boolean nouvelleSolution = hashP.addP(s.hashCode());
			if(s.costsol < meilleurSolution.costsol) {
				meilleurSolution = s.clone();
				
				constr.score[numHeuristicConst] += sigma1;
				
				destr.score[numHeuristicDest] += sigma1;
			}else if(nouvelleSolution) {
				constr.score[numHeuristicConst] += sigma2;
				
				destr.score[numHeuristicDest] += sigma2;
			}
			constr.nb[numHeuristicConst] ++;
			destr.nb[numHeuristicDest] ++;
			
			nbIteration++;
			if(nbIteration == 100) {
				nbSegment ++;
				calculPoid();
				reinitialise();
			}
		}
		InitialiserModel.mettreAJourSelonSolution(meilleurSolution);
		return meilleurSolution;
	}

	public void afficherMeilleurSolution(boolean touteLaSolution) {
		
		if(touteLaSolution) {
			System.out.println("*******************");
			System.out.println(meilleurSolution.toString());
			System.out.println("*******************");
		}else {
			System.out.println(meilleurSolution.costsol + "");
		}
		
	}
	
	private Couple lancerConstruction(Solution s) {
		
		Solution retour = null;
		int numHeuristic = constr.selectHeuristic();
		switch (numHeuristic) {
		case 0:				// greedy
			retour = ConstructionGreedy.reconstruit(s);
			break;
			
		case 1 : 			//regret1
			retour = ConstructionRegret.reconstruit(s, 1);
			break;
			
		case 2 : 			//regret2
			retour = ConstructionRegret.reconstruit(s, 2);
			break;

		case 3 : 			//regret3
			retour = ConstructionRegret.reconstruit(s, 3);
			break;

		default:
			break;
		}
		return new Couple(retour, numHeuristic);

		
	}
	
	private Couple lancerDestruction(Solution s) {
		
		Solution retour = null;
		int numHeuristic = destr.selectHeuristic();
		switch (numHeuristic) {
		case 0:  			// random
			retour = DestructionRandom.detruit(s);
			break;
			
		case 1 :			//shaw
			retour = DestructionShaw.detruit(s);
			break;
			
		case 2 :			//regret
			retour = DestructionPireRegret.detruit(s);
			break;

		default:
			break;
		}
		return new Couple(retour, numHeuristic);
		
	}
	
	private void reinitialise() {
		constr.reinitialise();
		destr.reinitialise();
		nbIteration = 0;
	}
	
	private void calculPoid() {
		constr.calculPoid();
		destr.calculPoid();
	}
	
	private class Choix{
		int[] score;
		int[] nb;
		double[] poid;
		double[] proba;
		
		public Choix(int nbHeuristic) {
			score = new int[nbHeuristic];
			nb = new int[nbHeuristic];
			poid = new double[nbHeuristic];
			proba = new double[nbHeuristic];
			for(int i=0 ; i<poid.length ; i++) {
				proba[i] = 1./nbHeuristic;
				poid[i] = 1;
			}
		}
		
		public void reinitialise() {
			for(int i=0 ; i<score.length ; i++) {
				score[i] = 0;
				nb[i] = 0;
			}
		}
		
		public int selectHeuristic() {
			Random r = new Random();
			double y = r.nextDouble();
			double sommeProba = 0.;
			for(int i=0 ; i<score.length ; i++) {
				sommeProba += proba[i];
				if(sommeProba > y) {
					return i;
				}
			}
			return 0;
		}
		
		public void calculPoid() {
			double sommePoid = 0;
			for(int i=0 ; i<poid.length ; i++) {
				if(nb[i] != 0) {
					poid[i] = poid[i]*(1-r) + r*(score[i]/nb[i]);
				}else {
					poid[i] = poid[i]*(1-r);
				}
				sommePoid += poid[i];
			}
			for(int i=0 ; i<poid.length ; i++) {
				proba[i] = poid[i]/sommePoid;
			}
		}
	}
	
	private class Couple{
		Solution s;
		int numHeuristic;
		
		public Couple(Solution s, int numHeuristic) {
			this.s = s;
			this.numHeuristic = numHeuristic;
		}
	}
	
}
