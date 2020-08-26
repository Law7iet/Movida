package movida.hanchu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import movida.commons.*;

public class MovidaCore implements IMovidaConfig, IMovidaDB, IMovidaSearch, IMovidaCollaborations {
	
	// strutture dati per i film, gli attori ed i registi
	protected Dizionario<Movie> dizionarioFilm;
	protected Dizionario<Person> dizionarioAttori;
	protected Dizionario<Person> dizionarioRegisti;
	// algoritmo di ordinamento
	protected Ordinamento ordinamento;
	// grafo per le collaborazioni
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
		} else {
			if(m == MapImplementation.ListaNonOrdinata) {
				this.dizionarioFilm = new ListaNonOrdinata<Movie>();
				this.dizionarioAttori = new ListaNonOrdinata<Person>();
				this.dizionarioRegisti = new ListaNonOrdinata<Person>();
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean setSort(SortingAlgorithm a) {
		if(a == SortingAlgorithm.BubbleSort) {
			this.ordinamento = new BubbleSort();
			return true;
		} else {
			if(a == SortingAlgorithm.MergeSort) {
				this.ordinamento = new MergeSort();
				return true;
			} else {
				return false;
			}
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
				String riga0 = lines.get(0);
				String riga1 = lines.get(1);
				String riga2 = lines.get(2);
				String riga3 = lines.get(3);
				String riga4 = lines.get(4);
				// controlla se le righe corrispondono ai dati di un film
				if(riga0.contains("Title: ") && riga1.contains("Year: ") && riga2.contains("Director: ") && riga3.contains("Cast: ") && riga4.contains("Votes: ")) {
					// cattura i dati significativi di ogni stringa
					riga0 = riga0.substring(7);
					riga1 = riga1.substring(6);
					riga2 = riga2.substring(10);
					riga3 = riga3.substring(6);
					riga4 = riga4.substring(7);
				} else {
					// il file in input usa una sintassi non corretta
					throw new MovidaFileException();
				}
				
				// copia i dati mel giusto tipo adatto per l'oggetto movie
				if (riga0.isEmpty() == true || riga0.startsWith(" ") == true) {
					// il titolo è errato
					throw new MovidaFileException();
				}
				String title = riga0;
				int year = Integer.parseInt(riga1);
				if (riga2.startsWith(" ") == true || riga2.isEmpty() == true) {
					// il regista è errato
					throw new MovidaFileException();
				}
				Person director = new Person(riga2, 1);
				String[] tmp = riga3.split(", ");
				Person[] cast = new Person[tmp.length];
				int index = 0;
				for(String name : tmp) {
					if(name.isEmpty() == true || name.startsWith(" ")) {
						// l'attore è errato
						throw new MovidaFileException();
					}
					cast[index] = new Person(name, 1);
					index++;
				}
				int votes = Integer.parseInt(riga4);
				
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
				// controllo della presenza sul database dei registi
				if(dizionarioRegisti.search("Name", director) == null) {
					// se non è presente lo si inserisce
					dizionarioRegisti.insert("Name", director);
				} else {
					// se è presente si segna che ha partecipato ad un film, aggiungendo +1 a 'film'
					dizionarioRegisti.search("Name", director).addFilm();
				}
				for(Person persona : cast) {
					// controlla per ogni attore del cast la presenza nel database degli attori
					if(dizionarioAttori.search("Name", persona) == null) {
						// se non è presente lo si aggiunge
						dizionarioAttori.insert("Name", persona);
					} else {
						// se è presente si segna che ha partecipato ad un film, aggiungendo +1 a 'film'
						dizionarioAttori.search("Name", persona).addFilm();
					}
				}
				// si aggiunge al grrafo le nuove collaborazioni
				grafo.addMovieCollaboration(movie);
				
				// cancellazione delle righe usate
				for(index = 1; index < 6; index++) {
					lines.remove(0);
				}
				// rimuovere la riga vuota
				// non è presente nell'ultimo film
				if(!lines.isEmpty()) {
					lines.remove(0);
				}
			}
		}
		catch (IOException e) {
			System.out.println("Errore. File " + e.getMessage() + " inesistente nella direcotry.");
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("Errore. Dati del film insufficienti.");
		}
		catch (NumberFormatException e) {
			System.out.println("Errore. Dati del film insufficienti.");
		}
		catch (MovidaCompareException e) {
			System.out.println(e.getMessage());
		}
		catch(MovidaFileException e) {
			System.out.println(e.getMessage());
		}
	}

	public void saveToFile(File f) {
		try {
			// si accede in scrittura al file 'f' con writer
			PrintWriter writer;
			writer = new PrintWriter(f);
			
			// si converte i film in un array
			LinkedList<Movie> lista = dizionarioFilm.convertToList();
			Movie[] array = lista.toArray(new Movie[dizionarioFilm.getSize()]);

			// stampa sul file i dati di ogni film
			// il risultato ha la stessa sintassi di un file per 'loadFromFile'
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
		// variabili ausiliarie
		boolean trovato = false;
		Movie film = null;
		
		// si converte i film in un array
		LinkedList<Movie> lista = this.dizionarioFilm.convertToList();
		Movie[] array = lista.toArray(new Movie[this.dizionarioFilm.getSize()]);
		
		// cerca il film scansionando tutti i film
		for(Movie movie : array) {
			if(movie.getTitle().compareTo(title) == 0) {
				// il film esiste
				trovato = true;
				film = movie;
				break;
			}
		}
		
		// controlla l'esistenza del film mediante la variabile 'trovato'
		if(trovato) {
			// si cancella il film e i relativi collaboratori
			this.grafo.removeMovieCollaboration(film);
			this.dizionarioFilm.delete("Title", film);
		}
		return trovato;
	}

	public Movie getMovieByTitle(String title) {
		// si converte i film in un array
		LinkedList<Movie> lista = this.dizionarioFilm.convertToList();
		Movie[] array = lista.toArray(new Movie[this.dizionarioFilm.getSize()]);
		
		// si cerca il film scansionanddo tutti i film
		for(Movie movie : array) {
			if(movie.getTitle().compareTo(title) == 0) {
				// il film esiste
				return movie;
			}
		}
		// il film non esiste
		return null;
	}

	public Person getPersonByName(String name) {
		// si converte gli attori in un array
		LinkedList<Person> lista = this.dizionarioAttori.convertToList();
		Person[] array = lista.toArray(new Person[this.dizionarioAttori.getSize()]);
		
		// si cerca la persona fra gli attori
		for(Person persona : array) {
			if(persona.getName().compareTo(name) == 0) {
				// la persona esiste
				return persona;
			}
		}
		
		// si converte i registi in un array
		lista = this.dizionarioRegisti.convertToList();
		array = lista.toArray(new Person[this.dizionarioRegisti.getSize()]);
		
		// si cerca la persona fra i registi
		for(Person persona : array) {
			if(persona.getName().compareTo(name) == 0) {
				// la persona esiste
				return persona;
			}
		}
		// il film non esiste
		return null;
	}

	public Movie[] getAllMovies() {
		// si converte i film in un array
		LinkedList<Movie> lista = this.dizionarioFilm.convertToList();
		Movie[] array = lista.toArray(new Movie[this.dizionarioFilm.getSize()]);
		return array;
	}

	public Person[] getAllPeople() {
		// si converte gli attori e i registi in un array
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
	public Movie[] searchMoviesByTitle(String title) {
		// la lista con i film che contengono una certa stringa
		ArrayList<Movie> tmp = new ArrayList<Movie>();
		// la lista con tutti i film
		LinkedList<Movie> lista = this.dizionarioFilm.convertToList();
		// si cerca i film che contengono nel titolo tale stringa
		for(Movie element : lista) {
			if(element.getTitle().contains(title)) {
				tmp.add(element);
			}
		}
		return tmp.toArray(new Movie[tmp.size()]);
	}

	public Movie[] searchMoviesInYear(Integer year) {
		// la lista con i film di un certo anno
		ArrayList<Movie> tmp = new ArrayList<Movie>();
		// la lista con tutti i film
		LinkedList<Movie> lista = this.dizionarioFilm.convertToList();
		// si cerca i film di un certo anno
		for(Movie element : lista) {
			if(element.getYear().equals(year)) {
				tmp.add(element);
			}
		}
		return tmp.toArray(new Movie[tmp.size()]);
	}

	public Movie[] searchMoviesDirectedBy(String name) {
		// la lista con i film diretti da un certo regista
		ArrayList<Movie> tmp = new ArrayList<Movie>();
		// la lista con tutti i film	
		LinkedList<Movie> lista = this.dizionarioFilm.convertToList();
		// si cerca i film diretti da un certo regista
		for(Movie element : lista) {
			if(element.getDirector().getName().equals(name)) {
				tmp.add(element);
			}
		}
		return tmp.toArray(new Movie[tmp.size()]);
	}

	public Movie[] searchMoviesStarredBy(String name) {
		// la lista con i film con un certo attore
		ArrayList<Movie> tmp = new ArrayList<Movie>();
		// la lista con tutti i film
		LinkedList<Movie> lista = this.dizionarioFilm.convertToList();
		// si cerca i film con un certo attore
		for(Movie element : lista) {
			for(Person persona : element.getCast()) {
				if(persona.getName().equals(name) && tmp.contains(element) == false) {
					tmp.add(element);
				}
			}
		}
		return tmp.toArray(new Movie[tmp.size()]);
	}

	public Movie[] searchMostVotedMovies(Integer N) {
		// si ottiene un array contenente tutti i film
		LinkedList<Movie> lista = this.dizionarioFilm.convertToList();
		Movie[] tmp = lista.toArray(new Movie[lista.size()]);
		// si sceglie il tipo di ordinamento
		this.ordinamento.setField("Votes");
		this.ordinamento.setOrdinamento(false);
		// si ordina
		tmp = this.ordinamento.ordina(tmp);
		// si modifica la lunghezza dell'input nei casi particolari
		// se N è più grande del numero totale dei film
		// se N è minore di 1
		if(N > tmp.length || N <= 0) {
			N = tmp.length;
		}
		// si copia i film nell'array (lungo N) che ritorna dalla funzione
		Movie[] array = new Movie[N];
		for(int index = 0; index < N; index++) {
			array[index] = tmp[index];
		}
		return array;
	}

	public Movie[] searchMostRecentMovies(Integer N) {
		// si ottiene un array contenente tutti i film
		LinkedList<Movie> lista = this.dizionarioFilm.convertToList();
		Movie[] tmp = lista.toArray(new Movie[lista.size()]);
		// si sceglie il tipo di ordinamento
		this.ordinamento.setField("Year");
		this.ordinamento.setOrdinamento(false);
		// si ordina
		tmp = this.ordinamento.ordina(tmp);
		// si modifica la lunghezza dell'input nei casi particolari
		// se N è più grande del numero totale dei film
		// se N è minore di 1
		if(N > tmp.length) {
			N = tmp.length;
		}
		// si copia i film nell'array (lungo N) che ritorna dalla funzione
		Movie[] array = new Movie[N];
		for(int index = 0; index < N; index++) {
			array[index] = tmp[index];
		}
		return array;
	}

	public Person[] searchMostActiveActors(Integer N) {
		// si ottiene un array contenente tutti gli attori
		LinkedList<Person> lista = new LinkedList<Person>(); 
		lista = this.dizionarioAttori.convertToList();
		Person[] tmp = lista.toArray(new Person[lista.size()]);
		// si sceglie il tipo di ordinamento
		this.ordinamento.setField("Activity");
		this.ordinamento.setOrdinamento(false);
		// si ordina
		tmp = ordinamento.ordina(tmp);
		// si modifica la lunghezza dell'input nei casi particolari
		// se N è più grande del numero totale dei film
		// se N è minore di 1
		if(N > tmp.length) {
			N = tmp.length;
		}
		// si copia gli attori nell'array (lungo N) che ritorna dalla funzione
		Person[] array = new Person[N];
		for(int index = 0; index < N; index++) {
			array[index] = tmp[index];
		}
		return array;
	}
	// IMovidaSearch FINE ------------------------------------------------------
	
	// IMovidaCollaborations INIZIO --------------------------------------------
	public Person[] getDirectCollaboratorsOf(Person actor) {
		if(actor == null) {
			return null;
		} else {
			return this.grafo.getDirect(actor);
		}
	}

	public Person[] getTeamOf(Person actor) {
		if(actor == null) {
			return null;
		} else {
			return this.grafo.getIndirect(actor);
		}
	}
	
	public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor) {
		if(actor == null) {
			return null;
		} else {
			return this.grafo.maximunSpanningTree(actor);
		}
	}
	
	// IMovidaCollaboration FINE -----------------------------------------------
	
}
