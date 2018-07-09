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
	
	public static void add1(Vector<Solution> vr, Vector<Solution> r){
		if(!r.isEmpty()){
			int i=0;
			while(i<vr.size() && vr.get(i).costsol < r.get(0).costsol && i<3){
				i++;
			}
			vr.add(i,r.get(0));
		}
	}
	
	public static void add2(Vector<Solution> vr, Vector<Solution> r){
		if(!r.isEmpty()){
			if(r.size()==1) {
				add1(vr, r);
			}else {
				int k = 0;
				if(r.get(1).costsol>r.get(0).costsol) {
					k = 0;
				}else {
					k = 1;
				}
				int i=0;
				while(i<vr.size() && vr.get(i).costsol < r.get(k).costsol && i<3){
					i++;
				}
				if(i<vr.size()) {
					vr.add(i,r.get(k));
					vr.add(i+1,r.get((k+1)%2));
				}else{
					vr.add(i,r.get(k));
					k = (k+1)%2;
					while(i<vr.size() && vr.get(i).costsol < r.get(k).costsol && i<3){
						i++;
					}
					vr.add(i,r.get(k));
				}
			}
		}
	}
	
	public static void add3(Vector<Solution> vr, Vector<Solution> r){
		if(!r.isEmpty()){
			if(r.size()==1) {
				add1(vr, r);
			}else{
				add2(vr, r);
				if(r.size()>=3) {
					int i=0;
					while(i<vr.size() && vr.get(i).costsol < r.get(2).costsol && i<3){
						i++;
					}
					vr.add(i,r.get(2));
				}
			}
		}
	}

}
