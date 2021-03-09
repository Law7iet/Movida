package movida.hanchu;

import movida.commons.Movie;
import movida.commons.Person;

public abstract class Ordinamento {
	// indica il tipo da confrontare
	protected String field;
	// indica l'ordine di confronto
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
	
	// confronta due film in base al tipo
	public int confronta(Movie x, Movie y) {
		int value = 0;
		switch(this.field) {
		case "Title":
			if(this.crescente == true) {
				value = x.getTitle().compareTo(y.getTitle());
			} else {
				value = -1 * (x.getTitle().compareTo(y.getTitle()));
			}
			break;
		case "Votes":
			if(this.crescente == true) {
				value = x.getVotes() - y.getVotes();
			} else {
				value = -1 * (x.getVotes() - y.getVotes());
			}
			break;
		case "Year":
			if(this.crescente == true) {
				value = x.getYear() - y.getYear();
			} else {
				value = -1 * (x.getYear() - y.getYear());
			}
			break;
		default:
			value = -1;
		}
		return value;
	}
	
	// confronta le persone in base al tipo
	public int confronta(Person x, Person y) {
		int value = 0;
		switch(this.field) {
		case "Name":
			if(this.crescente == true) {
				value = x.getName().compareTo(y.getName());
			} else {
				value = -1 * (x.getName().compareTo(y.getName()));
			}
			break;
		case "Activity":
			if(this.crescente == true) {
				value = x.getFilm() - y.getFilm();
			} else {
				value = -1 * (x.getFilm() - y.getFilm());
			}
			break;
		default:
			value = -1;
		}
		return value;
	}
	
	// ordina un array di film
	public Movie[] ordina(Movie[] values) {
		return null;
	}
	
	// ordina un array di persone
	public Person[] ordina(Person[] values) {	
		return null;
	}
}
