package algoGrandVoisinage;

import java.util.Map;
import java.util.Vector;

import outilDeBase.Solution;
import outilDeBase.Tache;
import outilGeneral.HashagePerso;

public class GrandVoisinage {
	
	Solution meilleurSolution;
	int nbIteration;
	long timeDeb;
	float timeMax;
	HashagePerso hash;
	int r;
	Choix constr;
	Choix destr;
	
	// a regrouper les const/dest en une classe avec des attributs
	
	public GrandVoisinage(Solution s, float timeMax, int facteurDeRegression) {
		meilleurSolution = s;
		this.timeMax = timeMax;
		timeDeb = System.currentTimeMillis();
		hash = new HashagePerso();
		hash.add(s.hashCode());
		r = facteurDeRegression;
		constr = new Choix(2); 	// greedy/regret
		destr = new Choix(5); 	// random/shaw/regret1/regret2/regret3
		reinitialise();
	}
	
	public void reinitialise() {
		constr.reinitialise();
		destr.reinitialise();
		nbIteration = 0;
	}
	
	public Solution lancer() {
		
		Solution s = meilleurSolution.clone();
		
		while(!timeout()) {
			
				
			nbIteration++;
			if(nbIteration == 100) {
				calculPoid();
				reinitialise();
			}
		}
		
		return null;
	}

	private boolean timeout() {
		return (((float)(System.currentTimeMillis()-timeDeb)/1000f)<timeMax);
	}
	
	private void calculPoid() {
		constr.calculPoid();
		destr.calculPoid();
	}
	
	private class Choix{
		int[] score;
		int[] nb;
		double[] poid;
		double sommePoid;
		
		public Choix(int nbHeuristic) {
			score = new int[nbHeuristic];
			nb = new int[nbHeuristic];
			poid = new double[nbHeuristic];
			sommePoid = nbHeuristic;
		}
		
		public void reinitialise() {
			for(int i=0 ; i<score.length ; i++) {
				score[i] = 0;
				nb[i] = 0;
			}
		}
		
		public void calculPoid() {
			sommePoid = 0;
			for(int i=0 ; i<poid.length ; i++) {
				poid[i] = poid[i]*(1-r) + r*(score[i]/nb[i]);
				sommePoid += poid[i];
			}
		}
	}
	
}
