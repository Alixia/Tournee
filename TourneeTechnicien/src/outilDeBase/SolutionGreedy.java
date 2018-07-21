package outilDeBase;

import java.util.Vector;

public class SolutionGreedy {
	
	
	public static Solution solutionInitiale(){
		Solution solution = new Solution();
		int i=0;
		while(i<InitialiserModel.tacheAFaire.size()){
			Tache tache = InitialiserModel.tacheAFaire.get(i);
			Vector<Solution> FeasRoute= new Vector<Solution>();
			for (int k=0; k<solution.sol.size();k++){
				if(ReadData.competence[solution.sol.get(k).tech.nom][tache.nom]==1){
					Route r = solution.sol.get(k);
					Vector <Route> routess = GetPossibleInsertion(tache, r);
					Vector <Solution> routess2 = creerSolutions(solution, routess, k, 1);
					Solution.add1(FeasRoute,routess2);
				}
			}
			
			if(!FeasRoute.isEmpty()){
				InitialiserModel.tacheFaite.add(tache);
				InitialiserModel.tacheAFaire.remove(i);
				solution = FeasRoute.get(0);
				i--;
			}
			i++;
			solution.Calcul_costsol();
		}
		
		
		CalculerLesCouts(solution);

		return solution;
	}
	
	public static Vector<Solution> creerSolutions(Solution sol, Vector<Route> routess, int k, int nbAjout) {
		
		Vector<Solution> solutions = new Vector<>();
		int i = 0;
		while(i < routess.size() && i < nbAjout) {
			Solution soltmp = sol.clone();
			soltmp.sol.remove(k);
			soltmp.sol.add(k,routess.get(i));
			Solution solTmp2 = soltmp.clone();
			CalculerLesCouts(solTmp2);
			soltmp.costsol = solTmp2.costsol;
			solutions.add(soltmp);
			i++;
		}
		
		return solutions;
	}
	
	public static void CalculerLesCouts(Solution solution){
		for (int j=0; j<solution.sol.size();j++){
			if (solution.sol.get(j).lesActivite.size()<6){
				solution.sol.remove(j); // dans cette route le technicien ne fait aucune acctivité
			}

		}
		solution.Calcul_costsol(); // On calcule les couts de chaque solution
	}

	public static void CalculerLesCouts(Vector<Solution> TTeLesSolutions){
		for (int i=0;i<TTeLesSolutions.size();i++){
			for (int j=0; j<TTeLesSolutions.get(i).sol.size();j++){
				if (TTeLesSolutions.get(i).sol.get(j).lesActivite.size()<6){
					TTeLesSolutions.get(i).sol.remove(j); // dans cette route le technicien ne fait aucune acctivité
				}
			}
			TTeLesSolutions.get(i).Calcul_costsol(); // On calcule les couts de chaque solution
		}
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
				Route.add(PossibleInsertion, NewR.get(index));
			}


		}		
		return PossibleInsertion;
	}

	public static Vector <Route> evaluerInsertion (Route r, Tache t, int pos){
		Vector <Route> MesRoutes = new Vector<Route>();
		// retourne soit null soit une Route en inserant la tache entre pos et pos+1

		Route NewR= r.clone();

		boolean passageDepot= false;

		NewR= Route.InsererLaTache(NewR,t, pos+1);
		NewR= Route.MAJInven(NewR);
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
				int index= Route.VerifTW(NewR, t, pos);
				if (index != -1){// on a une fenetre de temps qu'on peut utiliser
					NewR.lesActivite.get(pos+1).IndexTW= index;
					NewR.lesActivite.get(pos+1).TWi=t.TW[index];
					boolean Continue = Route.VerifierTps(NewR);				
					if (Continue){
						// on MAJ les distance
						Continue = Route.VerifierDistance(NewR);
						if (Continue){
							//System.out.println(" distance ");
							if (NewR.lesActivite.get(NewR.lesActivite.size()-1).distance>ReadData.dmax) Continue= false;
							// si la distance ne depasse pas la distance max on Calcule le cout
							NewR=Route.MAJInven(NewR);
							if (Continue && NewR!= null){
								//System.out.println("update Inventory");
								NewR.gain=Route.Calcul_gain(NewR);
								NewR.cost=Route.CalculCoutRoute(NewR);

								MesRoutes.add(NewR);

							}

						}

					}

				}

			}

		}


		// l'inventaire ne suffit pas il faut verifier le passage au depot
		if ((NewR==null  && r.passageDepot<0)||( r.passageDepot<0 && passageDepot)){// probleme d'inventaire et on a pas dans la Route un passage au depot
			Route newRd= r.clone();
			newRd= Route.InsererLaTache(newRd,t, pos+1);
			int index= Route.VerifTW(newRd, t, pos);
			if (index != -1){// on a une fenetre de temps qu'on peut utiliser
				newRd.lesActivite.get(pos+1).IndexTW= index;
				newRd.lesActivite.get(pos+1).TWi=t.TW[index];
				boolean Continue = Route.VerifierTps(newRd);				
				if (Continue){
					// on MAJ les distance
					Continue = Route.VerifierDistance(newRd);
					newRd.gain= Route.Calcul_gain(newRd);
					newRd.cost=Route.CalculCoutRoute(newRd);
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
				newRd= Route.InsererLaTache(newRd,t, pos+1);
				int index= Route.VerifTW(newRd, t, pos);
				if (index != -1){// on a une fenetre de temps qu'on peut utiliser
					newRd.lesActivite.get(pos+1).IndexTW= index;
					newRd.lesActivite.get(pos+1).TWi=t.TW[index];
					boolean Continue = Route.VerifierTps(newRd);				
					if (Continue){
						// on MAJ les distance
						Continue = Route.VerifierDistance(newRd);
						newRd.gain= Route.Calcul_gain(newRd);
						newRd.cost=Route.CalculCoutRoute(newRd);
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
		Route.insererDepot(r1,i);

		boolean Continue =Route.VerifierTps(r1);
		if (Continue){
			// on MAJ les distance
			Continue = Route.VerifierDistance(r1);

			if (Continue){	
				// si la distance ne depasse pas la distance max on Calcule le cout
				r1=Route.MAJInven(r1);

				if (r1!=null){
					r1.gain= Route.Calcul_gain(r1);
					r1.cost=Route.CalculCoutRoute(r1);

				}


			}
		}
		if (!Continue) return null;
		return r1;
	}

	public static Route deplacerdepot(Route rou, int pos){
		Route r = rou.clone();
		r= Route.removeDepot(r, r.passageDepot);
		Vector<Route> vecR= GenererRouteSDepot(r, pos-1);
		if (vecR.isEmpty()) return null;
		else{
			vecR= Route.Triroute(vecR);
			return vecR.get(0);
		}

	}

}
