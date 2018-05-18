package outilDeBase;



import java.io.*;

public class ReadData {


		public static String dataPath;//"/home/ines/workspace/GenerationDeColonne/Data/"
		public static String instance;

		public static int tache;
		public static int tech;
		public static int depot;
		public static int nbrTw;
		public static int nbrPiece;
		public static  int dmax=125;
		public static  double psi=5; // overTime // article 1 et 2 10 
		public static  double phi=550;  // nombre tache 550
		public static  double mu=5; // distance // article 1 et 2 5
		public static int TpsReap=25;	//temps reaprovisionnement
		public static 	double [][]  distance;
		public static	double [][]  distanced;
		public static	double [][]  Temps;
		public static	double [][]  Tempsd;
		public static	int [] []  piece; // piece necessaire pour chaque tache
		public static	int [] []  piecet; // piece disponible chez le technicien 
		public static 	int [] []  competence;
		public static	int [] [] fenetre;
		public 	static int [] dep;
		public 	static int []  sigma;// temps de reparation
		public static	int []  alpha;//penalitÃ© / priorité
		public static	int []   pieceTab; // piece Tabelette
	public ReadData( String data){
		instance = "";
		dataPath= data;
	}
		
		public double [][] lireMatriceTodouble(String fichier, int t1, int t2){	
			double [][] mat=new double [t1][t2];
			//lecture du fichier texte	
			try{
				InputStream ips=new FileInputStream(fichier); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;
				int j=0;
				while ((ligne=br.readLine())!=null){
							
					String[] result =ligne.split("\t");
					for (int i=0;i<result.length;i++){
						mat[j][i]=Float.parseFloat(result[i]);
						}
					j++;
					}
				
				br.close(); 
			}		
			catch (Exception e){
				System.out.println(e.toString());
			}
			return mat;
			}
		public int [][] lireMatriceToInt(String fichier, int t1, int t2){	
		int [][] mat=new int [t1][t2];
		//lecture du fichier texte	
	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			int j=0;
		
			while ((ligne=br.readLine())!=null){
				
				String[] result =ligne.split("\t");
				
				for (int i=0;i<t2;i++){
					mat[j][i]=Integer.parseInt(result[i]);
				}
				j++;
				}
			
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		return mat;
		}
		public int [][] lireMatriceTW(String fichier, int t1, int t2){	
			int [][] mat=new int [t1][t2];
			//lecture du fichier texte	
			try{
				InputStream ips=new FileInputStream(fichier); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;
				int j=0;
				while ((ligne=br.readLine())!=null){
					String[] result =ligne.split("\t");
					
					
					for (int i=0;i<result.length;i++){
						Heure h = new Heure (result[i]); 
						mat[j][i]=h.convertirenMinute();

						
					}
					j++;
					}
				
				br.close(); 
			}		
			catch (Exception e){
				System.out.println(e.toString());
			}
			return mat;
			}
		public int [] liretableau(String fichier, int t1){	
			int [] tab=new int [t1];
			//lecture du fichier texte	
			try{
				InputStream ips=new FileInputStream(fichier); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;
				while ((ligne=br.readLine())!=null){
					String[] result =ligne.split("\t");
					for (int i=0;i<result.length;i++){
						tab[i]=Integer.parseInt(result[i]);
					}
					}
				
				br.close(); 
			}		
			catch (Exception e){
				System.out.println(e.toString());
			}
			return tab;
			}
		public void DonneeInit(String fichier){	
			//lecture du fichier texte	
			try{
				InputStream ips=new FileInputStream(fichier); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;
				int j=0;
				while (j<6){
					ligne=br.readLine();
					String[] result =ligne.split("\t");
					switch (j){ 
						case 0:
							tech=Integer.parseInt(result[0]);
							break;
						case 1:
							tache=Integer.parseInt(result[0]);
							break;
						case 2:
							depot=Integer.parseInt(result[0]);

							break;
						case 3:
							nbrPiece=Integer.parseInt(result[0]);
							break;
						case 4:
							nbrTw=Integer.parseInt(result[0]);
							break;						
						case 5:
							dmax = Integer.parseInt(result[0]);
							break;
							
					}
					j++;
					
				}

				br.close(); 
			}		
			catch (Exception e){
				System.out.println(e.toString());
			}
			}
		public void affichageMatrice (double [][] mat) {
			for (int i=0; i<mat.length;i++){
				for(int j=0;j<mat[i].length;j++){
					System.out.print(mat[i][j]+" , ");
				}
				System.out.println();
			}
		}
		public void lancerLecture(){
			  DonneeInit(dataPath+instance+"data.txt");
			  distance=new double [tache+tech][tache+tech];
			  distance=lireMatriceTodouble( dataPath+instance+"DistanceTaches.txt", (tache+tech),(tache+tech));
			

			  distanced=new double [depot][tache+tech];
			  distanced=lireMatriceTodouble( dataPath+instance+"DistanceDepotTaches.txt", depot,(tache+tech));
			

			  Temps=new double [tache+tech][tache+tech];
			  Temps=lireMatriceTodouble( dataPath+instance+"TempsTaches.txt", (tache+tech),(tache+tech));
			 

			  Tempsd=new double [depot][tache+tech];
			  Tempsd=lireMatriceTodouble( dataPath+instance+"TempsDepotTaches.txt", depot,(tache+tech));
			 

			  piece= new int[nbrPiece][tache];
			  piece=lireMatriceToInt( dataPath+instance+"PieceNecessaire.txt", nbrPiece,tache);
			 

			  piecet= new int[nbrPiece][tech];
			  piecet=lireMatriceToInt( dataPath+instance+"Inventaire.txt", nbrPiece,tech);
			 

			  competence= new int[tech][tache];
			  competence=lireMatriceToInt( dataPath+instance+"Competence.txt", tech,tache);
		

			  fenetre= new int[(tache*nbrTw)][2];
			  fenetre=lireMatriceTW( dataPath+instance+"fenetresdeTemps.txt", (tache*nbrTw),2);
				

			  pieceTab= new int[tache];
			  pieceTab=liretableau( dataPath+instance+"PieceTab.txt", tache);
				

			  sigma= new int[tache];
			  sigma=liretableau( dataPath+instance+"TempsdeReparation.txt", tache);
			  alpha= new int[tache];
			  alpha=liretableau( dataPath+instance+"Penalite.txt", tache);
			  dep= new int[tech];
			  dep=liretableau( dataPath+instance+"DepotdesTechniciens.txt", tech);
			
		}		

	}
