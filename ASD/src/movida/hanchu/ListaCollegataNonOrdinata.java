package movida.hanchu;

import java.util.*;
import movida.commons.*;

public class ListaCollegataNonOrdinata implements Dizionario {
	protected LinkedList<Movie> movie;
	
	public ListaCollegataNonOrdinata() {
		this.movie = new LinkedList<Movie>();
	}

	public Movie search(Movie film) {
		int index = this.movie.indexOf(film);
		if(index == -1) {
			return null;
		} else {
			return this.movie.get(index);
		}
	}

	public void insert(Movie film) {
		Movie item = null;
		item = search(film);
		if (item == null) {
			this.movie.addFirst(film);
		}
	}

	public void delete(Movie film) {
		Movie item = null;
		item = search(film);
		if (item != null) {
			this.movie.remove(film);
		}
	}

}
