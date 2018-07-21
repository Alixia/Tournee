package outilDeBase;

import outilGeneral.GestionTableau;

public class Technicien {
	
	public int nom;
	public int [] invent; 
	public int position;
	public int depot;
	
	public Technicien(int nom, int[] invent, int position, int depot) {
		super();
		this.nom = nom;
		this.invent = invent;
		this.position = position;
		this.depot = depot;
	}

	public String toString(){
		return " technicien "+nom+", position: "+position;
	}
	
	public Technicien clone(){
		Technicien retour = new Technicien(nom, GestionTableau.cloneTableau(invent), position, depot);
		return retour;
	}
	
}
