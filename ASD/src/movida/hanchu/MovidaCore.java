package movida.hanchu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import movida.commons.*;

public class MovidaCore implements IMovidaConfig, IMovidaDB, IMovidaSearch, IMovidaCollaborations {
	// una struttura dati per i film e una per le persone
	protected Dizionario<Movie> dizionarioFilm;
	protected Dizionario<Person> dizionarioAttori;
	protected Dizionario<Person> dizionarioRegisti;
	protected Ordinamento ordinamento;
	protected Grafo grafo;
	
	// il costruttore
	public MovidaCore() {
		this.dizionarioFilm = new ABR<Movie>();
		this.dizionarioAttori = new ABR<Person>();
		this.dizionarioRegisti = new ABR<Person>();
		this.ordinamento = new BubbleSort();
		this.grafo = new Grafo();
	}
	
	// IMovidaConfig INIZIO ----------------------------------------------------
	public boolean setMap(MapImplementation m) {
		if(m == MapImplementation.ABR) {
			this.dizionarioFilm = new ABR<Movie>();
			this.dizionarioAttori = new ABR<Person>();
			this.dizionarioRegisti = new ABR<Person>();
			return true;
		} else if(m == MapImplementation.ListaNonOrdinata) {
			this.dizionarioFilm = new ListaNonOrdinata<Movie>();
			this.dizionarioAttori = new ListaNonOrdinata<Person>();
			this.dizionarioRegisti = new ListaNonOrdinata<Person>();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean setSort(SortingAlgorithm a) {
		if(a == SortingAlgorithm.BubbleSort) {
			this.ordinamento = new BubbleSort();
			return true;
		} else if(a == SortingAlgorithm.MergeSort) {
			this.ordinamento = new MergeSort();
			return true;
		} else {
			return false;
		}
	}
	// IMovidaConfig FINE ------------------------------------------------------
	
	// IMovidaDB INIZIO --------------------------------------------------------
	public void loadFromFile(File f) throws MovidaFileException {
		try {
			Path path = f.toPath();
		    Charset charset = Charset.forName("ISO-8859-1");
			// converte il file in array di stringhe
			// ogni elemento di 'lines' è una riga del file
			List<String> lines = Files.readAllLines(path, charset);
			// itera finché non legge tutte le righe
			while(!lines.isEmpty()) {
				// copia le prime 5 righe
				String riga1 = lines.get(0);
				String riga2 = lines.get(1);
				String riga3 = lines.get(2);
				String riga4 = lines.get(3);
				String riga5 = lines.get(4);
				// controlla se le righe corrispondono ai dati di un film
				if(riga1.contains("Title: ") && riga2.contains("Year: ") && riga3.contains("Director: ") && riga4.contains("Cast: ") && riga5.contains("Votes: ")) {
					// cattura i dati significativi di ogni stringa
					riga1 = riga1.substring(7);
					riga2 = riga2.substring(6);
					riga3 = riga3.substring(10);
					riga4 = riga4.substring(6);
					riga5 = riga5.substring(7);
				} else {
					throw new MovidaFileException();
				}
				
				// copia i dati mel giusto formato per il film
				String title = riga1;
				int year = Integer.parseInt(riga2);
				Person director = new Person(riga3, 1);
				// per il cast, separa la lista degli attori in un array di stringhe
				String[] tmp = riga4.split(", ");
				Person[] cast = new Person[tmp.length];
				int index = 0;
				// assegna ad ogni elemento di Person un attore
				for(String name : tmp) {
					cast[index] = new Person(name, 1);
					index++;
				}
				int votes = Integer.parseInt(riga5);
				
				// crea l'oggetto movie con i dati raccolti
				Movie movie = new Movie(title, year, votes, cast, director);
				// controllo della sua presenza sul database del film
				if(dizionarioFilm.search("Title", movie) == null) {
					// se non è presente lo si inserisce
					dizionarioFilm.insert("Title", movie);
				} else {
					// se è presente lo si sovrascrive
					dizionarioFilm.delete("Title", movie);
					dizionarioFilm.insert("Title", movie);
				}
				// controllo della presenza sul database delle persone
				if(dizionarioRegisti.search("Name", director) == null) {
					dizionarioRegisti.insert("Name", director);
				} else {
					dizionarioRegisti.search("Name", director).addFilm();
				}
				for(Person persona : cast) {
					if(dizionarioAttori.search("Name", persona) == null) {
						dizionarioAttori.insert("Name", persona);
					} else {
						dizionarioAttori.search("Name", persona).addFilm();
					}
				}
				grafo.addMovieCollaboration(movie);
				
				// cancellazione delle righe usate
				for(index = 1; index < 6; index++) {
					lines.remove(0);
				}
				// rimuovere la riga vuota
				if(lines.get(0).equals("")) {
					lines.remove(0);
				}
			}
		}
		catch(Exception e) {
			e.getMessage();
		}
	}

	public void saveToFile(File f) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(f);
			LinkedList<Movie> lista = dizionarioFilm.convertToList();
			Movie[] array = new Movie[dizionarioFilm.getSize()];
			lista.toArray(array);
			
			// stampa sul file i dati
			for(Movie movie : array) {
				writer.println("Title: " + movie.getTitle());
				writer.println("Year: " + movie.getYear());
				writer.println("Director: " + movie.getDirector().getName());
				Person[] cast = movie.getCast();
				int numero = cast.length;
				writer.print("Cast: ");
				writer.print(cast[0].getName());
				for(int index = 1; index < numero; index++) {
					writer.print(", ");
					writer.print(cast[index].getName());
				}
				writer.println("");
				writer.println("Votes: " + movie.getVotes());
				writer.println("");
			}
			writer.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		// crea una nuova istanza delle classi
		this.dizionarioFilm = new ABR<Movie>();
		this.dizionarioAttori = new ABR<Person>();
		this.dizionarioRegisti = new ABR<Person>();
		this.grafo = new Grafo();
	}

	public int countMovies() {
		return this.dizionarioFilm.getSize();
	}

	public int countPeople() {
		return this.dizionarioAttori.getSize() + this.dizionarioRegisti.getSize();
	}

	public boolean deleteMovieByTitle(String title) throws MovidaCompareException {
		// converte 'dizionarioFilm' in un array
		LinkedList<Movie> lista = this.dizionarioFilm.convertToList();
		Movie[] array = new Movie[this.dizionarioFilm.getSize()];
		lista.toArray(array);
		boolean trovato = false;
		Movie film = null;
		for(Movie movie : array) {
			if(movie.getTitle().compareTo(title) == 0) {
				trovato = true;
				film = movie;
				break;
			}
		}
		if(trovato) {
			this.grafo.removeMovieCollaboration(film);
			this.dizionarioFilm.delete("Title", film);
		}
		return trovato;
	}

	public Movie getMovieByTitle(String title) {
		LinkedList<Movie> tmp = this.dizionarioFilm.convertToList();
		Movie[] lista = (Movie[]) tmp.toArray();
		for(Movie movie : lista) {
			if(movie.getTitle().compareTo(title) == 0) {
				return movie;
			}
		}
		return null;
	}

	public Person getPersonByName(String name) {
		LinkedList<Person> lista = this.dizionarioAttori.convertToList();
		Person[] array = new Person[this.dizionarioAttori.getSize()];
		lista.toArray(array);
		for(Person persona : array) {
			if(persona.getName().compareTo(name) == 0) {
				return persona;
			}
		}
		lista = this.dizionarioRegisti.convertToList();
		array = new Person[this.dizionarioRegisti.getSize()];
		lista.toArray(array);
		for(Person persona : array) {
			if(persona.getName().compareTo(name) == 0) {
				return persona;
			}
		}
		return null;
	}

	public Movie[] getAllMovies() {
		LinkedList<Movie> tmp = this.dizionarioFilm.convertToList();
		Movie[] array = new Movie[this.dizionarioFilm.getSize()];
		return tmp.toArray(array);
	}

	public Person[] getAllPeople() {
		Person[] array = new Person[this.dizionarioRegisti.getSize() + this.dizionarioAttori.getSize()];
		LinkedList<Person> listaRegisti = this.dizionarioRegisti.convertToList();
		LinkedList<Person> listaAttori = this.dizionarioAttori.convertToList();
		int index = 0;
		for(int i = 0; i < this.dizionarioRegisti.getSize(); i++) {
			array[index] = listaRegisti.get(i);
			index++;
		}
		for(int i = 0; i < this.dizionarioAttori.getSize(); i++) {
			array[index] = listaAttori.get(i);
			index++;
		}
		return array;
	}
	// IMovidaConfig FINE ------------------------------------------------------

	// IMovidaSearch INIZIO ----------------------------------------------------

	// !------------------------------------------------------------------------
	// prima ordina poi cerca
	// |------------------------------------------------------------------------
	public Movie[] searchMoviesByTitle(String title) {
		ArrayList<Movie> tmp = new ArrayList<Movie>();
		LinkedList<Movie> lista = new LinkedList<Movie>();
		lista = this.dizionarioFilm.convertToList();
		for(Movie element : lista) {
			if(element.getTitle().contains(title)) {
				tmp.add(element);
			}
		}
		Movie[] array = new Movie[tmp.size()];
		return tmp.toArray(array);
	}

	public Movie[] searchMoviesInYear(Integer year) {
		ArrayList<Movie> tmp = new ArrayList<Movie>();
		LinkedList<Movie> lista = new LinkedList<Movie>();
		lista = this.dizionarioFilm.convertToList();
		for(Movie element : lista) {
			if(element.getYear().equals(year)) {
				tmp.add(element);
			}
		}
		Movie[] array = new Movie[tmp.size()];
		return tmp.toArray(array);
	}

	public Movie[] searchMoviesDirectedBy(String name) {
		ArrayList<Movie> tmp = new ArrayList<Movie>();
		LinkedList<Movie> lista = new LinkedList<Movie>();
		lista = this.dizionarioFilm.convertToList();
		for(Movie element : lista) {
			if(element.getDirector().getName().equals(name)) {
				tmp.add(element);
			}
		}
		Movie[] array = new Movie[tmp.size()];
		return tmp.toArray(array);
	}

	public Movie[] searchMoviesStarredBy(String name) {
		ArrayList<Movie> tmp = new ArrayList<Movie>();
		LinkedList<Movie> lista = new LinkedList<Movie>();
		lista = this.dizionarioFilm.convertToList();
		for(Movie element : lista) {
			for(Person persona : element.getCast()) {
				if(persona.getName().equals(name) && tmp.contains(element) == false) {
					tmp.add(element);
				}
			}
		}
		Movie[] array = new Movie[tmp.size()];
		return tmp.toArray(array);
	}

	public Movie[] searchMostVotedMovies(Integer N) {
		LinkedList<Movie> lista = new LinkedList<Movie>(); 
		lista = this.dizionarioFilm.convertToList();
		Movie[] tmp = new Movie[lista.size()];
		lista.toArray(tmp);
		this.ordinamento.setField("Votes");
		this.ordinamento.setOrdinamento(false);
		tmp = ordinamento.ordina(tmp);
		if(N > tmp.length) {
			N = tmp.length;
		}
		Movie[] array = new Movie[N];
		for(int index = 0; index < N; index++) {
			array[index] = tmp[index];
		}
		return array;
	}

	public Movie[] searchMostRecentMovies(Integer N) {
		LinkedList<Movie> lista = new LinkedList<Movie>(); 
		lista = this.dizionarioFilm.convertToList();
		Movie[] tmp = new Movie[lista.size()];
		lista.toArray(tmp);
		this.ordinamento.setField("Year");
		this.ordinamento.setOrdinamento(false);
		tmp = ordinamento.ordina(tmp);
		if(N > tmp.length) {
			N = tmp.length;
		}
		Movie[] array = new Movie[N];
		for(int index = 0; index < N; index++) {
			array[index] = tmp[index];
		}
		return array;
	}

	public Person[] searchMostActiveActors(Integer N) {
		LinkedList<Person> lista = new LinkedList<Person>(); 
		lista = this.dizionarioAttori.convertToList();
		Person[] tmp = new Person[lista.size()];
		lista.toArray(tmp);
		this.ordinamento.setField("Activity");
		tmp = ordinamento.ordina(tmp);
		if(N > tmp.length) {
			N = tmp.length;
		}
		Person[] array = new Person[N];
		for(int index = 0; index < N; index++) {
			array[index] = tmp[index];
		}
		return array;
	}
	// IMovidaSearch FINE ------------------------------------------------------
	
	// IMovidaCollaborations INIZIO --------------------------------------------
	public Person[] getDirectCollaboratorsOf(Person actor) {
		return this.grafo.getDirect(actor);
	}

	public Person[] getTeamOf(Person actor) {
		return this.grafo.getIndirect(actor);
	}

	public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor) {
		return this.grafo.maximunSpanningTree(actor);
	}

}
