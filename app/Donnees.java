package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author vklho
 * définissent et implémentent la structure de données contenant une instance du problème. 
 * La méthode readData permet de lire les fichiers de données au format présenté ci-dessous.
 */
class Donnees {
	final ArrayList<Integer> procTime;
	final ArrayList<Integer> penalty;
	final ArrayList<Integer> dueDate;
	int makespan;
	
	Donnees() {
		procTime = new ArrayList<>();
		penalty = new ArrayList<>();
		dueDate = new ArrayList<>();
	}
	
	Donnees(Donnees data) {
		procTime = new ArrayList<>(data.procTime);
		penalty = new ArrayList<>(data.penalty);
		dueDate = new ArrayList<>(data.dueDate);
		nbItems = data.nbItems;
		makespan = data.makespan;
	}
	
	boolean lire(String filename) {
		try {
			Scanner scanner = new Scanner(new File(filename));
			nbItems = scanner.nextInt();			
			makespan = 0;
			for (int i = 0; i != nbItems; ++i) {
				penalty.add(scanner.nextInt());
			}
			for (int i = 0; i != nbItems; ++i) {
				procTime.add(scanner.nextInt());
				makespan += procTime.get(i);
			}
			for (int i = 0; i != nbItems; ++i) {
				dueDate.add(scanner.nextInt());
			}	;
			scanner.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	
	@Override
	public String toString() {
		String toReturn = "\n	nbItems : " + nbItems + "\n";
		toReturn += "	Penalties " + penalty  + "\n";
		toReturn += "	ProcTimes " + procTime + "\n";
		toReturn += "	DueDates  " + dueDate + "\n";
		return toReturn;
	}
	
	int nbItems = 0;
}
