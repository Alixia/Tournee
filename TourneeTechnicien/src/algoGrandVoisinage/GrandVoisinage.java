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
	double sigma3 = 5;
	int nbSegment;

	
	//critere arret
	boolean arret;
	boolean arretSegment;
	int tourMax = 5;
	int nbSegmentsArret;
	int nbTour;
	
	
	//critere d'acceptation
	double c = 0.2; //vitesse de refroidissement
	double T; //temperature
		
	public GrandVoisinage(Solution s, double facteurDeRegression) {
		meilleurSolution = s;
		hashP = new HashagePerso();
		hashP.addP(s.hashCode());
		r = facteurDeRegression;
		constr = new Choix(4); 	// greedy/regret1/regret2/regret3 (0/1/2/3)
		destr = new Choix(3); 	// random/shaw/regret (0/1/2)
		reinitialise();
		nbSegment = 0;
		T = 4;
	}
	
	public Solution lancer() {
		
		timeDeb = System.currentTimeMillis();
		Solution s = meilleurSolution.clone();
		
		arret = false;
		arretSegment = false;
		nbSegmentsArret = 0;
		nbTour = 0;
		
		while(!Test.timeout() && !arret) {
			
			Solution sPrime = s.clone();
			
			//lancement des heuristic const/dest selon les poids
			Couple c = lancerDestruction(sPrime);
			int numHeuristicDest = c.numHeuristic;
			sPrime = c.s;
			c = lancerConstruction(sPrime);
			int numHeuristicConst = c.numHeuristic;
			sPrime = c.s;
			
			//critere d'acceptation
			boolean accepte = accepte(sPrime, s);
			
			majDonne(sPrime, s, numHeuristicDest, numHeuristicConst, accepte);
			
			if(accepte) {
				s = sPrime.clone();
			}
			
			//critere d'arret
			if(!arretSegment){
				nbSegmentsArret = nbIteration;
				arretSegment = true;
			}
			constr.nb[numHeuristicConst] ++;
			destr.nb[numHeuristicDest] ++;
			
			nbIteration++;
			if(arretSegment && nbIteration == nbSegmentsArret) {
				nbTour ++;
				if(nbTour > tourMax) {
					arret = true;
				}
			}
			if(nbIteration == 100) {
				nbSegment ++;
				calculPoid();
				reinitialise();
			}
			InitialiserModel.mettreAJourSelonSolution(meilleurSolution);
		}
		InitialiserModel.mettreAJourSelonSolution(meilleurSolution);
		return meilleurSolution;
	}

	private boolean accepte(Solution sPrime, Solution s) {
		if(sPrime.costsol < meilleurSolution.costsol) {
			T = T*c;
			return true;
		}
		double valeurProba = 0;
		valeurProba = Math.exp(-(sPrime.costsol-s.costsol)/T);
		Random r = new Random();
		double y = r.nextDouble();
		if(y<valeurProba) {
			T = T*c;
			return true;
		}
		T = T*c;
		return false;
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
	
	private void majDonne(Solution sPrime, Solution s, int numHeuristicDest, int numHeuristicConst, boolean accepte) {
		boolean nouvelleSolution = false;
		nouvelleSolution = hashP.addP(sPrime.hashCode());
		
		if(nouvelleSolution) {
			arretSegment = false;
			nbTour = 0;
		}
		
		if(sPrime.costsol < meilleurSolution.costsol) {
			meilleurSolution = sPrime.clone();
			constr.score[numHeuristicConst] += sigma1;
			destr.score[numHeuristicDest] += sigma1;

		}else if(nouvelleSolution && accepte) {
			if(sPrime.costsol < s.costsol) {
				constr.score[numHeuristicConst] += sigma2;
				destr.score[numHeuristicDest] += sigma2;
			}else{
				constr.score[numHeuristicConst] += sigma3;
				destr.score[numHeuristicDest] += sigma3;
			}
			
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
