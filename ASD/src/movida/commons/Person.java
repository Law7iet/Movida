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
 * Classe usata per rappresentare una persona, attore o regista,
 * nell'applicazione Movida.
 * 
 * Una persona � identificata in modo univoco dal nome 
 * case-insensitive, senza spazi iniziali e finali, senza spazi doppi. 
 * 
 * Semplificazione: <code>name</code> � usato per memorizzare il nome completo (nome e cognome)
 * 
 * La classe pu� essere modicata o estesa ma deve implementare il metodo getName().
 * 
 */
public class Person implements ComparableType<Person> {

	private String name;
	private int film;
	
	public Person(String name, int film) {
		this.name = name;
		this.film = film;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getFilm() {
		return this.film;
	}
	
	public void addFilm() {
		this.film++;
	}
	
	public void removeFilm() {
		this.film--;
	}
	
	// compara il titolo di due film
	public int compareTo(String type, Person name) throws MovidaCompareException {
		switch(type) {
		case "Name":
			// converto le lettere dei film in minuscole
			String tmp1 = this.name.toLowerCase();
			String tmp2 = name.getName().toLowerCase();
			// faccio il paragone
			return tmp1.compareTo(tmp2);
		default:
			throw new MovidaCompareException();
		}
			
	}
}
