import outilDeBase.*;

public class Test {

	public static void main(String[] args){
		String chemin ="inst1/";
		ReadData D = new ReadData(chemin);
		D.lancerLecture();
		System.out.println("nbr Tache:"+D.tache+"; nbrTech:"+D.tech+"; nbrDepot:"+D.depot);
		
		InitialiserModel.initialiser();
		InitialiserModel.afficher();
		
		Solution s = SolutionGreedy.solutionInitiale();
		System.out.println(s.toString());
	}
	
}
