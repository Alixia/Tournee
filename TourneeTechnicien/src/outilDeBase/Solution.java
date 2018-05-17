package outilDeBase;

import java.util.Vector;

public class Solution {
	public Vector <Route> sol ;
	public double costsol;
	public int RngCostSol;
	public double costVoisin;		//inutile (prise en compte du cout des voisin ou non des cout des solutions)
	public int rngCosVoisin;		//inutile
	public double BiasedFitness;	//inutile (prise en compte de BF)
	public Vector <Integer> tacheFaite ;
	public int code_sol;
	
public Solution (){
	sol = new Vector<Route>();
	tacheFaite = new Vector<Integer>();

	}
public Solution Clone(){
	Solution s = new Solution();
	Vector <Route> vec = new Vector<Route>();
	Vector <Integer> t= new Vector<Integer>();
	for (int i=0;i<sol.size();i++){
		Route r= sol.get(i).clone();
		vec.add(r);
		
	}
	s.sol=vec;
	for (int i=0;i<tacheFaite.size();i++){
		t.add(tacheFaite.get(i));
		
	}
	s.tacheFaite=t;
	s.costsol= new Float (costsol);
	s.RngCostSol=RngCostSol;
	s.costVoisin= new Double(costVoisin);
	s.rngCosVoisin=rngCosVoisin;
	s.BiasedFitness= new Double(BiasedFitness);
	return s;
	
}
public void ToString(){
	int i=0;
	System.out.println(" Le coutSol: "+costsol);
	//System.out.println(" Le coutSol: "+costsol+" RangSol: "+RngCostSol+"");

	//System.out.println(" Le coutVoi: "+costVoisin+" RangVois: "+rngCosVoisin+"");
	//System.out.println("** BF: ** "+BiasedFitness);

	while (i<sol.size()){
		sol.get(i).tostring();
		i++;
	}
	
}

public void Calcul_costsol(){
	double c =0;
	for (int i=0; i<sol.size();i++){
		c=c+sol.get(i).cost;
	}
	costsol = c;
}

}
