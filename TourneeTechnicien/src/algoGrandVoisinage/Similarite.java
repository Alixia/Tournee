package algoGrandVoisinage;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import outilDeBase.InitialiserModel;
import outilDeBase.ReadData;
import outilDeBase.Tache;

public class Similarite {
	
	public static double phi;
	public static double xi;
	public static double psy;
	public static double omega;
	
	public static void intialiser(double phi, double xi, double psy, double omega) {
		Similarite.phi = phi;
		Similarite.xi = xi;
		Similarite.psy = psy;
		Similarite.omega = omega;
	}
	
	public static double relation(Tache i, Tache j, double tempsi, double tempsj) {
		double retour = 0;
		retour += phi*ReadData.distance[i.nom][j.nom];
		retour += xi*(Math.abs(tempsi-tempsj));
		retour += psy*(Math.abs(i.TpsRepa-j.TpsRepa));
		Vector<Integer> ki = InitialiserModel.K.get(i.nom);
		Vector<Integer> kj = InitialiserModel.K.get(j.nom);
		retour += omega*(1-(cardinalIntersection(ki, kj)/(Math.min(ki.size(), kj.size()))));
		return retour;
	}
	
	private static int cardinalIntersection(Vector<Integer> ki, Vector<Integer> kj) {
		Set<Integer> si = new HashSet<>(ki.size());
		Set<Integer> sj = new HashSet<>(kj.size());
		for (int i : ki) {
			si.add(i);
		}
		for (int j : kj) {
			sj.add(j);
		}
		si.retainAll(sj);
		int[] intersection = new int[sj.size()];
		int i=0;
		for (Integer integer : si) {
			intersection[i] = integer;
			i++;
		}
		return intersection.length;
	}

}
