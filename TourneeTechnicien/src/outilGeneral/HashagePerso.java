package outilGeneral;

import java.util.Vector;

public class HashagePerso {
	Vector<Integer> hash;
	
	public HashagePerso() {
		hash = new Vector<>();
	}

	public boolean add(int valeur) {
		int i = 0; 
		while(i<hash.size() && hash.get(i)<valeur) {
			i++;
		}
		if(hash.get(i)==valeur) {
			return false;
		}
		hash.add(i, valeur);
		return true;
	}
	
}
