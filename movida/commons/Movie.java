/* 
 * Copyright (C) 2020 - Angelo Di Iorio
 * 
 * Progetto Movida.
 * Corso di Algoritmi e Strutture Dati
 * Laurea in Informatica, UniBO, a.a. 2019/2020
 * 
*/
package movida.commons;

import movida.hanchu.ComparableType;
import movida.hanchu.MovidaCompareException;

/**
 * Classe usata per rappresentare un film
 * nell'applicazione Movida.
 * 
 * Un film � identificato in modo univoco dal titolo 
 * case-insensitive, senza spazi iniziali e finali, senza spazi doppi. 
 * 
 * La classe pu� essere modicata o estesa ma deve implementare tutti i metodi getter
 * per recupare le informazioni caratterizzanti di un film.
 * 
 */
public class Movie implements ComparableType<Movie> {
	
	private String title;
	private Integer year;
	private Integer votes;
	private Person[] cast;
	private Person director;
	
	public Movie(String title, Integer year, Integer votes, Person[] cast, Person director) {
		this.title = title;
		this.year = year;
		this.votes = votes;
		this.cast = cast;
		this.director = director;
	}

	public String getTitle() {
		return this.title;
	}

	public Integer getYear() {
		return this.year;
	}

	public Integer getVotes() {
		return this.votes;
	}

	public Person[] getCast() {
		return this.cast;
	}

	public Person getDirector() {
		return this.director;
	}
	
	// compara il titolo di due film
	public int compareTo(String type, Movie movie) throws MovidaCompareException {
		switch(type) {
		case "Title":
			// converto le lettere dei film in minuscole
			String tmp1 = this.title.toLowerCase();
			String tmp2 = movie.getTitle().toLowerCase();
			// faccio il paragone
			return tmp1.compareTo(tmp2);
		case "Year":
			return this.year.compareTo(movie.getYear());
		case "Director":
			String tmp3 = this.director.getName().toLowerCase().trim();
			String tmp4 = movie.getDirector().getName().toLowerCase().trim();
			return tmp3.compareTo(tmp4);
		case "Votes":
			return this.votes.compareTo(movie.getVotes());
		default:
			throw new MovidaCompareException();
		}
	}
}
