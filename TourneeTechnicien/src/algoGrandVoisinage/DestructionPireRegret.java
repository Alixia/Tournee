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

		while(nbDestruction >0) {
			ArrayList<Couple> solutions = triSolution(retour);
			Random r = new Random();
			double y = r.nextDouble();
			int position =0;
			if(probaRandom == 0) {
				position = 0;
			}else {
				position = (int)(Math.pow(y, probaRandom)*solutions.size());
			}
			retour = solutions.get(position).s;
			InitialiserModel.tacheAFaire = solutions.get(position).tacheAFaire;
			for (Tache tache : InitialiserModel.tacheFaite) {
				System.out.println(tache.toString());
			}
			System.out.println("************");
			System.out.println("************");
			System.out.println("************");
			InitialiserModel.tacheFaite = solutions.get(position).tacheFaite;
			for (Tache tache : InitialiserModel.tacheFaite) {
				System.out.println(tache.toString());
			}
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
			PriorityQueue<Integer> liste = new PriorityQueue<>();
			liste.add(i);
			System.out.println(s.toString());
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
