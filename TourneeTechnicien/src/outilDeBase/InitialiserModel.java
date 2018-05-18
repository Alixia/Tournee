package outilDeBase;

import java.util.Vector;

public class InitialiserModel {

	public static Vector <Tache> tache = new Vector<Tache>();
	public static Vector <Technicien> tech = new Vector<Technicien>();
	
	public InitialiserModel (){
		initialiser();
	}
	
	public static void initialiser(){
		Intialisertache();
		InitialiserTechnician();
	}
	
	//public Solution UneSolution (){
	//
	//	
	//	Vector <Integer> technicienCreer = new Vector<Integer>();
	//	Vector <Integer> TacheFaiteCreer = new Vector<Integer>();
	//	
	//		technicienCreer.clear();
	//		TacheFaiteCreer.clear();
	//		Intialisertache ();		
	//		InitialiserTechnician();
	//		Solution sol =CreerSolVide();
	//		while (!tache.isEmpty()){
	//		//for(int t=0; t<tache.size();t++){
	//			int t=(int)Math.random()*tache.size();
	//			Tache ta= tache.remove(t);
	//
	//			Vector<Route> FeasRoute= new Vector<Route>();
	//			for (int k=0; k<sol.sol.size();k++){
	//				if(ReadData.competence[sol.sol.get(k).tech.nom][ta.nom]==1){
	//					Route r= sol.sol.get(k);
	//				for (int pos=1;pos<r.lesActivite.size()-1;pos++){
	//					FeasRoute.addAll( SolutionInitiale.GetPossibleInsertion (ta, r));
	//				}
	//			}
	//		}
	//			FeasRoute= SolutionInitiale.TriRoute(FeasRoute);
	//			boolean trouv=false;
	//			int z=0;
	//			while(z<sol.sol.size() && !trouv && !FeasRoute.isEmpty()){
	//				if (sol.sol.get(z).tech.nom==FeasRoute.get(0).tech.nom){
	//					trouv=true;
	//					sol.sol.remove(z);
	//					sol.sol.add(FeasRoute.get(0));
	//				}
	//				z++;
	//			}
	//		}
	//		for (int s=0; s<sol.sol.size();s++){
	//			for (int i=0; i<sol.sol.get(s).lesActivite.size();i++){
	//				if (sol.sol.get(s).lesActivite.get(i).task.nom>=0){
	//					
	//			sol.tacheFaite.add(sol.sol.get(s).lesActivite.get(i).task.nom);
	//				}
	//			}
	//		}
	//		return sol;
	//	
	//}
	public static void Intialisertache (){
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
			//par ordre de priorite
			add(tache, (new Tache (i,tw, p, ReadData.pieceTab[i],ReadData.alpha[i],ReadData.sigma[i])));
		}
		
	}
	public static void InitialiserTechnician(){
		// On remplie tableau des techniciens 
		for (int k=0;k<ReadData.tech; k++){
			int [] inv = new int [ReadData.nbrPiece];
			for (int z=0; z<ReadData.nbrPiece;z++){
				inv[z]=ReadData.piecet[z][k];			
			}
		
			//remplir un vecteur avec tte les tache que le technicien peut faire
			Vector <Tache> tachetech = new Vector<Tache>();
			for (int i=0; i<tache.size();i++){
				if (ReadData.competence[k][tache.get(i).nom]==1){
					add(tachetech, tache.get(i));
				}
			}
			
			Technicien te= new Technicien (k, inv, ReadData.tache+k, ReadData.dep[k], tachetech);
			tech.add(te);
			
			
		}
	}
	
	public static void add(Vector<Tache> vt, Tache t){
		int i=0;
		while(i<vt.size() || vt.get(i).prio > t.prio){
			i++;
		}
		vt.add(t);
	}

}
