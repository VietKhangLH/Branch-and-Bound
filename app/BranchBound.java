package app;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author vklho implémente l'algorithme de Branch and Bound.
 */
class BranchBound {
	/**
	 * Point d'entrée de l'application. On va connaître le nom du fichier par
	 * ligne de commande.
	 * 
	 * @param args
	 */
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String... args) {
		Donnees donnees = new Donnees();
		do {
			System.out.print("Fichier : ");
		} while (!donnees.lire(scan.nextLine()));
		new BranchBound().resoudre(donnees);
		
		BranchBound.main(args);
	}

	/**
	 * Variables nécessaires à l'algorithme.
	 */
	ArrayList<Noeud> lesNoeudsATraiter = new ArrayList<>();
	int meilleureBorneSup = Integer.MAX_VALUE;
	Noeud meilleureSolution = null;
	Noeud noeudCourant;

	/**
	 * Trouve une solution à partir des données fournies.
	 * 
	 * @param donnees
	 */
	
	void resoudre(Donnees donnees) {
		System.out.println("\n<< Début >>\n");
		lesNoeudsATraiter.add(Noeud.creerRacine(donnees));
		while (prendreParmi(lesNoeudsATraiter)) {
			if (noeudCourant.estSolutionComplete()) {
				if (meilleureSolution == null
						|| meilleureSolution.borneInferieure() < noeudCourant.borneInferieure()) {
					meilleureSolutionDevient(noeudCourant);
				}
			} else if (noeudCourant.borneInferieure() < meilleureBorneSup) {
				traiterEnfants(noeudCourant);
			} else {
				elaguer(noeudCourant);
			}
			lesNoeudsATraiter.remove(noeudCourant);
		}
		afficherMeilleureSolution();
	}

	/**
	 * @param noeudsATraiter
	 * @return vrai si il y a quelque chose à prendre
	 */
	private boolean prendreParmi(ArrayList<Noeud> noeudsATraiter) {
		if (noeudsATraiter.isEmpty()) {
			return false;
		}
		Noeud meilleurNoeudATraiter = plusPetiteBorneParmi(noeudsATraiter);
		noeudCourant = meilleurNoeudATraiter;
		noeudsATraiter.remove(meilleurNoeudATraiter);
		System.out.println("On prend " + meilleurNoeudATraiter + " => " + meilleurNoeudATraiter.borneInferieure());
		return true;
	}

	/**
	 * @param noeuds
	 * @return le noeud dont la borne est la plus petite parmi un ensemble
	 */
	private Noeud plusPetiteBorneParmi(ArrayList<Noeud> noeuds) {
		Noeud plusPetiteBorne = noeuds.get(0); // par defaut
		for (Noeud noeud : noeuds) {
			if (noeud.borneInferieure() <= plusPetiteBorne.borneInferieure()) {
				plusPetiteBorne = noeud;
			}
		}
		return plusPetiteBorne;
	}

	/**
	 * Ne fait rien à part donner des infos. Je trouve ça cool.
	 * 
	 * @param noeud
	 */
	private void elaguer(Noeud noeud) {
		System.out.print("    ce noeud à une borne inférieure de " + noeud.borneInferieure()
				+ " qui est supérieure à " + meilleureBorneSup + ", ");
		System.out.println("on ne traitera pas ses fils.");
	}

	/**
	 * Mets à jour la meilleure solution.
	 * 
	 * @param noeud
	 */
	private void meilleureSolutionDevient(Noeud noeud) {
		meilleureBorneSup = noeud.borneInferieure();
		meilleureSolution = noeud;
		System.out.println("[!] Solution possible trouvée : " + meilleureSolution + " de borne "
				+ meilleureSolution.borneInferieure());
	}

	/**
	 * Crée des enfants, les évalue et les ajoute à l'ensemble des noeuds à
	 * traiter.
	 * 
	 * @param noeud
	 */
	private void traiterEnfants(Noeud noeud) {
		ArrayList<Noeud> enfants = noeud.creerEnfants();
		for (Noeud enfant : enfants) {
			enfant.evaluer();
			System.out.println("    " + enfant.toString() + " => " + enfant.borneInferieure());
		}
		lesNoeudsATraiter.addAll(enfants);
	}

	/**
	 * Affichage des meilleures solutions ou pas
	 */
	private void afficherMeilleureSolution() {
		if (meilleureSolution != null) {
			System.out.println("\nLa meilleure solution trouvée est " + meilleureSolution.toString());
			System.out.println("    sa valeur est " + meilleureSolution.borneInferieure());
		} else {
			System.out.println("\nPas de solution trouvée, revoir l'algo.");
		}
		System.out.println("\n<< Fin >>\n");
	}
}