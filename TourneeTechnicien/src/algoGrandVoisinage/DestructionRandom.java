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
	static int nbTache;
	static int nbTech;
	static int nbTacheAvecSuppression;
	static boolean depot;
	
	public DestructionRandom(int nb) {
		this.nbDestruction = nb;
	}

	public Solution detruit(Solution donnee) {
		
		Solution s = donnee.clone();
		
		if(nbDestruction>=InitialiserModel.tacheFaite.size()) {
			return new Solution();
		}
		//generation de nbDestruction taches differentes
		int[] ints = new Random().ints(0, InitialiserModel.tacheFaite.size()).distinct().limit(nbDestruction).toArray();
		PriorityQueue<Integer> randomList = new PriorityQueue<>();
		for (int i : ints) {
			randomList.add(i);
		}
		
		s = suppressionTache(s, randomList, InitialiserModel.tacheFaite.size());
		return s;
	}
	
	static Solution suppressionTache(Solution s, PriorityQueue<Integer> randomList, int fin) {
		//suppression des taches
		int i = 0;
		nbTache = 0;
		nbTacheAvecSuppression = 0;
		nbTech = 0;
		depot = false;
		Solution solution = new Solution();
		boolean enAttente = true;
		boolean finNb  = false;
		while (!randomList.isEmpty() && !InitialiserModel.tacheFaite.isEmpty() && nbTech < ReadData.tech) {
			int k = randomList.poll();
			while((i!=k || enAttente) && !finNb){
				//parcours des pauses
				parcoursPause(s);
				if(!enAttente) {
					//construction de la solution
					Tache tache = s.sol.get(nbTech).lesActivite.get(nbTache).task;
					boolean ok = construire(solution, nbTech, nbTacheAvecSuppression, tache);
					if(!ok) {
						nbTacheAvecSuppression--;
						InitialiserModel.add(InitialiserModel.tacheAFaire, tache);
						GestionTableau.removeNom(InitialiserModel.tacheFaite, tache.nom);
						if(!randomList.isEmpty()) {
							k = randomList.poll();
						}else {
							finNb = true;
						}
					}
					if(solution.sol.get(nbTech).passageDepot>0 && !depot) {
						nbTacheAvecSuppression++;
						depot = true;
					}
					nbTache++;
					nbTacheAvecSuppression++;
					if(nbTache >= s.sol.get(nbTech).lesActivite.size()-1) {
						nbTache = 0;
						nbTacheAvecSuppression = 0;
						nbTech ++;
						depot = false;
					}
					i++;
				}
				enAttente = false;
			}
			if(!finNb) {
				parcoursPause(s);
				Tache tmp = s.sol.get(nbTech).lesActivite.get(nbTache).task;
				InitialiserModel.add(InitialiserModel.tacheAFaire, tmp);
				GestionTableau.removeNom(InitialiserModel.tacheFaite, tmp.nom);
				nbTache++;
				if(nbTache >= s.sol.get(nbTech).lesActivite.size()-1) {
					nbTache = 0;
					nbTacheAvecSuppression = 0;
					nbTech ++;
					depot = false;
				}
				i++;
			}
			parcoursPause(s);
		}
		while(i<fin && nbTech < s.sol.size() && nbTache < s.sol.get(nbTech).lesActivite.size()) {
			parcoursPause(s);
			System.out.println(nbTech + "   " + nbTache + "   " + i + "   " + fin);
			Tache tache = s.sol.get(nbTech).lesActivite.get(nbTache).task;
			boolean ok = construire(solution, nbTech, nbTacheAvecSuppression, tache);
			if(!ok) {
				nbTacheAvecSuppression--;
				InitialiserModel.add(InitialiserModel.tacheAFaire, tache);
				GestionTableau.removeNom(InitialiserModel.tacheFaite, tache.nom);
			}
			if(solution.sol.get(nbTech).passageDepot>0 && !depot) {
				nbTacheAvecSuppression++;
				depot = true;
			}
			nbTache++;
			nbTacheAvecSuppression++;
			if(nbTache >= s.sol.get(nbTech).lesActivite.size()-1) {
				nbTache = 0;
				nbTech ++;
				nbTacheAvecSuppression=0;
				depot = false;
			}
			i++;
		}
		
		return solution;
	}
	
	
	
	private static boolean construire(Solution solution, int nbTech, int nbTache, Tache tache) {
		Route r = solution.sol.get(nbTech);
		Vector<Route> NewR = SolutionGreedy.evaluerInsertion (r, tache, nbTache-1);
		if(NewR.isEmpty()) {
			return false;
		}
		double min = NewR.get(0).cost;
		int index = 0;
		int j=1;
		while( j<NewR.size()){
			if (NewR.get(j).cost<min){
				min = NewR.get(j).cost;
				index=j;
			}
			j++;
		}
		Route insertion = NewR.get(index);
		solution.sol.remove(nbTech);
		solution.sol.add(nbTech,insertion);

		return true;
	}
	
	private static void parcoursPause(Solution s) {
		while(nbTech < ReadData.tech && nbTache < s.sol.get(nbTech).lesActivite.size()-1 && s.sol.get(nbTech).lesActivite.get(nbTache).isPause()) {
			if(!(s.sol.get(nbTech).lesActivite.get(nbTache).task.nom==-1000)) {
				nbTache++;
				nbTacheAvecSuppression++;
				if(nbTache >= s.sol.get(nbTech).lesActivite.size()-1) {
					nbTache = 0;
					nbTacheAvecSuppression = 0;
					nbTech ++;
					depot = false;
				}
			}else{
				nbTache++;
			}
		}
	}

}
