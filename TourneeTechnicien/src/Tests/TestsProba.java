package Tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import algoGrandVoisinage.GrandVoisinage;
import outilDeBase.Solution;
import outilDeBase.SolutionGreedy;

public class TestsProba {
	
	public static float[] listeTemps;
	public static double[] listeCout;
	public static FileWriter fw;
	
	public static void main(String[] args) throws IOException{
		File f = new File("sortieProbaWide.txt");
		fw = new FileWriter (f);
		for(int nbTaille=10 ; nbTaille<30 ; nbTaille += 5) {
			for(int numInstance=1; numInstance<6; numInstance++) {
				lancement("Narrow", nbTaille, numInstance);
			}
		}
		fw.close();
		
		System.out.println("Narrow fini");
		
		f = new File("sortieProbaNarrow.txt");
		fw = new FileWriter (f);
		for(int nbTaille=10 ; nbTaille<30 ; nbTaille += 5) {
			for(int numInstance=1; numInstance<6; numInstance++) {
				lancement("Wide", nbTaille, numInstance);
			}
		}
		fw.close();
	}

	private static void lancement(String largeur, int nbTaille, int numInstance) throws IOException {
		String chemin =largeur+"/40-40/40-40-"+nbTaille+"/inst"+numInstance+"/";
		TestInit.initialiser(chemin);
		
		fw.write(chemin+"\n");
		System.out.println(chemin);
		
		for(double prob = 0; prob <= 1; prob+=0.1) {
			TestInit.probaRandom = prob;
			float tempsMoyen=0;
			float tempsMedian=0;
			double coutMoyen=0;
			double coutMedian=0;
			listeTemps = new float[10];
			listeCout = new double[10];
			for(int i = 0; i < 10; i++) {
				TestInit.initialiser(chemin);
				TestInit.tempsDeb = System.currentTimeMillis();
				Solution s = SolutionGreedy.solutionInitiale();
				GrandVoisinage gv = new GrandVoisinage(s, TestInit.r, TestInit.nbConstr, TestInit.nbDestr);
				Solution s2 = gv.lancer();
				float tempsEnCours = ((float)(System.currentTimeMillis()-TestInit.tempsDeb)/1000f);
				tempsMoyen+=tempsEnCours/10;
				coutMoyen+=s2.costsol/10;
				add(tempsEnCours);
				addbis(s2.costsol);
			}
			tempsMedian = listeTemps[4];
			coutMedian = listeCout[4];
			String ecrire = "\t" + tempsMoyen + "s" + "\t" + tempsMedian + "s" + "\t" + coutMoyen + "\t" + coutMedian + "\t" + "\n";
			//System.out.print(ecrire);
			fw.write(ecrire);
		}
		
	}

	private static void addbis(double costsol) {
		int i = 0;
		while(listeCout[i]!=0 && listeCout[i]<costsol){
			i++;
		}
		if(listeCout[i]==0) {
			listeCout[i] = costsol;
		}else{
			double costsolbis = costsol;
			while(i<listeCout.length && listeCout[i]!=0) {
				double tmp = listeCout[i];
				listeCout[i] = costsolbis;
				costsolbis = tmp;
				i++;
			}
			listeCout[i] = costsolbis;
		}
		
	}

	private static void add(float tempsEnCours) {
		int i = 0;
		while(listeTemps[i]!=0 && listeTemps[i]<tempsEnCours){
			i++;
		}
		if(listeTemps[i]==0) {
			listeTemps[i] = tempsEnCours;
		}else{
			float tempsEnCoursbis = tempsEnCours;
			while(i<listeTemps.length && listeTemps[i]!=0) {
				float tmp = listeTemps[i];
				listeTemps[i] = tempsEnCoursbis;
				tempsEnCoursbis = tmp;
				i++;
			}
			listeTemps[i] = tempsEnCoursbis;
		}
		
	}
}
