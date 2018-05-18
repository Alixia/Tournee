package outilDeBase;

import java.util.Vector;

public class Technicien {
	
	public int nom;
	public int [] invent; 
	public int position;
	public int depot;
	public Vector <Tache> tachetech = new Vector<Tache>();

	
	public Technicien(int nom, int[] invent, int position, int depot, Vector<Tache> tachetech) {
		super();
		this.nom = nom;
		this.invent = invent;
		this.position = position;
		this.depot = depot;
		this.tachetech = tachetech;
	}

	public void tostring(){
		System.out.println(" technicien "+nom+", position: "+position);
	}
	
	public Technicien clone(){
		Technicien retour = new Technicien(nom, invent.clone(), position, depot, tachetech);
		return retour;
	}
	
	//public boolean 
}
