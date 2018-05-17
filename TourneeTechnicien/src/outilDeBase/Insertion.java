package outilDeBase;

import java.util.Vector;

public class Insertion {
int nbrSol;
Vector<Solution> TTeLesSolutions = new Vector<Solution>()	;
Vector <Tache> tache = new Vector<Tache>();
Vector <Technicien> tech = new Vector<Technicien>();
public Insertion (int n){
	nbrSol=n;
}
public Vector<Solution> LancerInsertion (){
		
		while (TTeLesSolutions.size()<nbrSol){
			Solution s =UneSolution();
			TTeLesSolutions.add(s);
			
			tache.clear();
			tech.clear();
		}

		SolutionInitiale.CalculerLesCouts(TTeLesSolutions);
	
		/*for (int i=0;i<TTeLesSolutions.size();i++){
			System.out.println("");
			System.out.print("***********Solution "+i+" ***********");
			System.out.println("");
			TTeLesSolutions.get(i).ToString();
		}*/
		return TTeLesSolutions;
	}
public Solution UneSolution (){

	
	Vector <Integer> technicienCreer = new Vector<Integer>();
	Vector <Integer> TacheFaiteCreer = new Vector<Integer>();
	
		technicienCreer.clear();
		TacheFaiteCreer.clear();
		Intialisertache ();		
		InitialiserTechnician();
		Solution sol =CreerSolVide();
		while (!tache.isEmpty()){
		//for(int t=0; t<tache.size();t++){
			int t=(int)Math.random()*tache.size();
			Tache ta= tache.remove(t);

			Vector<Route> FeasRoute= new Vector<Route>();
			for (int k=0; k<sol.sol.size();k++){
				if(ReadData.competence[sol.sol.get(k).tech.nom][ta.nom]==1){
					Route r= sol.sol.get(k);
				for (int pos=1;pos<r.lesActivite.size()-1;pos++){
					FeasRoute.addAll( SolutionInitiale.GetPossibleInsertion (ta, r));
				}
			}
		}
			FeasRoute=SolutionInitiale.Triroute(FeasRoute);
			boolean trouv=false;
			int z=0;
			while(z<sol.sol.size() && !trouv && !FeasRoute.isEmpty()){
				if (sol.sol.get(z).tech.nom==FeasRoute.get(0).tech.nom){
					trouv=true;
					sol.sol.remove(z);
					sol.sol.add(FeasRoute.get(0));
				}
				z++;
			}
		}
		for (int s=0; s<sol.sol.size();s++){
			for (int i=0; i<sol.sol.get(s).lesActivite.size();i++){
				if (sol.sol.get(s).lesActivite.get(i).task.nom>=0){
					
			sol.tacheFaite.add(sol.sol.get(s).lesActivite.get(i).task.nom);
				}
			}
		}
		return sol;
	
}
public Solution CreerSolVide(){
	Solution sol = new Solution();
	for(int t=0; t<tech.size();t++){
		Route r= new Route(tech.get(t));
		sol.sol.add(r);
	}
	sol.costsol=0;
	return sol;
}
public void  Intialisertache (){
	// On remplie tableau des taches
	Vector<Tache> vecT= new Vector<Tache>();
	for(int i=0; i<ReadData.tache;i++){
		double [][] tw = new double [ReadData.nbrTw][2];
		int [] p = new int [ReadData.nbrPiece];				
		for (int tt=0; tt<ReadData.nbrTw;tt++){
			tw[tt][0]=ReadData.fenetre[i*ReadData.nbrTw+tt][0];
			tw[tt][1]=ReadData.fenetre[i*ReadData.nbrTw+tt][1];
		}				
		for (int z=0; z<ReadData.nbrPiece;z++){
			p[z]=ReadData.piece[z][i];
		}
		vecT.add(new Tache (i,tw, p, ReadData.pieceTab[i],ReadData.alpha[i],ReadData.sigma[i]));
	}
	 while(!vecT.isEmpty()){
		 int i=0;
		 int index=0;
		 int max=vecT.get(0).prio;
		 while(i<vecT.size()){
			 if (vecT.get(i).prio>max){
				 max= vecT.get(i).prio;
				 index=i;
				 
			 }
			 i++;
		 }
		 tache.add(vecT.remove(index));
	}
	
}
public void InitialiserTechnician(){
	// On remplie tableau des techniciens 
			for (int k=0;k<ReadData.tech; k++){
				int [] inv = new int [ReadData.nbrPiece];
				for (int z=0; z<ReadData.nbrPiece;z++){
					inv[z]=ReadData.piecet[z][k];			
				}
			
				//remplir un vecteur avec tte les tache que le technicien peut faire
				Vector <Tache> tachetech = new Vector<Tache>();
				for (int i=0; i<tache.size();i++){
					if (ReadData.competence[k][tache.get(i).nom]==1)
						tachetech.add(tache.get(i));
					
				}
			
				tachetech =SolutionInitiale.TritacheGain(tachetech);
				
				
				Technicien te= new Technicien (k, inv, ReadData.tache+k, ReadData.dep[k]);
				te.tachetech= tachetech;
				tech.add(te);
				
				
			}
}
}
