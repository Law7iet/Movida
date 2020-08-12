package movida.hanchu;

import java.io.File;

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
		
		Movie[] tmp = object.searchMostVotedMovies(10);
		
		for(Movie element : tmp) {
			System.out.println(element.getTitle());
		}
	}
}
