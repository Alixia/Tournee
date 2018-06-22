package outilDeBase;

import java.util.Vector;

import outilGeneral.GestionTableau;

public class Solution {
	public Vector <Route> sol ;
	public double costsol;
	public Vector <Integer> tacheFaite ;
	public int code_sol;
	
	
	public Solution (){
		sol = new Vector<Route>();
		tacheFaite = new Vector<Integer>();
		for(int t=0; t<ReadData.tech;t++){
			Route r= new Route(InitialiserModel.tech.get(t));
			sol.add(r);
		}
		costsol=0;
	}
	
	public Solution(Vector<Route> sol, double costsol, Vector<Integer> tacheFaite, int code_sol) {
		this.sol = sol;
		this.costsol = costsol;
		this.tacheFaite = tacheFaite;
		this.code_sol = code_sol;
	}



	public Solution clone(){
		Vector <Route> vec = GestionTableau.cloneRoutes(sol);
		Vector <Integer> t= GestionTableau.cloneIntegers(tacheFaite);
		Solution s = new Solution(vec, costsol, t, code_sol);
		return s;
		
	}
	
	public String toString(){
		int i=0;
		String retour = " Le coutSol: "+costsol ;
	
		while (i<sol.size()){
			retour += sol.get(i).toString();
			i++;
		}
		return retour;
	}
	
	public void Calcul_costsol(){
		double c =0;
		for (int i=0; i<sol.size();i++){
			c=c+sol.get(i).cost;
		}
		costsol = c;
	}

}
