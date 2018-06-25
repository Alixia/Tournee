package algoGrandVoisinage;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;

import outilDeBase.InitialiserModel;
import outilDeBase.Solution;
import outilDeBase.SolutionGreedy;
import outilDeBase.Tache;
import outilGeneral.GestionTableau;

public class DestructionPireRegret implements AlgoDestruction{
	
	int nbDestruction;
	int probaRandom;
	
	public DestructionPireRegret(int nbDestruction, int probaRandom) {
		this.nbDestruction = nbDestruction;
		this.probaRandom = probaRandom;
	}

	public Solution detruit(Solution soluc) {
		
		Solution retour = soluc.clone();

		while(nbDestruction >0 && InitialiserModel.tacheFaite.size() >0) {
			ArrayList<Couple> solutions = triSolution(retour);
			Random r = new Random();
			double y = r.nextDouble();
			int position =0;
			position = (int)(Math.pow(y, probaRandom)*(solutions.size()-1));
			retour = solutions.get(position).s;
			InitialiserModel.tacheAFaire = solutions.get(position).tacheAFaire;
			InitialiserModel.tacheFaite = solutions.get(position).tacheFaite;
			nbDestruction --;
		}

		return retour;
	}
	
	
	private ArrayList<Couple> triSolution(Solution soluc) {
		
		Vector <Tache> tacheAFaire = GestionTableau.cloneTache(InitialiserModel.tacheAFaire);
		Vector <Tache> tacheFaite = GestionTableau.cloneTache(InitialiserModel.tacheFaite);
		
		ArrayList<Couple> retour = new ArrayList<>();
		
		for(int i = 0 ; i < tacheFaite.size() ; i++) {
			Solution s = soluc.clone();
			InitialiserModel.tacheAFaire = GestionTableau.cloneTache(tacheAFaire);
			InitialiserModel.tacheFaite = GestionTableau.cloneTache(tacheFaite);
			Vector<Integer> liste = new Vector<>();
			liste.add(i);
			Solution ret = DestructionRandom.suppressionTache(s, liste, tacheFaite.size());
			ret.Calcul_costsol();
			Couple c = new Couple(ret, GestionTableau.cloneTache(InitialiserModel.tacheAFaire), GestionTableau.cloneTache(InitialiserModel.tacheFaite));
			add(retour, c);
		}
		
		return retour;
	}
	
	//ajoute les couples selon les couts (du plus benefique au moins)
	private void add(ArrayList<Couple> liste, Couple c) {
		int i=0;
		while(i<liste.size() && liste.get(i).s.costsol < c.s.costsol){
			i++;
		}
		liste.add(i,c);
	}
	
	private class Couple {
		Solution s;
		Vector <Tache> tacheAFaire;
		Vector <Tache> tacheFaite;
		
		public Couple(Solution s, Vector <Tache> tacheAFaire, Vector <Tache> tacheFaite) {
			this.s = s;
			this.tacheAFaire = tacheAFaire;
			this.tacheFaite = tacheFaite;
		}
	}

}
