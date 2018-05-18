package prout;

import java.util.Vector;

import outilDeBase.Activite;
import outilDeBase.ReadData;
import outilDeBase.Route;
import outilDeBase.Solution;
import outilDeBase.Tache;
import outilDeBase.Technicien;

public class SolutionInitiale {
public Vector <Tache> tache = new Vector<Tache>();
public Vector <Technicien> tech = new Vector<Technicien>();
public static int  nbrSol;

public  SolutionInitiale() {
	
}
public  Solution Solution_Initiales (int n){
	
	
		Solution s=UneSolution();
		

		
	

	
	
	/*for (int i=0;i<TTeLesSolutions.size();i++){
		System.out.println("");
		System.out.print("***********Solution "+i+" ***********");
		System.out.println("");
		TTeLesSolutions.get(i).ToString();
	}*/
	
	return s;
}
public static void CalculerLesCouts(Vector<Solution> TTeLesSolutions){
	for (int i=0;i<TTeLesSolutions.size();i++){
		
		for (int j=0; j<TTeLesSolutions.get(i).sol.size();j++){
			if (TTeLesSolutions.get(i).sol.get(j).lesActivite.size()<6){
			TTeLesSolutions.get(i).sol.remove(j); // dans cette Route le technicien ne fait aucune acctivité
			}
			
		}
		TTeLesSolutions.get(i).Calcul_costsol(); // On calcule les couts de chaque solution
		
	}
	
	
}
public static int Calcul_gain(Route r){
	int somme = 0;
	for (int i=0; i<r.lesActivite.size();i++){
		if(r.lesActivite.get(i).task.nom>=0 &&r.lesActivite.get(i).task.nom<ReadData.tache ){
			somme = somme + ReadData.alpha[r.lesActivite.get(i).task.nom];
		}
	}
	return somme;
}

public static  Vector <Solution> TriSolutionCostTW (Vector <Solution> ta) {

	   Vector <Solution> ta2 = new Vector<Solution>();
	  int i =0;
	   while (i<ta.size()){
		   int j = i+1;
		  double  max= ta.get(i).costVoisin;
		  int indexmax=i;
		   while (j<ta.size()){
				  if (ta.get(j).costVoisin>max) {
					  max=ta.get(j).costVoisin;
					  indexmax=j;
				  }
				  
				  j++;
		   }
		   ta2.add(ta.get(indexmax));
		   ta.remove(indexmax);
		  
	   }
	   return ta2;
	}
public static int findTaskOnRoute( int ta, Route r){
	int index=-1;
	int i=0;
	while (index==-1 && i<r.lesActivite.size()){
		if (r.lesActivite.get(i).task.nom==ta) index=i;
		i++;
	}
	if (index==-1) return -1;
	else return r.lesActivite.get(index).IndexTW;
}
public void NetoyerRoute (Route r){

	// sila Route finit par une pause et retour a la maison alors on les supprime et on met a jour le temps
	if (r.lesActivite.get((r.lesActivite.size()-2)).task.nom<0){
		int x= r.lesActivite.size()-2;
		while (r.lesActivite.get(x).task.nom <0 && r.lesActivite.get(x).task.nom !=1000 )x--;
		double TpsARetrancher  = 0;
		for (int i=x+1;i<=r.lesActivite.size()-2;i++){
			TpsARetrancher=TpsARetrancher+r.lesActivite.get(i).task.TpsRepa;
			r.lesActivite.remove(i);
		}
		r.lesActivite.get(r.lesActivite.size()-1).TpsArriv=r.lesActivite.get(r.lesActivite.size()-1).TpsArriv-TpsARetrancher;
		r.lesActivite.get(r.lesActivite.size()-1).TpsDebServ=r.lesActivite.get(r.lesActivite.size()-1).TpsDebServ-TpsARetrancher;
		r.lesActivite.get(r.lesActivite.size()-1).TpsFinServ=r.lesActivite.get(r.lesActivite.size()-1).TpsFinServ-TpsARetrancher;

	}
	r.gain=Calcul_gain(r);
	r.cost=CalculCoutRoute(r);
	
}
public Solution UneSolution(){
	Vector <Solution> LesSolutions = new Vector<Solution>();
	Vector <Integer> technicienCreer = new Vector<Integer>();
	Vector <Integer> TacheFaiteCreer = new Vector<Integer>();
	
		technicienCreer.clear();
		TacheFaiteCreer.clear();
		Intialisertache ();
		
		InitialiserTechnician();
	//1- on choisit aleatoirement un technicien pour commencer a creer la solution
	int startTech= (int)(Math.random() * tech.size());
	Technicien Technb= tech.get(startTech);
	technicienCreer.add(Technb.nom);

	Route r= new Route(Technb);
	Vector <Route> MesRoutes =CreerRourte(Technb, r, TacheFaiteCreer);
	for(int i=0; i<MesRoutes.size();i++){
		Solution sol= new Solution();
		sol.sol.add(MesRoutes.get(i));
		for (int s=0; s<MesRoutes.get(i).lesActivite.size();s++){
			sol.tacheFaite.add(MesRoutes.get(i).lesActivite.get(s).task.nom);
		}
		
		LesSolutions.add(sol);
		
	}
	
		// On Continue a creer les solutions en se basant sur les Routes generer en haut
		while (technicienCreer.size()<ReadData.tech){
			r=null;
			do {
				startTech= (int)(Math.random() * tech.size());
				}
			while(technicienCreer.indexOf(tech.get(startTech).nom)!=-1);
				Technb= tech.get(startTech);
				technicienCreer.add(Technb.nom);
				r= new Route(Technb);
				for (int s=0; s<LesSolutions.size();s++){
					MesRoutes.clear();
					MesRoutes =CreerRourte(Technb, r,LesSolutions.get(s).tacheFaite);
					if (MesRoutes.size()==1){// On a une seule Route resultante
						LesSolutions.get(s).sol.add(MesRoutes.get(0));
						 for (int ss=0; ss<MesRoutes.get(0).lesActivite.size();ss++){
							 LesSolutions.get(s).tacheFaite.add(MesRoutes.get(0).lesActivite.get(ss).task.nom);
							}

					 }
					 else{// On a generer plusieurs Routes Jumelles
						 // On cree autant de solution que les Routes jumelles
						 if (MesRoutes.size()>1){

							 int rt=0;
							 Solution sol2= new Solution();
							 sol2.sol.addAll( LesSolutions.get(s).sol);
							 sol2.tacheFaite=LesSolutions.get(s).tacheFaite;

							 LesSolutions.remove(s);

							 while (rt<MesRoutes.size()){							
								 Solution sol3= new Solution();
								 sol3.sol.addAll( sol2.sol);
								 sol3.tacheFaite=(Vector<Integer>) sol2.tacheFaite.clone();
								 sol3.sol.add(MesRoutes.get(rt));
								 for (int ss=0; ss<MesRoutes.get(rt).lesActivite.size();ss++){
									 sol3.tacheFaite.add(MesRoutes.get(rt).lesActivite.get(ss).task.nom);
									}
								 LesSolutions.add(s,sol3);
								

								/*
								 if(rt==0){
									 // la premiere Route va etre jumeler avec la solution de base
									 LesSolutions.get(s).sol.add( (LesSolutions.get(s).sol.size()-1),MesRoutes.get(0));
									 for (int ss=0; ss<MesRoutes.get(0).lesActivite.size();ss++){
										 LesSolutions.get(s).tacheFaite.add(MesRoutes.get(0).lesActivite.get(ss).task.nom);
										} 
								 }
								 else{
									 // le reste on va dupliquer la Route de base
									 Solution sol= new Solution();
									 sol.sol.addAll( LesSolutions.get(s).sol);
									 sol.sol.remove(sol.sol.size()-1);
									 sol.sol.add(MesRoutes.get(rt));
									 sol.tacheFaite.addAll(LesSolutions.get(s).tacheFaite);
									 for (int ss=0; ss<MesRoutes.get(rt).lesActivite.size();ss++){
										 sol.tacheFaite.add(MesRoutes.get(rt).lesActivite.get(ss).task.nom);
										}
									 LesSolutions.add(sol);
								 }*/
								 rt++;

							 }
							 s=s+MesRoutes.size();

					
					 }
						 
				}

		}
		}
		
		
	CalculerLesCouts(LesSolutions);
	
	LesSolutions =SolutionInitiale.TriSolutionCost(LesSolutions);
return LesSolutions.get(0);
}
public void  Intialisertache (){
	// On remplie tableau des taches
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
		tache.add(new Tache (i,tw, p, ReadData.pieceTab[i],ReadData.alpha[i],ReadData.sigma[i]));
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
			
				tachetech =TritacheGain(tachetech);
				
				
				Technicien te= new Technicien (k, inv, ReadData.tache+k, ReadData.dep[k], tachetech);
				tech.add(te);
				
				
			}
}
public static  Vector <Tache> TritacheGain (Vector <Tache> ta) {

   Vector <Tache> ta2 = new Vector<Tache>();
  int i =0;
   while (i<ta.size()){
	   int j = i+1;
	  int  max= ta.get(i).prio;
	  int indexmax=i;
	   while (j<ta.size()){
			  if (ta.get(j).prio>max) {
				  max=ta.get(j).prio;
				  indexmax=j;
			  }
			  
			  j++;
	   }
	   ta2.add(ta.get(indexmax));
	   ta.remove(indexmax);
	  
   }
   return ta2;
}
public  static Vector <Route> TriRoute (Vector <Route> ta) {

	   Vector <Route> ta2 = new Vector<Route>();
	  int i =0;
	   while (i<ta.size()){
		   int j = i+1;
		  double  min= ta.get(i).cost;
		  int indexmin=i;
		   while (j<ta.size()){
				  if (ta.get(j).cost<min) {
					  min=ta.get(j).cost;
					  indexmin=j;
				  }
				  
				  j++;
		   }
		   ta2.add(ta.get(indexmin));
		   ta.remove(indexmin);
		  
	   }
	   return ta2;
	}
