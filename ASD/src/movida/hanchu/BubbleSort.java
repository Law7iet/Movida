package movida.hanchu;

import movida.commons.Movie;
import movida.commons.Person;

public class BubbleSort extends Ordinamento {

	public BubbleSort() {
		super();
	}
	
	public Movie[] bubbleSortVotesCresc(Movie[] values) {
		for(int i = 1; i < values.length; i++) {
			boolean scambiAvvenuti = false;
			for(int j = 1; j <= values.length - i; j++) {
				if(values[j - 1].getVotes().compareTo(values[j].getVotes()) > 0) {
					Movie tmp = values[j - 1];
					values[j - 1] = values[j];
					values[j] = tmp;
					scambiAvvenuti = true;
				}
			}
			if(!scambiAvvenuti) {
				break;
			}
		}
		return values;
	}

	public Movie[] bubbleSortYearCresc(Movie[] values) {
		for(int i = 1; i < values.length; i++) {
			boolean scambiAvvenuti = false;
			for(int j = 1; j <= values.length - i; j++) {
				if(values[j - 1].getYear().compareTo(values[j].getYear()) > 0) {
					Movie tmp = values[j - 1];
					values[j - 1] = values[j];
					values[j] = tmp;
					scambiAvvenuti = true;
				}
			}
			if(!scambiAvvenuti) {
				break;
			}
		}
		return values;
	}
	
	public Movie[] bubbleSortVotesDecresc(Movie[] values) {
		for(int i = 1; i < values.length; i++) {
			boolean scambiAvvenuti = false;
			for(int j = 1; j <= values.length - i; j++) {
				if(values[j - 1].getVotes().compareTo(values[j].getVotes()) < 0) {
					Movie tmp = values[j - 1];
					values[j - 1] = values[j];
					values[j] = tmp;
					scambiAvvenuti = true;
				}
			}
			if(!scambiAvvenuti) {
				break;
			}
		}
		return values;
	}

	public Movie[] bubbleSortYearDecresc(Movie[] values) {
		for(int i = 1; i < values.length; i++) {
			boolean scambiAvvenuti = false;
			for(int j = 1; j <= values.length - i; j++) {
				if(values[j - 1].getYear().compareTo(values[j].getYear()) < 0) {
					Movie tmp = values[j - 1];
					values[j - 1] = values[j];
					values[j] = tmp;
					scambiAvvenuti = true;
				}
			}
			if(!scambiAvvenuti) {
				break;
			}
		}
		return values;
	}
	
	public Person[] bubbleSortNameCresc(Person[] values) {
		for(int i = 0; i < values.length; i++) {
			boolean scambiAvvenuti = false;
			for(int j = 1; j <= values.length - i; j++) {
				if(values[j - 1].getName().compareTo(values[j].getName()) > 0) {
					Person tmp = values[j - 1];
					values[j - 1] = values[j];
					values[j] = tmp;
					scambiAvvenuti = true;
				}
				if(!scambiAvvenuti) {
					break;
				}
			}
		}
		return values;
	}
	
	public Person[] bubbleSortNameDecresc(Person[] values) {
		for(int i = 0; i < values.length; i++) {
			boolean scambiAvvenuti = false;
			for(int j = 1; j <= values.length - i; j++) {
				if(values[j - 1].getName().compareTo(values[j].getName()) < 0) {
					Person tmp = values[j - 1];
					values[j - 1] = values[j];
					values[j] = tmp;
					scambiAvvenuti = true;
				}
				if(!scambiAvvenuti) {
					break;
				}
			}
		}
		return values;
	}
	
	public Person[] bubbleSortActivityCresc(Person[] values) {
		for(int i = 0; i < values.length; i++) {
			boolean scambiAvvenuti = false;
			for(int j = 1; j <= values.length - i; j++) {
				if(values[j - 1].getFilm() < values[j].getFilm()) {
					Person tmp = values[j - 1];
					values[j - 1] = values[j];
					values[j] = tmp;
					scambiAvvenuti = true;
				}
				if(!scambiAvvenuti) {
					break;
				}
			}
		}
		return values;
	}
	
	public Person[] bubbleSortActivityDecresc(Person[] values) {
		for(int i = 0; i < values.length; i++) {
			boolean scambiAvvenuti = false;
			for(int j = 1; j <= values.length - i; j++) {
				if(values[j - 1].getFilm() > values[j].getFilm()) {
					Person tmp = values[j - 1];
					values[j - 1] = values[j];
					values[j] = tmp;
					scambiAvvenuti = true;
				}
				if(!scambiAvvenuti) {
					break;
				}
			}
		}
		return values;
	}
	
	public Movie[] ordina(Movie[] values) {
		if(this.field == "Year") {
			if(this.crescente == true) {
				values = bubbleSortYearCresc(values);
			} else {
				values = bubbleSortYearDecresc(values);
			}
		}
		if(this.field == "Votes") {
			if(this.crescente == true) {
				values = bubbleSortVotesCresc(values);
			} else {
				values = bubbleSortVotesDecresc(values);
			}
		}
		return values;
	}
	
	public Person[] ordina(Person[] values) {
		if(this.field == "Name") {
			if(this.crescente == true) {
				values = bubbleSortNameCresc(values);
			} else {
				values = bubbleSortNameDecresc(values);
			}
		}
		if(this.field == "Activity") {
			if(this.crescente == true) {
				values = bubbleSortActivityCresc(values);
			} else {
				values = bubbleSortActivityDecresc(values);
			}
		}
		return values;
	}
}
