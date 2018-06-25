package algoGrandVoisinage;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import outilDeBase.InitialiserModel;
import outilDeBase.Solution;
import outilDeBase.Tache;

public class DestructionShaw implements AlgoDestruction{

	int nbDestruction;
	int probaRandom;
	
	public DestructionShaw(int nbDestruction, int probaRandom) {
		this.nbDestruction = nbDestruction;
		this.probaRandom = probaRandom;
	}
	
	public Solution detruit(Solution soluc) {
		
		Random r = new Random();
		int i = r.nextInt(InitialiserModel.tacheFaite.size());
		Tache tache = InitialiserModel.tacheFaite.get(i).clone();
		Vector<Tache> taches = new Vector<>();
		taches.add(tache);
		
		Solution retour = soluc.clone();

		while(taches.size() < nbDestruction && InitialiserModel.tacheFaite.size() > 0) {
			i = r.nextInt(taches.size());
			tache = taches.get(i);
			Vector<Tache> solutions = triRequete(taches, tache);
			double y = r.nextDouble();
			int position = (int)(Math.pow(y, probaRandom)*(solutions.size()-1));
			taches.add(solutions.get(position));
			nbDestruction --;
		}
	
		Vector<Integer> tachesSelonIndice = tacheSelonPosition(retour, taches);
		retour = DestructionRandom.suppressionTache(retour, tachesSelonIndice, InitialiserModel.tacheFaite.size());
		
		return retour;
	}

	private Vector<Tache> triRequete(Vector<Tache> taches, Tache tache) {
		// TODO Auto-generated method stub
		return null;
	}

	private Vector<Integer> tacheSelonPosition(Solution retour, Vector<Tache> taches) {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
