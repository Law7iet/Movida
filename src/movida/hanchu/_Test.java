package movida.hanchu;

import java.io.File;
import movida.commons.Collaboration;
import movida.commons.MapImplementation;
import movida.commons.Movie;
import movida.commons.Person;
import movida.commons.SortingAlgorithm;

public class _Test {
	public static void main(String[] args) throws MovidaCompareException {
		System.out.println("Test MovidaCore.");
		
		// creazione di un'istanza di MovidaCore
		// di default il dizionario è un ABR
		// di default l'algoritmo di ordinamento è il BubbleSort
		MovidaCore object = new MovidaCore();
		
		// test di IMovidaConfig Inizio -----------------------------------------
		
		// test di setMap
//		object.setMap(MapImplementation.ABR);
//		object.setMap(MapImplementation.ListaNonOrdinata);
		
		// test di setSort
//		object.setSort(SortingAlgorithm.BubbleSort);
//		object.setSort(SortingAlgorithm.MergeSort);
		
		// test di IMovidaConfig Fine -------------------------------------------
		
		
		// test di IMovidaDB Inizio ---------------------------------------------
		
		// test di loadFromFile
//		File file1 = new File("esempio-formato-dati.txt");
//		object.loadFromFile(file1);
		
//		File file2 = new File("test-file.txt");
//		object.loadFromFile(file2);
		
		// test di saveToFile
//		File tmp = new File("saveToFile.txt");
//		object.saveToFile(tmp);
		
		// test di clear
//		object.clear();
		
		// test di countMovies
//		System.out.println(object.countMovies());
		
		// test di countPeople
//		System.out.println(object.countPeople());
		
		// test di deleteMovieByTitle
//		object.deleteMovieByTitle("Cape Fear");
//		System.out.println(object.countMovies());
		
		// test di getMovieByTitle
//		object.getMovieByTitle("Scarface");
		
		// test di getPersonByName
//		System.out.println(object.getPersonByName("Andrew Davis").getFilm());
		
		// test di getAllMovies
//		for(Movie movie : object.getAllMovies()) {
//			System.out.println(movie.getTitle());
//		}
		
		// test di getAllPeople
//		for(Person person : object.getAllPeople()) {
//			System.out.println(person.getName());
//		}

		// test di IMovidaDB Fine -----------------------------------------------

		
		// test di IMovidaSearch Inizio -----------------------------------------
		
		// test di searchMoviesByTitle
//		for(Movie movie : object.searchMoviesByTitle(" ")) {
//			System.out.println(movie.getTitle());
//		}
		
		// test di searchMoviesInYear
//		for(Movie movie : object.searchMoviesInYear(1997)) {
//			System.out.println(movie.getTitle() + ", " + movie.getYear());
//		}
		
		// test di searchMoviesDirectedBy
//		for(Movie movie : object.searchMoviesDirectedBy("Martin Scorsese")) {
//			System.out.println(movie.getTitle());
//		}
		
		// test di searchMoviesStarredBy
//		for(Movie movie : object.searchMoviesStarredBy("Harrison Ford")) {
//			System.out.println(movie.getTitle());
//		}
		
		// test di searchMostVotedMovies
//		for(Movie movie : object.searchMostVotedMovies(5)) {
//			System.out.println(movie.getTitle() + " " + movie.getVotes());
//		}		
		
		// test di searchMostRecentMovies
//		for(Movie movie : object.searchMostRecentMovies(5)) {
//			System.out.println(movie.getTitle() + " " + movie.getYear());
//		}
		
		// test di searchMostActiveActors
//		for(Person person : object.searchMostActiveActors(10)) {
//			System.out.println(person.getName() + " " + person.getFilm());
//		}
		
		// test di IMovidaSearch Fine ----------------------------------------------
		
		
		// test di IMovidaCollaboration Inizio -------------------------------------
		
		// test di getDirectCollaboratorsOf
//		Person persona = object.getPersonByName("Nick Nolte");
//		Person[] tmp = object.getDirectCollaboratorsOf(persona);
//		if(tmp != null) {
//			for(Person person : tmp) {
//				System.out.println(person.getName());
//			}
//		}
		
		// test di getTeamOf
//		Person persona = object.getPersonByName("Tom Skerritt");
//		Person[] tmp = object.getTeamOf(persona);
//		if(tmp != null) {
//			for(Person person : tmp) {
//				System.out.println(person.getName());
//			}
//		}
		
		// test di maximizeCollaborationInTheTeamOf
//		Person persona = object.getPersonByName("Bonnie Bedelia");
//		Collaboration[] tmp = object.maximizeCollaborationsInTheTeamOf(persona);
//		for(Collaboration collaboration : tmp) {
//			System.out.println(collaboration.getActorA().getName() + " - " + collaboration.getActorB().getName() + " - Score: " + collaboration.getScore());
//		}
		
		// test di IMovidaCollaboration Fine --------------------------------------
		
		System.out.println("Fine test MovidaCore.");
	}
}
