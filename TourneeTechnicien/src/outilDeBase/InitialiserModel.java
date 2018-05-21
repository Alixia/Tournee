package outilDeBase;

import java.util.Vector;

public class InitialiserModel {

	public static Vector <Tache> tache = new Vector<Tache>();
	public static Vector <Technicien> tech = new Vector<Technicien>();
	
	public static void initialiser(){
		Intialisertache();
		InitialiserTechnician();
	}
	
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
			
			Technicien te= new Technicien (k, inv, ReadData.tache+k, ReadData.dep[k]);
			tech.add(te);
		}
	}
	
	public static void add(Vector<Tache> vt, Tache t){
		int i=0;
		while(i<vt.size() && vt.get(i).prio > t.prio){
			i++;
		}
		vt.add(i,t);
	}

	public static void afficher(){
		System.out.println("******************");
		for (Tache tache2 : tache) {
			System.out.println(tache2.toString());
		}
		System.out.println("---------");
		for (Technicien tech2 : tech) {
			System.out.println(tech2.toString());
		}
		System.out.println("*****************");
	}
	
}
