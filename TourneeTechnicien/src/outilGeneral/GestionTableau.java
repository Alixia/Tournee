package outilGeneral;

import java.util.Vector;

import outilDeBase.Activite;

public class GestionTableau {
	
	public static double[][] clone(double[][] obj){
		double[][] copie = new double[obj.length][obj[0].length];
		for (int i = 0; i < obj.length; i++){
			copie[i] = obj[i].clone();
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

}
