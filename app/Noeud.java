package app;

import java.util.ArrayList;

/**
 * @author vklho Définissent et implémentent un noeud de l'arbre de branchement.
 *         Ces fichiers doivent être complétés
 */
class Noeud {
	Donnees data;
	ArrayList<Integer> solution = new ArrayList<>();
	private boolean evaluated = false;

	static Noeud creerRacine(Donnees data) {
		return new Noeud(data);
	}

	private Noeud(Donnees data) {
		if (data.nbItems == 0) {
			throw new IllegalStateException("nb items == 0 have you parse file ?");
		}
		this.data = new Donnees(data);
	}

	Noeud(Noeud node) {
		this(node.data);
		solution = new ArrayList<>();
		solution.addAll(node.solution);
		bornInf = node.bornInf;
	}

	ArrayList<Noeud> creerEnfants() {
		ArrayList<Noeud> children = new ArrayList<>();
		for (int i = 0; i != data.nbItems; ++i) {
			if (!solution.contains(i)) {
				Noeud child = new Noeud(this);
				child.solution.add(0, i);
				children.add(child);
			}
		}
		return children;
	}

	void evaluer() {
		if (!evaluated) {
			int retard = (data.makespan - data.dueDate.get(solution.get(0))) * data.penalty.get(solution.get(0));
			bornInf += Math.max(retard, 0);
			data.makespan -= data.procTime.get(solution.get(0));
			evaluated = true;
		}
	}

	boolean estSolutionComplete() {
		return solution.size() == data.nbItems;
	}

	boolean dejaTraite() {
		return evaluated;
	}
	
	@Override
	public String toString() {
		String toReturn = "< ";
		for (int i = 0; i != data.nbItems - solution.size(); ++i) {
			toReturn += " . ";
		}
		for (int s : solution) {
			toReturn += s + " ";
		}
		toReturn += ">";
		return toReturn;
	}

	private int bornInf = 0;

	int borneInferieure() {
		return bornInf;
	}
}
