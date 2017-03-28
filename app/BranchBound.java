package app;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author vklho impl�mente l'algorithme de Branch and Bound.
 */
class BranchBound {
	/**
	 * Point d'entr�e de l'application. On va conna�tre le nom du fichier par
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
	 * Variables n�cessaires � l'algorithme.
	 */
	ArrayList<Noeud> lesNoeudsATraiter = new ArrayList<>();
	int meilleureBorneSup = Integer.MAX_VALUE;
	Noeud meilleureSolution = null;
	Noeud noeudCourant;

	/**
	 * Trouve une solution � partir des donn�es fournies.
	 * 
	 * @param donnees
	 */
	
	void resoudre(Donnees donnees) {
		System.out.println("\n<< D�but >>\n");
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
	 * @return vrai si il y a quelque chose � prendre
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
	 * Ne fait rien � part donner des infos. Je trouve �a cool.
	 * 
	 * @param noeud
	 */
	private void elaguer(Noeud noeud) {
		System.out.print("    ce noeud � une borne inf�rieure de " + noeud.borneInferieure()
				+ " qui est sup�rieure � " + meilleureBorneSup + ", ");
		System.out.println("on ne traitera pas ses fils.");
	}

	/**
	 * Mets � jour la meilleure solution.
	 * 
	 * @param noeud
	 */
	private void meilleureSolutionDevient(Noeud noeud) {
		meilleureBorneSup = noeud.borneInferieure();
		meilleureSolution = noeud;
		System.out.println("[!] Solution possible trouv�e : " + meilleureSolution + " de borne "
				+ meilleureSolution.borneInferieure());
	}

	/**
	 * Cr�e des enfants, les �value et les ajoute � l'ensemble des noeuds �
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
			System.out.println("\nLa meilleure solution trouv�e est " + meilleureSolution.toString());
			System.out.println("    sa valeur est " + meilleureSolution.borneInferieure());
		} else {
			System.out.println("\nPas de solution trouv�e, revoir l'algo.");
		}
		System.out.println("\n<< Fin >>\n");
	}
}