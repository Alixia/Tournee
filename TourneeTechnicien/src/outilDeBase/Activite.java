package outilDeBase;


public class Activite {
	
	public Tache task;
	public double [] TWi;
	public int IndexTW;
	public double TpsArriv;
	public double TpsDebServ;
	public double TpsFinServ;
	public double distance;
	
	 public Activite (Tache t, double [] TW, int i, double tpsA, double tpsD, double tpsF, double dist ){
		 task = t;
		 TWi=TW;
		 IndexTW=i;
		 TpsArriv= tpsA;
		 TpsDebServ=tpsD;
		 TpsFinServ=tpsF;
		 distance = dist;
	 }
	 
	 // dépot
	 public Activite (Tache dep, int itw, double[] heuredep){
		task=dep;
		IndexTW=itw;
		TWi= heuredep;
	 }
	 
	 //à voir
	 public Activite(Tache t){
		 task = t;
	 }
	 
	 public Activite clone(){
		 Activite retour;
		 if(TWi == null){
			 retour = new Activite(task, null, IndexTW, TpsArriv, TpsDebServ, TpsFinServ, distance);
		 }else{
			 retour = new Activite(task.clone(), TWi.clone(), IndexTW, TpsArriv, TpsDebServ, TpsFinServ, distance);
		 }
		 return retour;
	 }

	public void ToString(){
		 System.out.println("Task "+task.nom+" in its "+IndexTW+" TpsArr "+TpsArriv+" TpsDeb "+TpsDebServ+" TpsFin "+TpsFinServ+" dist: "+distance);
	 }

}
