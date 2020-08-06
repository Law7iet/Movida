package movida.hanchu;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_16);
			while(!lines.isEmpty()) {

				String riga1 = lines.get(1);
				String riga2 = lines.get(2);
				String riga3 = lines.get(3);
				String riga4 = lines.get(4);
				String riga5 = lines.get(5);

				if(riga1.contains("Title: ") && riga2.contains("Year: ") && riga3.contains("Director: ") && riga4.contains("Cast: ") && riga5.contains("Votes: ")) {
					riga1 = riga1.substring(7);
					riga2 = riga2.substring(6);
					riga3 = riga3.substring(9);
					riga4 = riga4.substring(6);
					riga5 = riga5.substring(7);
				} else {
					throw new MovidaFileException();
				}

				String title = riga1;
				int year = Integer.parseInt(riga2);
				Person director = new Person(riga3);
				String[] tmp = riga4.split(", ");
				Person[] cast = new Person[tmp.length];
				int index = 0;
				for(String name : tmp) {
					cast[index] = new Person(name);
				}
				int votes = Integer.parseInt(riga5);

				Movie movie = new Movie(title, year, votes, cast, director);
				if(dizionarioFilm.search(movie) == null) {
					dizionarioFilm.insert(movie);
				} else {
					dizionarioFilm.delete(movie);
					dizionarioFilm.insert(movie);
				}

				if(dizionarioPersone.search(director) == null) {
					dizionarioPersone.insert(director);
				}
				for(Person persona : cast) {
					if(dizionarioPersone.search(persona) == null) {
						dizionarioPersone.insert(director);
					}
				}


				
				for(index = 1; index < 7; index++) {
					lines.remove(1);
				}
			}
		}
		catch(Exception e) {
			new MovidaFileException().getMessage();
		}
	}

	@Override
	public void saveToFile(File f) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public int countMovies() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countPeople() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteMovieByTitle(String title) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Movie getMovieByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person getPersonByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie[] getAllMovies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person[] getAllPeople() {
		// TODO Auto-generated method stub
		return null;
	}

}
