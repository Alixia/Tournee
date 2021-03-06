package outilGeneral;

import java.util.Vector;

import outilDeBase.Activite;
import outilDeBase.Route;
import outilDeBase.Tache;

public class GestionTableau {
	
	public static double[][] clone(double[][] obj){
		double[][] copie = new double[obj.length][obj[0].length];
		for (int i = 0; i < obj.length; i++){
			for (int j = 0 ; j<obj[0].length ; j++) {
				copie[i][j] = obj[i][j];
			}
		}
		return copie;
	}
	
	public static Vector<Activite> clone(Vector<Activite> liste){
		Vector <Activite> retour = new Vector<>();
		for(int i=0; i<liste.size(); i++){
			retour.add(liste.get(i).clone());
		}
		return retour;
	}
	
	public static Vector<int[]> cloneVectTab(Vector<int[]> liste){
		Vector <int[]> retour = new Vector<>();
		for(int i=0; i<liste.size(); i++){
			retour.add(liste.get(i).clone());
		}
		return retour;
	}
	
	public static Vector <Route> cloneRoutes(Vector <Route> liste){
		Vector <Route> retour = new Vector<>();
		for(int i=0; i<liste.size(); i++){
			retour.add(liste.get(i).clone());
		}
		return retour;
	}
	
	public static Vector <Integer> cloneIntegers(Vector <Integer> liste){
		Vector <Integer> retour = new Vector<>();
		for(int i=0; i<liste.size(); i++){
			retour.add(liste.get(i));
		}
		return retour;
	}
	
	public static Vector <Tache> cloneTache(Vector <Tache> liste){
		Vector <Tache> retour = new Vector<>();
		for(int i=0; i<liste.size(); i++){
			retour.add(liste.get(i).clone());
		}
		return retour;
	}
	
	public static Vector<Tache> removeNom(Vector <Tache> liste, int nom){
		int i=0;
		while(i < liste.size() && nom != liste.get(i).nom) {
			i++;
		}
		if(i < liste.size()) {
			liste.remove(i);
		}
		return liste;
		
	}

	public static int[] cloneTableau(int[] piece) {
		int[] copie = new int[piece.length];
		for (int i = 0; i < piece.length; i++){
			copie[i] = piece[i];
		}
		return copie;
	}

}
