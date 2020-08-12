package movida.hanchu;

import movida.commons.Movie;
import movida.commons.Person;

public abstract class Ordinamento {
	protected String field;
	protected boolean crescente;
	
	public Ordinamento() {
		this.field = "";
		this.crescente = false;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public void setOrdinamento(boolean crescente) {
		this.crescente = crescente;
	}
	
	public Movie[] ordina(Movie[] values) {
		// dipende dall'algoritmo di ordinamento
		return null;
	}
	
	public Person[] ordina(Person[] values) {
		
		return null;
	}
}
