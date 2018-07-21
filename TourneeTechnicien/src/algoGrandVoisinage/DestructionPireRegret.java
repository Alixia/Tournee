package algoGrandVoisinage;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import Tests.TestInit;
import outilDeBase.InitialiserModel;
import outilDeBase.Solution;
import outilDeBase.Tache;
import outilGeneral.GestionTableau;

public class DestructionPireRegret{
	
	static int tailleProbleme;
	static double probaRandom;
	
	public static void intialiser(int n, double probaRandoms) {
		tailleProbleme = n;
		probaRandom = probaRandoms;
	}

	public static Solution detruit(Solution soluc) {
		
		Random rbis = new Random();
		int bornemin = Math.min((int)(0.1*tailleProbleme), 30);
		int bornemax = Math.min((int)(0.4*tailleProbleme), 60);
		int nbDestruction = rbis.nextInt(bornemax - bornemin)+bornemin;

		Vector <Tache> taf = GestionTableau.cloneTache(InitialiserModel.tacheAFaire);
		Vector <Tache> tf = GestionTableau.cloneTache(InitialiserModel.tacheFaite);
		
		Solution retour = soluc.clone();

		while(nbDestruction >0 && InitialiserModel.tacheFaite.size() >0 && !TestInit.timeout()) {
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
		
		if(TestInit.timeout()) {
			InitialiserModel.tacheFaite = taf;
			InitialiserModel.tacheFaite = tf;
			retour = soluc;
		}
		
		retour.Calcul_costsol();

		return retour;
	}
	
	
	private static ArrayList<Couple> triSolution(Solution soluc) {
		
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
	private static void add(ArrayList<Couple> liste, Couple c) {
		int i=0;
		while(i<liste.size() && liste.get(i).s.costsol > c.s.costsol){
			i++;
		}
		liste.add(i,c);
	}
	
	private static class Couple {
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
