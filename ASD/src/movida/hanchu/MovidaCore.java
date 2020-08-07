package movida.hanchu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import movida.commons.*;

public class MovidaCore implements IMovidaDB {
	protected Dizionario<Movie> dizionarioFilm;
	protected Dizionario<Person> dizionarioPersone;
	
	public MovidaCore() {
		this.dizionarioFilm = new ABR<Movie>();
		this.dizionarioPersone = new ListaCollegataNonOrdinata<Person>();
	}

	public void loadFromFile(File f) throws MovidaFileException {
		try {
			Path path = f.toPath();
			// converte il file in array di stringhe, ogni elemento è una riga del file
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_16);
			while(!lines.isEmpty()) {
				// copia le prime 5 righe
				String riga1 = lines.get(1);
				String riga2 = lines.get(2);
				String riga3 = lines.get(3);
				String riga4 = lines.get(4);
				String riga5 = lines.get(5);
				// controlla se le righe corrispondono ai dati di un film
				if(riga1.contains("Title: ") && riga2.contains("Year: ") && riga3.contains("Director: ") && riga4.contains("Cast: ") && riga5.contains("Votes: ")) {
					// cattura solo i dati significativi
					riga1 = riga1.substring(7);
					riga2 = riga2.substring(6);
					riga3 = riga3.substring(9);
					riga4 = riga4.substring(6);
					riga5 = riga5.substring(7);
				} else {
					throw new MovidaFileException();
				}
				// copia i dati mel giusto formato per il film
				String title = riga1;
				int year = Integer.parseInt(riga2);
				Person director = new Person(riga3);
				// separa la lista degli attori in un array di stringhe
				String[] tmp = riga4.split(", ");
				Person[] cast = new Person[tmp.length];
				int index = 0;
				// assegna ad ogni elemento di Person un attore
				for(String name : tmp) {
					cast[index] = new Person(name);
				}
				int votes = Integer.parseInt(riga5);
				// istanza dell'oggetto movie
				Movie movie = new Movie(title, year, votes, cast, director);
				// controllo della sua presenza del database
				if(dizionarioFilm.search(movie) == null) {
					// se non è presente lo si inserisce
					dizionarioFilm.insert(movie);
				} else {
					// se è presente lo si sovrascrive
					dizionarioFilm.delete(movie);
					dizionarioFilm.insert(movie);
				}

				// ---------------------------------------------------------
				// controllo della presenza delle persone
				if(dizionarioPersone.search(director) == null) {
					dizionarioPersone.insert(director);
				}
				for(Person persona : cast) {
					if(dizionarioPersone.search(persona) == null) {
						dizionarioPersone.insert(director);
					}
				}
				// ----------------------------------------------------------
				
				// cancellazione delle righe usate
				for(index = 1; index < 6; index++) {
					lines.remove(1);
				}
				// rimuovere la riga vuota
				if(lines.get(1) == "\r\n") {
					lines.remove(1);
				}
			}
		}
		catch(Exception e) {
			e.getMessage();
		}
	}

	public void saveToFile(File f) {
		// cancello tutti i dati del file
		PrintWriter writer;
		try {
			writer = new PrintWriter(f);
			writer.print("");
			writer.close();
			// inserisco i dati del database
			LinkedList<Movie> tmp = dizionarioFilm.convertList();
			Movie[] lista;
			lista = (Movie[]) tmp.toArray();
			
			// stampa i film
			writer.println("FILM:");
			for(Movie movie : lista) {
				writer.println("Title: " + movie.getTitle());
				writer.println("Year: " + movie.getYear());
				writer.println("Director: " + movie.getDirector());
				Person[] cast = movie.getCast();
				int numero = cast.length;
				writer.print("Cast: ");
				writer.print(cast[0].getName());
				for(int index = 1; index < numero; index++) {
					writer.print(", ");
					writer.print(cast[index].getName());
				}
				writer.print("\r\n");
				writer.println("Votes: " + movie.getVotes());
				writer.println("");
			}
			
			// stampa i personaggi
			writer.println("PERSONAGGI:");
			LinkedList<Person> x = dizionarioPersone.convertList();
			Person[] persone = (Person[]) x.toArray();
			for(Person persona : persone) {
				writer.println(persona.getName());
			}
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		dizionarioFilm = null;
		dizionarioPersone = null;

	}

	public int countMovies() {
		return dizionarioFilm.getSize();
	}

	public int countPeople() {
		return dizionarioPersone.getSize();
	}


	public boolean deleteMovieByTitle(String title) {
		boolean trovato = false;
		LinkedList<Movie> tmp = dizionarioFilm.convertList();
		Movie[] lista;
		Movie film = null;
		lista = (Movie[]) tmp.toArray();
		for(Movie movie : lista) {
			if(movie.getTitle().compareTo(title) == 0) {
				trovato = true;
				film = movie;
				break;
			}
		}
		if(trovato) {
			dizionarioFilm.delete(film);
		}
		return trovato;
	}

	public Movie getMovieByTitle(String title) {
		LinkedList<Movie> tmp = dizionarioFilm.convertList();
		Movie[] lista = (Movie[]) tmp.toArray();
		for(Movie movie : lista) {
			if(movie.getTitle().compareTo(title) == 0) {
				return movie;
			}
		}
		return null;
	}

	public Person getPersonByName(String name) {
		LinkedList<Person> tmp = dizionarioPersone.convertList();
		Person[] lista = (Person[]) tmp.toArray();
		for(Person persona : lista) {
			if(persona.getName().compareTo(name) == 0) {
				return persona;
			}
		}
		return null;
	}

	public Movie[] getAllMovies() {
		LinkedList<Movie> tmp = dizionarioFilm.convertList();
		return (Movie[]) tmp.toArray();
	}

	public Person[] getAllPeople() {
		LinkedList<Person> tmp = dizionarioPersone.convertList();
		return (Person[]) tmp.toArray();
	}

}