public  Vector <Solution> TriSolutionBF (Vector <Solution> ta) {

	   Vector <Solution> ta2 = new Vector<Solution>();
	  int i =0;
	   while (i<ta.size()){
		   int j = i+1;
		  double  max= ta.get(i).BiasedFitness;
		  int indexmax=i;
		   while (j<ta.size()){
				  if (ta.get(j).BiasedFitness>max) {
					  max=ta.get(j).BiasedFitness;
					  indexmax=j;
				  }
				  
				  j++;
		   }
		   ta2.add(ta.get(indexmax));
		   ta.remove(indexmax);
		  
	   }
	   return ta2;
	}
public  static Vector <Solution> TriSolutionCost (Vector <Solution> ta) {

	   Vector <Solution> ta2 = new Vector<Solution>();
	  int i =0;
	   while (i<ta.size()){
		   int j = i+1;
		  double  min= ta.get(i).costsol;
		  int indexmin=i;
		   while (j<ta.size()){
				  if (ta.get(j).costsol<min) {
					  min=ta.get(j).costsol;
					  indexmin=j;
				  }
				  
				  j++;
		   }
		   ta2.add(ta.get(indexmin));
		   ta.remove(indexmin);
		  
	   }
	   return ta2;
	}
public Vector <Route> CreerRourte(Technicien k, Route r, Vector<Integer>vect){
	int i =0; 
	Vector <Route> MesRoutes= new Vector<Route>();
	MesRoutes.add(r);
	while ( i<k.tachetech.size()){
		if (vect.indexOf(k.tachetech.get(i).nom)==-1){
		
		int j=0;
	
		while (j<MesRoutes.size()){
		
			Route New = MesRoutes.get(j).clone();
			
			Vector <Route> LesRoutesPossible= GetPossibleInsertion (k.tachetech.get(i), New);
			if (!LesRoutesPossible.isEmpty()){
				//vect.add(k.tachetech.get(i).nom);
				if (MesRoutes.get(j).passageDepot<0 && LesRoutesPossible.get(0).passageDepot>0){
					// on a generer des Routes jumelles dans on gardes les 3 meilleure
					MesRoutes.remove(j);
					int t=0;
					while (t<LesRoutesPossible.size()&& t<3){
						MesRoutes.add(j,LesRoutesPossible.get(t));
						t++;
						j++;
					}
				}
				else{// on n a pas generer des Routes jumelles donc on garde l'une des 3 meilleure solution
					int oneBest;
					if (LesRoutesPossible.size()>3)
						oneBest = (int)(Math.random() * 3);
					else   oneBest= (int)(Math.random() * LesRoutesPossible.size());
					MesRoutes.remove(j);
					MesRoutes.add(j,LesRoutesPossible.get(oneBest));
					j++;
				}
				
			}
			j++;
		}
	}
		i++;
		
	}
	
	return MesRoutes;
}

