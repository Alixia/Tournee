package outilDeBase;

import java.util.Vector;

public class Technicien {
	
	public int nom;
	public int [] Invent; 
	public double [][] pause = {{540,660},{720,900},{900,1020}};//9-10;12-15;15-17
	public int position;
	
	public Technicien (int n, int [] inv, int pos){
		nom = n;
		Invent=inv;	
		position = pos;
	}
	
	public void tostring(){
		System.out.println(" technicien "+nom+", position: "+position);
	}
	
	public Technicien clone(){
		Technicien retour = new Technicien(nom, Invent.clone(), position);
		return retour;
	}
	
	public boolean 
}
