package movida.hanchu;

import movida.commons.*;

public interface Dizionario {
	public Movie search(Movie film);
	public void insert(Movie film);
	public void delete(Movie film);
}
