package outilDeBase;

import java.util.Vector;

import outilGeneral.GestionTableau;

public class InitialiserModel {

	public static Vector <Tache> tacheAFaire = new Vector<>();
	public static Vector <Technicien> tech = new Vector<Technicien>();
	public static Vector <Tache> tacheFaite = new Vector<>();
	public static Vector <Vector<Integer>> K = new Vector<>();
	
	//implementation du greedy2 (prise en compte des compétences des techniciens)
	public static int[] nombreTechnParTache = new int[ReadData.tache];
	
	public static void initialiser(){
		tacheAFaire = new Vector<Tache>();
		tech = new Vector<Technicien>();
		tacheFaite = new Vector<>();
		K = new Vector<>();
		intialisertache();
		initialiserTechnician();
		initialiserSimilarite();
		initialiserGreedy2();
	}
	
	private static void initialiserGreedy2() {

		for(int tache=0 ; tache<ReadData.tache ; tache++) {
			int nb = 0;
			for(int tech=0 ; tech<ReadData.tech ; tech++) {
				if(ReadData.competence[tech][tache] != 0){
					nb++;
				}
			}
			nombreTechnParTache[tache] = nb;
		}
		
	}

	public static void mettreAJourSelonSolution(Solution s) {
		tacheAFaire = new Vector<>();
		tacheFaite = new Vector<>();
		intialisertache();
		int nbRoutes = 0;
		while(nbRoutes < s.sol.size()) {
			int nbActivite = 0;
			while(nbActivite < s.sol.get(nbRoutes).lesActivite.size()) {
				Activite a = s.sol.get(nbRoutes).lesActivite.get(nbActivite);
				if(!a.isPause()) {
					GestionTableau.removeNom(tacheAFaire, a.task.nom);
					add(tacheFaite, a.task);
				}
				nbActivite ++;
			}
			nbRoutes ++;
		}
	}
	
	public static int findTache(int nomTache) {
		int i=0;
		while(i<tacheAFaire.size() && nomTache != tacheAFaire.get(i).nom) {
			i++;
		}
		return i;
	}
	
	public static void intialisertache (){
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
			//par ordre de priorite
			add(tacheAFaire, (new Tache (i,tw, p, ReadData.pieceTab[i],ReadData.alpha[i],ReadData.sigma[i])));
		}
		
	}
	private static void initialiserTechnician(){
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
	
	//par ordre de priorité
	public static void add(Vector<Tache> vt, Tache t){
		int i=0;
		while(i<vt.size() && vt.get(i).prio > t.prio){
			i++;
		}
		vt.add(i,t);
	}
	
	//par ordre de competence
	public static void addCompetence(Vector<Tache> vt, Tache t){
		int i=0;
		while(i<vt.size() && nombreTechnParTache[vt.get(i).nom] < nombreTechnParTache[t.nom]){
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
