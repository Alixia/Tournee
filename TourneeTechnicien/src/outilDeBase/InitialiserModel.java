package outilDeBase;

import java.util.Vector;

public class InitialiserModel {

	public static Vector <Tache> tacheAFaire = new Vector<Tache>();
	public static Vector <Technicien> tech = new Vector<Technicien>();
	public static Vector <Tache> tacheFaite = new Vector<>();
	public static Vector <Vector<Integer>> K = new Vector<>();
	
	public static void initialiser(){
		tacheAFaire = new Vector<Tache>();
		tech = new Vector<Technicien>();
		tacheFaite = new Vector<>();
		K = new Vector<>();
		Intialisertache();
		InitialiserTechnician();
		initialiserSimilarite();
	}
	
	public static void mettreAJourSelonSolution(Solution s) {
		tacheAFaire = new Vector<Tache>();
		tacheFaite = new Vector<>();
		Intialisertache();
		int nbRoutes = 0;
		while(nbRoutes < s.sol.size()) {
			int nbActivite = 0;
			while(nbActivite < s.sol.get(nbRoutes).lesActivite.size()) {
				Activite a = s.sol.get(nbRoutes).lesActivite.get(nbActivite);
				if(!a.isPause()) {
					int remove = findTache(a.task.nom);
					add(tacheFaite, a.task);
					tacheAFaire.remove(remove);
				}
				nbActivite ++;
			}
			nbRoutes ++;
		}
	}
	
	private static int findTache(int nomTache) {
		int i=0;
		while(i<tacheAFaire.size() && nomTache != tacheAFaire.get(i).nom) {
			i++;
		}
		return i;
	}
	
	private static void Intialisertache (){
		// On remplie tableau des taches
		Vector<Tache> vecT= new Vector<Tache>();
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
			//par ordre de priorite
			add(tacheAFaire, (new Tache (i,tw, p, ReadData.pieceTab[i],ReadData.alpha[i],ReadData.sigma[i])));
		}
		
	}
	private static void InitialiserTechnician(){
		// On remplie tableau des techniciens 
		for (int k=0;k<ReadData.tech; k++){
			int [] inv = new int [ReadData.nbrPiece];
			for (int z=0; z<ReadData.nbrPiece;z++){
				inv[z]=ReadData.piecet[z][k];			
			}
			
			Technicien te= new Technicien (k, inv, ReadData.tache+k, ReadData.dep[k]);
			tech.add(te);
		}
	}
	
	public static void add(Vector<Tache> vt, Tache t){
		int i=0;
		while(i<vt.size() && vt.get(i).prio > t.prio){
			i++;
		}
		vt.add(i,t);
	}

	public static void afficher(){
		System.out.println("******************************");
		System.out.println("********** TECHNICIEN **********");
		for (Technicien tech2 : tech) {
			System.out.println(tech2.toString());
		}
		System.out.println("********** COMPETENCE **********");
		int i = 0;
		for (Vector<Integer> k : K) {
			System.out.print("tache "+ i + " " + k);
			System.out.println();
			i++;
		}
		System.out.println("********** TACHE A FAIRE ********");
		for (Tache tache2 : tacheAFaire) {
			System.out.println(tache2.toString());
		}
		System.out.println("********** TACHE FAITE ********");
		for (Tache tache2 : tacheFaite) {
			System.out.println(tache2.toString());
		}
		System.out.println("******************************");
	}
	
	private static void initialiserSimilarite() {
		//on rempli le tableau K (pour le calcul de la similarité)
		int[] nbTechParTach = new int[ReadData.tache];
		for(int i=0; i<ReadData.tache; i++){
			K.add(new Vector<>());
		}
		for (int i = 0; i < ReadData.competence.length; i++) {
			for (int j = 0; j < ReadData.competence[0].length; j++) {
				if(ReadData.competence[i][j] == 1) {
					K.get(j).add(i);
				}
			}
		}
		
	}
		
}
