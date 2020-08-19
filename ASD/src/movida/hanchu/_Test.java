package movida.hanchu;

import java.io.File;

import movida.commons.Collaboration;
import movida.commons.MapImplementation;
import movida.commons.Movie;
import movida.commons.Person;
import movida.commons.SortingAlgorithm;

public class _Test {
	public static void main(String[] args) throws MovidaCompareException {
		MovidaCore object = new MovidaCore();
		object.setMap(MapImplementation.ListaNonOrdinata);
		object.setSort(SortingAlgorithm.BubbleSort);
				
		File myObj = new File("esempio-formato-dati.txt");
		
		object.loadFromFile(myObj);
		
		for(Collaboration element : object.maximizeCollaborationsInTheTeamOf(object.getPersonByName("Harrison Ford"))) {
			System.out.println(element.getActorA().getName() + " " + element.getActorB().getName() + " " + element.getScore());
		}
		System.out.println("");
		/*
		object.deleteMovieByTitle("Cape Fear");
		for(Person element : object.getTeamOf(object.getPersonByName("Robert De Niro"))) {
			System.out.println(element.getName());
		}
		System.out.println("");
		*/
	}
}
