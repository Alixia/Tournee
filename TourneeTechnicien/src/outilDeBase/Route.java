package outilDeBase;

import java.util.Vector;

import outilGeneral.GestionTableau;

public class Route {
	
	public Technicien tech ;
	public Vector <Activite> lesActivite = new Vector<>();
	public Vector <int []> piece = new Vector <int []>();
	public Double cost;
	public int gain;
	public int passageDepot;
	public int code_sol;		//peut etre inutile
	
	
	
	public Route(Technicien tech, Vector<Activite> lesActivite, Vector<int[]> piece, Double cost, int gain,
			int passageDepot, int code_sol) {
		this.tech = tech;
		this.lesActivite = lesActivite;
		this.piece = piece;
		this.cost = cost;
		this.gain = gain;
		this.passageDepot = passageDepot;
		this.code_sol = code_sol;
	}

	public Route (Technicien k){
	
		passageDepot= -5;
		// Creation des activité fictif qui represente les pauses
		Tache Pau1Tache = new Tache (-1, 15);	
		Activite Pause1 = new Activite (Pau1Tache, Pau1Tache.TW[0], 0, (double)Pau1Tache.TW[0][0], (double)Pau1Tache.TW[0][0], ((double)Pau1Tache.TW[0][0]+15),0);
		Tache Pau2Tache = new Tache (-2, 30);	
		Activite Pause2 = new Activite (Pau2Tache, Pau2Tache.TW[1], 1, (double)Pau2Tache.TW[1][0], (double)Pau2Tache.TW[1][0], ((double)Pau2Tache.TW[1][0]+30),0);
		Tache Pau3Tache = new Tache (-3, 15);	
		Activite Pause3 = new Activite (Pau3Tache, Pau3Tache.TW[2], 2, (double)Pau3Tache.TW[2][0], (double)Pau3Tache.TW[2][0], ((double)Pau3Tache.TW[2][0]+15),0);
		
		//Creation d'une activite fictif qui represente la maison du techncian 
		Tache HomePos = new Tache (k.position, 0);	
		double chiffre = (double)540;
		double [] debJour = {540, 2040};
		Activite TechHomeD = new Activite (HomePos,debJour ,0, chiffre, chiffre, chiffre,0);
		Activite TechHomeE = new Activite (HomePos,debJour ,0, chiffre, (chiffre+60), (chiffre+60),0);
		
		
		
		//Initialiser la route
		this.tech=k; // le tech qui effectue la route
		this.cost=0.0; // le cout de la route initialement 0
		this.lesActivite.add(TechHomeD); // la position de depart
		this.lesActivite.add(Pause1); // les pause
		this.lesActivite.add(Pause2);
		this.lesActivite.add(Pause3);
		this.lesActivite.add(TechHomeE); // la position d arrivé
		this.piece.add(k.invent); // l'inventaire de debut car les activite sont fictifs
		this.piece.add(k.invent);
		this.piece.add(k.invent);
		this.piece.add(k.invent);
		this.piece.add(k.invent);
		
		
		
	}
	
	public Route clone(){
		Route r= new Route (tech, GestionTableau.clone(lesActivite), GestionTableau.cloneVectTab(piece),cost, gain, passageDepot, code_sol);
		r.tech=this.tech;
		r.lesActivite=this.lesActivite;
		r.passageDepot=this.passageDepot;
		r.piece=this.piece;
		r.cost=this.cost;
		return r;
	}
	public void tostring (){
		System.out.print(" \n ");
		System.out.println("tech:"+tech.nom);
		for (int ii=0; ii<lesActivite.size();ii++){
			lesActivite.get(ii).ToString();
		}
		System.out.print("\n Pieces :");
		for (int ii=0; ii<piece.size();ii++){
			for(int jj=0;jj<piece.get(ii).length;jj++)
			System.out.print(piece.get(ii)[jj]+" ");
			
		}
	
	//System.out.print("\n le cout de cette route  :"+cost);
	
		
	}
	public void tostring2 (){
		System.out.print(" \n ");
		System.out.println("tech:"+tech.nom);
		System.out.print("tache :");
		for (int ii=0; ii<lesActivite.size();ii++){
		 lesActivite.get(ii).ToString();
		}
	
	
	
		System.out.print("\n Pieces :");
		for (int ii=0; ii<piece.size();ii++){
			for(int jj=0;jj<piece.get(ii).length;jj++)
			System.out.print(piece.get(ii)[jj]+" ");
		}
	
		//System.out.print("\n le cout de cette route  :"+cost);
	
		
	}
	public double CalculCost(){
		double cost = 0;
		return cost ;
	}
}
