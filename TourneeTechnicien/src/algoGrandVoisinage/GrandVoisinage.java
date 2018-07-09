package algoGrandVoisinage;

import java.util.Vector;

import outilDeBase.Solution;
import outilDeBase.Tache;

public class GrandVoisinage {
	
	Solution meilleurSolution;
	int scoreDestPire;
	int scoreDestRandom;
	int scoreDestShaw;
	int scoreConstGreedy;
	int scoreConstRegret1;
	int scoreConstRegret2;
	int scoreConstRegret3;
	int nbIteration;
	long timeDeb;
	float timeMax;
	
	public void reinitialise() {
		scoreDestPire = 0;
		scoreDestRandom = 0;
		scoreDestShaw = 0;
		scoreConstGreedy = 0;
		scoreConstRegret1 = 0;
		scoreConstRegret2 = 0;
		scoreConstRegret3 = 0;
		nbIteration = 0;
	}
	
	public Solution lancer(float timeMax, Solution s) {
		meilleurSolution = s;
		this.timeMax = timeMax;
		timeDeb = System.currentTimeMillis();
		reinitialise();
		
		while(!timeout()) {
			if(nbIteration == 100) {
				reinitialise();
			}
				
			nbIteration++;
		}
		
		return null;
	}

	private boolean timeout() {
		return (((float)(System.currentTimeMillis()-timeDeb)/1000f)<timeMax);
	}
	
}
