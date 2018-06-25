package algoGrandVoisinage;

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
			add(taches, solutions.get(position));
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
		int i = 0;
		int nbTache = 0;
		int nbTech = 0;
		Vector<Integer> liste = new Vector<>();
		
		while(nbTech < retour.sol.size()) {
			while(nbTache < retour.sol.get(nbTech).lesActivite.size()) {
				Tache t = retour.sol.get(nbTech).lesActivite.get(nbTache).task; 
				if(!t.isPause()) {
					if(appartient(taches, t.nom)) {
						liste.add(i);
					}
					i++;
				}
				nbTache++;
			}
			nbTache = 0;
			nbTech ++;
		}
		return liste;
	}

	private void add(Vector<Tache> taches, Tache tache) {
		int i=0;
		while(i<taches.size() && taches.get(i).nom < tache.nom){
			i++;
		}
		taches.add(i,tache);
	}
	
	public boolean appartient(Vector<Tache> taches, int nom){

	  boolean trouve = false; 
	  int id = 0;
	  int ifin = taches.size() ;
	  int im;
	  
	  /* boucle de recherche */
	  while(!trouve && ((ifin - id) > 1)){
	    im = (id + ifin)/2;
	    trouve = (taches.get(im).nom == nom);  
	    if(taches.get(im).nom > nom) ifin = im;
	    else id = im;
	  }
	  
	  if(taches.get(id).nom == nom) return(true);
	  else return(false);
	  
	}

	
}