public static Vector <Route> GetPossibleInsertion (Tache t, Route r){
	Vector <Route> PossibleInsertion = new Vector<Route>();
	// retourne un vecteur avec les 3 meilleur insertion pour la Tache t
	
	for (int i=1; i<r.lesActivite.size()-1;i++){

		Route Newr=r.clone();
		Vector <Route> NewR= evaluerInsertion(Newr, t, i);
		
		if (!NewR.isEmpty()){
			double min = NewR.get(0).cost;
			int index = 0;
			int j=1;
			while( j<NewR.size()){
				if (NewR.get(j).cost<min){
					min = NewR.get(j).cost;
					index=j;
				}
				j++;
			}
			PossibleInsertion.add(NewR.get(index));
			
		
		}
		
		
	}

	PossibleInsertion=TriRoute(PossibleInsertion);
	
return PossibleInsertion;
}
public static Route deplacerdepot(Route rou, int pos){
	Route r = rou.clone();
	//r= Tabu.removeDepot(r, r.passageDepot);
	Vector<Route> vecR= GenererRouteSDepot(r, pos-1);
	if (vecR.isEmpty()) return null;
	else{
		vecR= TriRoute(vecR);
		return vecR.get(0);
	}
	
}
public static Vector <Route> evaluerInsertion (Route r, Tache t, int pos){
	Vector <Route> MesRoutes = new Vector<Route>();
	// retourne soit null soit une Route en inserant la tache entre pos et pos+1
	
	Route NewR= r.clone();
	
	boolean passageDepot= false;
	
	NewR= InsererLaTache(NewR,t, pos+1);
	NewR= MAJInven(NewR);
	if (NewR!= null ){
		//On a MAJ les Pieces et il n y a pas de probleme 
		if (t.PTab==1){ // la tache a besoin d'une piece special
			passageDepot=true;
			if (NewR.passageDepot>=0 && NewR.passageDepot<= pos){
				
				passageDepot= false;// si avant la position ou on va inserer il y a un passage depot alors c bon
			}
			else{
				if(r.passageDepot>=0 && r.passageDepot> pos){
					Route rx= deplacerdepot(r, pos);
					if(rx!=null){
						NewR=null;
						NewR=rx.clone();
						passageDepot= false;
						
					}
				}
			}
			
			
			
		}
		if (!passageDepot){ // soit elle n 'a pas besoin de Piece Tab soit il y a un passage depot avant
			int index= VerifTW(NewR, t, pos);
			if (index != -1){// on a une fenetre de temps qu'on peut utiliser
				NewR.lesActivite.get(pos+1).IndexTW= index;
				NewR.lesActivite.get(pos+1).TWi=t.TW[index];
				boolean Continue = VerifierTps(NewR);				
				if (Continue){
					// on MAJ les distance
					Continue = VerifierDistance(NewR);
					if (Continue){
						//System.out.println(" distance ");
						if (NewR.lesActivite.get(NewR.lesActivite.size()-1).distance>ReadData.dmax) Continue= false;
						// si la distance ne depasse pas la distance max on Calcule le cout
						NewR=MAJInven(NewR);
						if (Continue && NewR!= null){
							//System.out.println("update Inventory");
							NewR.gain=Calcul_gain(NewR);
							NewR.cost=CalculCoutRoute(NewR);
							
							MesRoutes.add(NewR);
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	

		// l'inventaire ne suffit pas il faut verifier le passage au depot
		if ((NewR==null  && r.passageDepot<0)||( r.passageDepot<0 && passageDepot)){// probleme d'inventaire et on a pas dans la Route un passage au depot
			Route newRd=r.clone();
			newRd= InsererLaTache(newRd,t, pos+1);
			int index= VerifTW(newRd, t, pos);
			if (index != -1){// on a une fenetre de temps qu'on peut utiliser
				newRd.lesActivite.get(pos+1).IndexTW= index;
				newRd.lesActivite.get(pos+1).TWi=t.TW[index];
				boolean Continue = VerifierTps(newRd);				
				if (Continue){
					// on MAJ les distance
					Continue = VerifierDistance(newRd);
					newRd.gain= Calcul_gain(newRd);
					newRd.cost=CalculCoutRoute(newRd);
					if (Continue){
						 Vector <Route> lesjumelles=GenererRouteSDepot(newRd, pos);
						if (!lesjumelles.isEmpty()) MesRoutes.addAll(lesjumelles);
					}
					
					
				}
				
			}
			
			
		}
		else{if (NewR==null && r.passageDepot>pos){
			Route rx=deplacerdepot(r, pos);
			if (rx!=null){
				Route newRd= rx.clone();
				newRd= InsererLaTache(newRd,t, pos+1);
				int index= VerifTW(newRd, t, pos);
				if (index != -1){// on a une fenetre de temps qu'on peut utiliser
					newRd.lesActivite.get(pos+1).IndexTW= index;
					newRd.lesActivite.get(pos+1).TWi=t.TW[index];
					boolean Continue = VerifierTps(newRd);				
					if (Continue){
						// on MAJ les distance
						Continue = VerifierDistance(newRd);
						newRd.gain= Calcul_gain(newRd);
						newRd.cost=CalculCoutRoute(newRd);
						if (Continue){
							 Vector <Route> lesjumelles=GenererRouteSDepot(newRd, pos);
							if (!lesjumelles.isEmpty()) MesRoutes.addAll(lesjumelles);
						}
						
					}
					
				}
				
				
			}
		}
			
		}
		
		
	
	
	return MesRoutes;
}
public static Vector <Route> GenererRouteSDepot(Route r, int pos){
	// retourne un vecteur de Route jumelle a r ou on essaye d inserer le depot entre 1 et pos
	Vector <Route> MesRoutes = new Vector<Route>();
	int i=1;
	while (i<=pos){
		
		Route r1=r.clone();
		r1=genererRouteJumelle(r1, i);

		if (r1!=null ){
			
				MesRoutes.add(r1);
				//r1.tostring2();
			
		}
		i++;
	}
	return MesRoutes;
}
public static Route genererRouteJumelle(Route r, int i){
	// retourne une r en  inserant le depot a i ou null sinon 
	Route r1= r.clone();
	insererdepot(r1,i);
	
	boolean Continue =VerifierTps(r1);
	if (Continue){
		// on MAJ les distance
		Continue = VerifierDistance(r1);
	
		if (Continue){	
			// si la distance ne depasse pas la distance max on Calcule le cout
			 r1=MAJInven(r1);
			
			if (r1!=null){
				r1.gain= Calcul_gain(r1);
				r1.cost=CalculCoutRoute(r1);
				
			}
		
			
		}
	}
	if (!Continue) return null;
	return r1;
}
public static Route MAJInven (Route r){
	Route r1=r.clone();
	int i=1; 
	
	boolean Continue = true;
	while (i<r1.piece.size() && Continue){
		if (r1.lesActivite.get(i).task.nom!=-1000){
			 int w=0;
			 int [] p =   new int [ReadData.nbrPiece] ;
			 while (w<ReadData.nbrPiece && Continue ){
				 p[w]=r1.piece.get(i-1)[w]-r1.lesActivite.get(i).task.Piece[w];
				 if (p[w]>=0){ w++;}
				 else Continue = false;
			 }
			 if (Continue){
				 r1.piece.removeElementAt(i);		
				 r1.piece.add(i, p);
				 i++;
			}
		}
		else{i++;}
	}
	
	if (!Continue) {
		r1=null;	
	}
	return r1;
}
public static void insererdepot(Route r, int i){
	double [] heuredep = {540, 2040};
	Tache dep = new Tache(-1000, ReadData.TpsReap);
	Activite a = new Activite(dep, 0, heuredep);
	r.lesActivite.add(i, a);
	r.passageDepot=i;
	r.piece.add(i, dep.Piece);
	
	
}
public static double CalculCoutRoute(Route r){
	double cout=ReadData.mu*r.lesActivite.get(r.lesActivite.size()-1).distance;
	if (r.lesActivite.get(r.lesActivite.size()-1).TpsFinServ>1020)
		cout= cout +((r.lesActivite.get(r.lesActivite.size()-1).TpsFinServ-1020)*ReadData.psi);
	
	cout = cout-(ReadData.phi*r.gain);
	return cout;
}
public static boolean VerifierDistance(Route r){
	boolean Continue = true;
	int i=0;
	while (i<r.lesActivite.size()-1 && Continue){
	double  distance= r.lesActivite.get(i).distance;
	//1- cas si pos est une tache 
	if (r.lesActivite.get(i).task.nom>=0){
		//1.1 cas ou pos+1 est une tache
		if (r.lesActivite.get(i+1).task.nom>=0){
			distance= distance+ReadData.distance[r.lesActivite.get(i+1).task.nom][r.lesActivite.get(i).task.nom];
		}
		else{// 1.2 cas ou pos+1 est un passage au depot
			if (r.lesActivite.get(i+1).task.nom==-1000){
				distance= distance+ReadData.distanced[r.tech.depot][r.lesActivite.get(i).task.nom];
	
			}
			else{// 1.3 cas ou pos+1 est une pause
				if (r.lesActivite.get(i+1).task.nom<0){
					 distance= r.lesActivite.get(i).distance;
				}
			}
		}
	}
	else{
		//2- cas si pos est une passage au depot
		if (r.lesActivite.get(i).task.nom==-1000){
			//2.1 cas ou pos+1 est une tache
			if (r.lesActivite.get(i+1).task.nom>=0){
				distance= distance+ReadData.distanced[r.tech.depot][r.lesActivite.get(i+1).task.nom];
			}
			else{//2.2 cas ou pos+1 est une pause
				if (r.lesActivite.get(i+1).task.nom<0){
					distance= r.lesActivite.get(i).distance;
				}
			}
		}
		else{
			// 3- cas si pos est une pause
			int x= i;
			while (r.lesActivite.get(x).task.nom<0 && r.lesActivite.get(x).task.nom!=-1000) x--;
			//3.1 cas si avant pos y a un depot
			if (r.lesActivite.get(x).task.nom==-1000){
				//3.1.1 cas ou pos+1 est une tache
				if (r.lesActivite.get(i+1).task.nom>=0){
					distance= distance+ReadData.distanced[r.tech.depot][r.lesActivite.get(i+1).task.nom];
				}
				else{//3.1.2 cas ou pos+1 est une pause
					if (r.lesActivite.get(i+1).task.nom<0){
						distance= r.lesActivite.get(x).distance;
					}
					
				}
			}
			else{//3.2 cas si avant pause y a une tache 
				//3.2.1 cas ou pos+1 est une tache 
				if (r.lesActivite.get(i+1).task.nom>=0){
					distance= distance+ReadData.distance[r.lesActivite.get(x).task.nom][r.lesActivite.get(i+1).task.nom];
				}
				else{//3.2.2 cas ou pos+1 est un depot
					if (r.lesActivite.get(i+1).task.nom==-1000){
						distance= distance+ReadData.distanced[r.tech.depot][r.lesActivite.get(x).task.nom];

					}
					else{//3.2.3 cas ou pos+1 est une pause
						distance= r.lesActivite.get(x).distance;
					}
				}
			}
		}
	}
	
	 r.lesActivite.get(i+1).distance= distance;	
	

	i++;
	}
	
	if(r.lesActivite.lastElement().distance>ReadData.dmax) Continue =false;
	
	return Continue;
	
}
public static Route InsererLaTache (Route r, Tache t, int pos){	
	Activite a = new Activite(t);	
	r.lesActivite.add(pos, a);
	r.piece.add(pos, t.Piece);
	r.gain=r.gain+t.prio;
	return r;
}
public static Route VerifInventaire (Route r, Tache t, int pos){;
	 // retourne soit null soit la Route r en MAJ l'inventaire 
	Route RoutesRes = r.clone();
	 boolean Continue = true ;
	 int i=pos;
	 int [] p =   new int [ReadData.nbrPiece] ;

	 while (i<RoutesRes.piece.size()-1 && Continue && RoutesRes.lesActivite.get(i+1).task.nom!=-1000){ // tant que l'insertion est permise en continue a MAJ
		int w=0;
		while (w<ReadData.nbrPiece && Continue){
			 p[w]=RoutesRes.piece.get(i)[w]-(RoutesRes.lesActivite.get(i+1).task.Piece[w]);
			 if (p[w]>=0){			
				 w++;
			 }
			 else Continue = false;
		 }	  
		 RoutesRes.piece.removeElementAt(i+1);
		 RoutesRes.piece.add(i+1, p);
		
		 i++;
	 }
	if (!Continue) return null;
	return RoutesRes;
	 
 }
public static int VerifTW (Route r, Tache t, int pos){

	// on caclucule le tmps prevu d arriver a la nouvelle tache 
	double arriveTime = r.lesActivite.get(pos).TpsFinServ; // tps fin de la premiere tache
	// +temps de deplacement 
	// 1- cas on insere apres une taches
	if (r.lesActivite.get(pos).task.nom>=0){
		arriveTime=arriveTime+ReadData.Temps[r.lesActivite.get(pos).task.nom][t.nom];
	}
	else{ //2- cas on insere apres un depot 
		if (r.lesActivite.get(pos).task.nom==-1000){
		arriveTime=arriveTime+ReadData.Tempsd[r.tech.depot][t.nom];
		}
		else{//  3- cas on insere apres une pause
			int x=pos;
			while (r.lesActivite.get(x).task.nom<0 && r.lesActivite.get(x).task.nom!=-1000 ) x--; // on cherche la derniere position du tech 
			
			if (r.lesActivite.get(x).task.nom==-1000){
				arriveTime=r.lesActivite.get(pos).TpsFinServ+ReadData.Tempsd[r.tech.depot][t.nom];
				}
			else{
			arriveTime=r.lesActivite.get(pos).TpsFinServ+ReadData.Temps[r.lesActivite.get(x).task.nom][t.nom];}
		}
	}

	int index = -1;
	int i=0;
	while (i<ReadData.nbrTw){
		if ((t.TW[i][0]>=arriveTime)||((t.TW[i][0]<arriveTime) && (t.TW[i][1]>=arriveTime))){
			if (index!=-1){
				if (t.TW[i][0]<t.TW[index][0]) index = i;
			}
			else index=i;
		}
		i++;
	}
	return index;
}
public static double CalculeTpsArrivee(Route r, int pos){
	double tpsArrivee = r.lesActivite.get(pos).TpsFinServ;
	//1 cas 1 pos est une tache
	if (r.lesActivite.get(pos).task.nom>=0){
		//1.1 cas 1.1 pos+1 est une tache
		
		if (r.lesActivite.get(pos+1).task.nom>=0){
			tpsArrivee=tpsArrivee+ReadData.Temps[r.lesActivite.get(pos).task.nom][r.lesActivite.get(pos+1).task.nom];
			
		}
		else{//1.2 cas pos+1 est un passage au depot
			if (r.lesActivite.get(pos+1).task.nom==-1000){
				tpsArrivee=tpsArrivee+ReadData.Tempsd[r.tech.depot][r.lesActivite.get(pos).task.nom];
			}
			else{//1.3 cas pos+1 est une pause
				
				tpsArrivee = r.lesActivite.get(pos).TpsFinServ;
				
			}
		}
	}
	//2 cas 2 si pos est un passage depot
	if (r.lesActivite.get(pos).task.nom==-1000){
		//2.1 cas 2.1 pos+1 est une tache
		if (r.lesActivite.get(pos+1).task.nom>=0){
			tpsArrivee=tpsArrivee+ReadData.Tempsd[r.tech.depot][r.lesActivite.get(pos+1).task.nom];
		}
		else{//2.2 cas pos+1 est une pause
			tpsArrivee = r.lesActivite.get(pos).TpsFinServ;
		}
	}
	//3 cas 3 si pos est une pause
	if (r.lesActivite.get(pos).task.nom<0 && r.lesActivite.get(pos).task.nom!=-1000){
		if (r.lesActivite.get(pos+1).task.nom<0 && r.lesActivite.get(pos+1).task.nom!=-1000){
			tpsArrivee = r.lesActivite.get(pos).TpsFinServ;
		}
		else{
			int x=pos;
			while(r.lesActivite.get(x).task.nom<0 && r.lesActivite.get(x).task.nom!=-1000 ) x--; //on cherche l'activité apres la ou les pauses
			if (r.lesActivite.get(pos+1).task.nom>=0){
				if (r.lesActivite.get(x).task.nom==-1000){
					tpsArrivee=r.lesActivite.get(pos).TpsFinServ+ReadData.Tempsd[r.tech.depot][r.lesActivite.get(pos+1).task.nom];
				}
				else{
					tpsArrivee=r.lesActivite.get(pos).TpsFinServ+ReadData.Temps[r.lesActivite.get(x).task.nom][r.lesActivite.get(pos+1).task.nom];

				}
			}
			else{	if (r.lesActivite.get(pos+1).task.nom<0 && r.lesActivite.get(pos+1).task.nom!=1000){
				tpsArrivee = r.lesActivite.get(pos).TpsFinServ;
			}
			else tpsArrivee=r.lesActivite.get(pos).TpsFinServ+ReadData.Tempsd[r.tech.depot][r.lesActivite.get(x).task.nom];

			}
		}
		
	}
	return tpsArrivee;
}
public static double CalculeDebutdeService (Route r, int pos, double tpsArr){
	double tpsdebu=-5;
	if (r.lesActivite.get(pos+1).task.nom>=0){
		if (tpsArr<r.lesActivite.get(pos+1).TWi[0]){
			tpsdebu=r.lesActivite.get(pos+1).TWi[0];
		}
		else{
			if ((tpsArr>=r.lesActivite.get(pos+1).TWi[0]) &&(tpsArr<=r.lesActivite.get(pos+1).TWi[1])){
				tpsdebu=tpsArr;
			}
			else{
				tpsdebu=-5;
			}
			
		}
		if(tpsdebu==-5){
			Tache t= r.lesActivite.get(pos+1).task;
			int twi=VerifTW ( r,  t,  pos);
			//System.out.println(" We try other twi "+r.lesActivite.get(pos+1).IndexTW+" by "+twi);
			if (twi!=-1){
				if (tpsArr<t.TW[twi][0]){
					tpsdebu=t.TW[twi][0];
				}
				else{
					if ((tpsArr>=t.TW[twi][0]) &&(tpsArr<=t.TW[twi][1])){
						tpsdebu=tpsArr;
						r.lesActivite.get(pos+1).IndexTW=twi;
						r.lesActivite.get(pos+1).TWi=t.TW[twi];
					}
					else{
						tpsdebu=-5;
					}
					
				}
			}
		}
	}
	else{
		if (r.lesActivite.get(pos+1).task.nom==-1000) tpsdebu= tpsArr; // pos+1 est un passage au depot
		else{// pos+1 est une pause
			if (tpsArr<=r.lesActivite.get(pos+1).TWi[0]){
				tpsdebu=r.lesActivite.get(pos+1).TWi[0];
			}
			else{
			if ((tpsArr>=r.lesActivite.get(pos+1).TWi[0]) && (tpsArr<=r.lesActivite.get(pos+1).TWi[1])){
				tpsdebu=tpsArr;
				

			}
			else {
				

				tpsdebu=-5;
			}
			}
			
		}
	}

	
	
return tpsdebu;
}
public static boolean VerifierTps (Route r){
	// retourne null si on ne peut pas insere la tache faute tmps ou la Route MAJ tmps et distance
	boolean Continue = true;
	int i=0;
	while (i<r.lesActivite.size()-1 && Continue ){
		double tpsArrivee ;
		double tpsdebu;
		double tpsfin ;
		
		//1- Calcule Temps d'arrivé à pos+1
		tpsArrivee=CalculeTpsArrivee(r, i);
		//2- CALCUL dDEBUT DE SERVICE
		tpsdebu= CalculeDebutdeService(r, i, tpsArrivee);
		//System.out.println(" pos: "+pos+"- TpsArr: "+tpsArrivee+"- tpsdebu"+tpsdebu);
		//3-Calcul fin de service 
			if (Continue && tpsdebu!=-5){
		
				if (r.lesActivite.get(i+1).task.nom>=0) tpsfin=tpsdebu+r.lesActivite.get(i+1).task.TpsRepa;
				else{
					if (r.lesActivite.get(i+1).task.nom==-1000) tpsfin=tpsdebu+ReadData.TpsReap;
					else{
						tpsfin=tpsdebu+r.lesActivite.get(i+1).task.TpsRepa;
					}
				}
				
				// On MAJ la Routes
				r.lesActivite.get(i+1).TpsArriv= tpsArrivee;
				r.lesActivite.get(i+1).TpsDebServ=tpsdebu;
				r.lesActivite.get(i+1).TpsFinServ=tpsfin;
				
				
			}
			if (tpsdebu==-5) Continue = false;
			
			i++;
		
	}
	
	return Continue;
}

}
