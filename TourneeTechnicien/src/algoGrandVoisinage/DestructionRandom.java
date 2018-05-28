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
		Random r = new Random();
		PriorityQueue<Integer> randomList = new PriorityQueue<>();
		for(int i=0 ; i<nbDestruction ; i++){
			randomList.add(r.nextInt(ReadData.tache));
		}
		
		int i = 0;
		while (!randomList.isEmpty()) {
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
