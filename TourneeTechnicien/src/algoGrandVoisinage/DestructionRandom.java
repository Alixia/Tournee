package algoGrandVoisinage;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;

import outilDeBase.Activite;
import outilDeBase.InitialiserModel;
import outilDeBase.ReadData;
import outilDeBase.Route;
import outilDeBase.Solution;
import outilDeBase.SolutionGreedy;
import outilDeBase.Tache;
import outilGeneral.GestionTableau;

public class DestructionRandom implements AlgoDestruction{
	
	int nbDestruction;
	
	public DestructionRandom(int nb) {
		this.nbDestruction = nb;
	}

	public Solution detruit(Solution s) {
		
		//generation de nbDestruction taches differentes
		int[] ints = new Random().ints(0, InitialiserModel.tacheFaite.size()).distinct().limit(nbDestruction).toArray();
		PriorityQueue<Integer> randomList = new PriorityQueue<>();
		for (int i : ints) {
			randomList.add(i);
		}	
		
		//suppression des taches
		int i = 0;
		while (!randomList.isEmpty() && !InitialiserModel.tacheFaite.isEmpty()) {
			int j = randomList.poll();
			while(i!=j){
				i++;
			}
			Tache tmp = InitialiserModel.tacheFaite.get(i%InitialiserModel.tacheFaite.size());
			InitialiserModel.add(InitialiserModel.tacheAFaire, tmp);
			InitialiserModel.tacheFaite.remove(i%InitialiserModel.tacheFaite.size());
		}
		
		Solution soluc = SolutionGreedy.solutionTache(InitialiserModel.tacheFaite);
		return soluc;
		
	}

}
