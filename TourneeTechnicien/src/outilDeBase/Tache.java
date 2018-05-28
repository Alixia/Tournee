package outilDeBase;

import outilGeneral.GestionTableau;

public class Tache {
	
	public int nom;
	public double [][]TW;
	public int [] Piece = new int [ReadData.nbrPiece];
	public int PTab;
	public int prio;			//penalité/ Gain
	public int TpsRepa;
	
	//constructeur tache quelconque
	public Tache (int n, double [][] tw, int [] p, int Pt, int pr, int tps){
		nom = n; 
		TW=tw;
		Piece=p;
		PTab=Pt;
		prio=pr;
		TpsRepa=tps;
	}
	
	//constructeur tache pause ou maison
	public Tache (int n, int tps){
		double [][] pause = {{540,660},{720,900},{900,1020}};//9-10;12-15;15-17
		double [][]  debJour = {{540, 2040},{540, 2040}};
		nom= n;
		TpsRepa= tps;
		if (n<0 && n>-1000 ){
			TW= pause;
			for(int i=0;i<ReadData.nbrPiece;i++) Piece[i]=0;
		}
		else{		
			TW= debJour;
			if (n>=0){
				for(int i=0;i<ReadData.nbrPiece;i++) Piece[i]=0;
			}
			else{
				for(int i=0;i<ReadData.nbrPiece;i++) Piece[i]=20;
			}
		}
		
		
	}
	
	public String toString (){
		String res = ("Task: "+this.nom+"\n"+"PTab: "+this.PTab+"\n"+"prio: "+this.prio+"\n"+"TpsReap: "+this.TpsRepa+"\n");
		return res; 
	}

	public Tache clone(){
		double[][] copieTW;
		if(TW == null ){
			copieTW = null;
		}else{
			copieTW =  GestionTableau.clone(TW);
		}
		int[] copiePiece;
		if(Piece == null){
			copiePiece = null;
		}else{
			copiePiece = Piece.clone();
		}
		Tache retour = new Tache(nom, copieTW, copiePiece, PTab, prio, TpsRepa);
		return retour;
	}
	
	public boolean isPause(){
		if(nom<0 || nom>=ReadData.tache){
			return true;
		}
		return false;
	}
}
