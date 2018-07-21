package outilDeBase;


public class Heure {
	
	public String h;
	public int he;
	public int mn;
	
	public Heure(String j) {
		h=j;
	}
	
	public int convertirenMinute (){
		int res=0;

		res=valeurNum(CouperChaine1(this.h))*60;
		//System.out.println(" -------------------- res "+res);
		res= res+valeurNum(CouperChaine2(this.h));
		
		return res;
	}
	public String get (){
		return (this.h);
	}
	public Heure Add (double x){
		Heure res;
		int x1=(int) Math.round(x);
		int x2=this.convertirenMinute();
			
		int enmin=x1+x2;
		res=convertirenHeure(enmin);
	

		return res;
	}
	public int valeurNum  (String texte) {

	    int n = 0;

	    for (int i=0; i<texte.length(); i++) {
	        n = n*10 + (int)texte.charAt(i) - 48;
	    }
	    return n;
	}
	public String CouperChaine1 (String ch){
		// retourne une sou chaine de l' indice 0 inclu a  l indice des : exclu
		String res ="";
		int i = 0;
		boolean find = false;
		while(!find){
			if (ch.charAt(i)==':'){
				find=true;
			}
			else { if (!find)
					res=res+ch.charAt(i);
			}
			
			i++;
		}
		return res;
	}
	public String CouperChaine2 (String ch){
		// retourne une sou chaine de l' indice : exclu  a  la fin
		String res ="";
		int i = 0;
		boolean find = false;
		while(i<ch.length()){
			if (ch.charAt(i)==':'){
				find=true;
			}
			else{ if (find){
					res= res+ch.charAt(i);
					}
				
			}
			i++;
		}
		/*if (res.charAt(0)=='0' && res.length()==2){
		return res.charAt(1)+"";
		}
		else{ if (res.length()==1){
			return res.charAt(0)+"0";
		}
		else return res;
		
			
		}*/
		return res;
	}
	public static Heure convertirenHeure (int x){
		Heure res;
		
		if (x%60>=10)
			res = new Heure (x/60+":"+x%60);
		else
			res = new Heure (x/60+":0"+x%60);
		return res;
	}
	
	public boolean Compareinf (int x){
		boolean res;
		Heure nw = new Heure (x+":0");
		if (this.convertirenMinute()<=nw.convertirenMinute())
			res=true;
		else res=false;
		
			
		
		return res;
		/*
		 * *boolean res;
		Heure inter = new Heure (this.convertirenHeure(x).h);
		if (he<inter.he)
			res=true;
		else{if (inter.he==he){
			if(mn==0){
				res=true;
			}
			else{ if (mn<=inter.mn)
					res= true;
			else
				res=false;
			}
			}
		else res=false;
		
			
		}
		
		return res;
		 */
		
	}
	public boolean Compareinf2 (int x){ //compare heure avec des minutes
		boolean res;
		int result=this.convertirenMinute();
		if (result<=x)
			res=true;
		else{res=false;
			}
		
		
			
		
		return res;
		
	}
	public boolean CompareSup2 (int x){ //compare heure avec des minutes
		boolean res;
		
		int result=this.convertirenMinute();
		if (result>=x)
			res=true;
		else{res=false;
			}
		
			
		
		
		return res;
		
	}
	public boolean Comparesup (int x){
		boolean res;
		Heure ne = new Heure(x+":0");
		if (this.convertirenMinute ()>=ne.convertirenMinute())
			res=true;
		else res=false;		

		return res;
		
	}
	
	public boolean compareegale (Heure x){
		if (this.h.equalsIgnoreCase(x.h))
			return true;
		else return false;

		
	}
	public void ecrire (){
		System.out.println(h);
	}
	public Heure sousstraire (int x){
		Heure aux;
		int min = this.convertirenMinute ();
		int min1=x*60;
		min=min-min1;
		aux=convertirenHeure(min);
		return aux;
		
	}

}
