package outilDeBase;

import java.util.Vector;

import outilGeneral.GestionTableau;

public class Route {

	public Technicien tech ;
	public Vector <Activite> lesActivite = new Vector<>();
	public Double cost;
	public int gain;
	public int passageDepot;
	public int code_sol;		//peut etre inutile
	public Vector<int[]> piece = new Vector<>();


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
		return r;
	}

	public Route clone2(){
		Route r = new Route (tech,new Vector<Activite>(),null,cost,gain,passageDepot, 0);
		Vector <int []> vec = new Vector <int []>();

		for(int i=0; i<lesActivite.size();i++){
			Activite tmp = lesActivite.get(i);
			Activite x = new Activite(tmp.task, tmp.TWi, tmp.IndexTW, tmp.TpsArriv, tmp.TpsDebServ, tmp.TpsFinServ, tmp.distance);

			r.lesActivite.add(x);
		}
		r.piece=vec;
		for (int i=0; i<piece.size();i++){
			int [] Piece = new int [ReadData.nbrPiece];
			for(int j=0;j<ReadData.nbrPiece;j++) Piece[j]=piece.get(i)[j];
			r.piece.add(Piece);
		}
		return r;
	}

	public String toString (){
		String retour = " \n ";
		retour += "tech:"+tech.nom+"\n";
		for (int ii=0; ii<lesActivite.size();ii++){
			retour += lesActivite.get(ii).toString()+"\n";
		}
		retour +="\n Pieces :";
		for (int ii=0; ii<piece.size();ii++){
			for(int jj=0;jj<piece.get(ii).length;jj++)
				retour += piece.get(ii)[jj]+" ";

		}

		//System.out.print("\n le cout de cette route  :"+cost);
		return retour;

	}
	public void tostring2 (){
		System.out.print(" \n ");
		System.out.println("tech:"+tech.nom);
		System.out.print("tache :");
		for (int ii=0; ii<lesActivite.size();ii++){
			lesActivite.get(ii).toString();
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

	public static void add(Vector<Route> vr, Route r){
		int i=0;
		while(i<vr.size() && vr.get(i).cost < r.cost){
			i++;
		}
		vr.add(i,r);
	}
	
	public  static Vector <Route> Triroute (Vector <Route> ta) {

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


	public static Route removeDepot(Route rou, int index){
		Route r= rou.clone();
		r.lesActivite.remove(index);
		r.piece.remove(index);
		r.passageDepot=-1;
		return r;
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
	// retourne soit null soit la route r en MAJ l'inventaire 
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

	public static void insererDepot(Route r, int i){
		double [] heuredep = {540, 2040};
		Tache dep = new Tache(-1000, ReadData.TpsReap);
		Activite a = new Activite(dep, 0 , heuredep);
		r.lesActivite.add(i, a);
		r.passageDepot=i;
		r.piece.add(i, dep.Piece);
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

	public static double CalculCoutRoute(Route r){
		double cout=ReadData.mu*r.lesActivite.get(r.lesActivite.size()-1).distance;
		if (r.lesActivite.get(r.lesActivite.size()-1).TpsFinServ>1020)
			cout= cout +((r.lesActivite.get(r.lesActivite.size()-1).TpsFinServ-1020)*ReadData.psi);

		cout = cout-(ReadData.phi*r.gain);
		return cout;
	}

	public static boolean VerifierTps (Route r){
		// retourne null si on ne peut pas insere la tache faute tmps ou la route MAJ tmps et distance
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

				// On MAJ la routes
				r.lesActivite.get(i+1).TpsArriv= tpsArrivee;
				r.lesActivite.get(i+1).TpsDebServ=tpsdebu;
				r.lesActivite.get(i+1).TpsFinServ=tpsfin;


			}
			if (tpsdebu==-5) Continue = false;

			i++;

		}

		return Continue;
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
}
