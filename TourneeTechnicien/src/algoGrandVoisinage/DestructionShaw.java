package algoGrandVoisinage;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import outilDeBase.Activite;
import outilDeBase.InitialiserModel;
import outilDeBase.Solution;
import outilDeBase.Tache;

public class DestructionShaw{

	static int nbDestruction;
	static int probaRandom;
	
	public static void intialiser(int nbDestructions, int probaRandoms) {
		nbDestruction = nbDestructions;
		probaRandom = probaRandoms;
	}
	
	public static Solution detruit(Solution soluc) {
		Vector<Activite> tachesSolution = new Vector<>();
		int nbTache = 0;
		int nbTech = 0;		
		while(nbTech < soluc.sol.size()) {
			while(nbTache < soluc.sol.get(nbTech).lesActivite.size()) {
				Activite t = soluc.sol.get(nbTech).lesActivite.get(nbTache).clone(); 
				if(!t.task.isPause()) {
					add(tachesSolution, t);
				}
				nbTache++;
			}
			nbTache = 0;
			nbTech ++;
		}
		
		Vector<Activite> taches = new Vector<>();
		Random r = new Random();
		int i = r.nextInt(tachesSolution.size());
		Activite tache = tachesSolution.get(i).clone();
		taches.add(tache);
		remove(tachesSolution, tache);
		
		Solution retour = soluc.clone();

		while(taches.size() < nbDestruction && InitialiserModel.tacheFaite.size() > 0) {
			i = r.nextInt(taches.size());
			tache = taches.get(i);
			Vector<Activite> solutions = triRequete(tachesSolution, tache, retour);
			double y = r.nextDouble();
			int position = (int)(Math.pow(y, probaRandom)*(solutions.size())-1);
			add(taches, solutions.get(position));
			remove(tachesSolution, solutions.get(position));
		}
	
		System.out.println("tache size : " + taches.size() + " nb destruction : " + nbDestruction + " tachefaitesize : " + InitialiserModel.tacheFaite.size() + "\n " + taches.toString()+ "\n " + tachesSolution.toString());
		Vector<Integer> tachesSelonIndice = tacheSelonPosition(retour, taches);
		retour = DestructionRandom.suppressionTache(retour, tachesSelonIndice, InitialiserModel.tacheFaite.size());
		
		return retour;
	}

	private static Vector<Activite> triRequete(Vector<Activite> tachesSolution, Activite tache, Solution soluc) {
		Vector<Activite> liste = new Vector<>();
		for (Activite activiteSol : tachesSolution) {
			addSelonSim(liste, activiteSol, tache);
		}
		
		return liste;
	}

	private static void addSelonSim(Vector<Activite> liste, Activite t, Activite tache) {
		double sim = Similarite.relation(t.task, tache.task, t.TpsDebServ, tache.TpsDebServ);
		int i=0;
		while(i<liste.size() && Similarite.relation(tache.task, liste.get(i).task, tache.TpsDebServ, liste.get(i).TpsDebServ)>sim){
			i++;
		}
		liste.add(i,t);
	}

	private static Vector<Integer> tacheSelonPosition(Solution soluc, Vector<Activite> taches) {
		int i = 0;
		int nbTache = 0;
		int nbTech = 0;
		Vector<Integer> liste = new Vector<>();
		
		while(nbTech < soluc.sol.size()) {
			while(nbTache < soluc.sol.get(nbTech).lesActivite.size()) {
				Tache t = soluc.sol.get(nbTech).lesActivite.get(nbTache).task; 
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

	private static void add(Vector<Activite> taches, Activite tache) {
		int i=0;
		while(i<taches.size() && taches.get(i).task.nom < tache.task.nom){
			i++;
		}
		taches.add(i,tache);
	}
	
	private static void remove(Vector<Activite> taches, Activite tache) {
		 boolean trouve = false; 
		  int id = 0;
		  int ifin = taches.size() ;
		  int im;
		  
		  /* boucle de recherche */
		  while(!trouve && ((ifin - id) > 1)){
		    im = (id + ifin)/2;
		    trouve = (taches.get(im).task.nom == tache.task.nom);  
		    if(taches.get(im).task.nom > tache.task.nom) ifin = im;
		    else id = im;
		  }
		  
		  if(taches.get(id).task.nom == tache.task.nom) {
			  taches.remove(id);
		  }
	}
	
	public static boolean appartient(Vector<Activite> taches, int nom){

	  boolean trouve = false; 
	  int id = 0;
	  int ifin = taches.size() ;
	  int im;
	  
	  /* boucle de recherche */
	  while(!trouve && ((ifin - id) > 1)){
	    im = (id + ifin)/2;
	    trouve = (taches.get(im).task.nom == nom);  
	    if(taches.get(im).task.nom > nom) ifin = im;
	    else id = im;
	  }
	  
	  if(taches.get(id).task.nom == nom) return(true);
	  else return(false);
	  
	}

	
}
