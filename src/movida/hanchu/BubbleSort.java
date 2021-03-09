package movida.hanchu;

import movida.commons.Movie;
import movida.commons.Person;

public class BubbleSort extends Ordinamento {

	// il costruttore
	public BubbleSort() {
		super();
	}
	
	public Movie[] bubbleSort(Movie[] values) {
		for(int i = 1; i < values.length; i++) {
			boolean scambiAvvenuti = false;
			for(int j = 1; j <= values.length - i; j++) {
				// la funzione 'confronta' è definita in 'Ordinamento'
				if(confronta(values[j - 1], values[j]) > 0) {
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
	
	public Person[] bubbleSort(Person[] values) {
		for(int i = 1; i < values.length; i++) {
			boolean scambiAvvenuti = false;
			for(int j = 1; j <= values.length - i; j++) {
				// la funzione 'confronta' è definita in 'Ordinamento'
				if(confronta(values[j - 1], values[j]) > 0) {
					Person tmp = values[j - 1];
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
	
	public Movie[] ordina(Movie[] values) {
		// chiama la funzione per i film
		return bubbleSort(values);
	}
	
	public Person[] ordina(Person[] values) {
		// chiama la funzione per le persone
		return bubbleSort(values);
	}
}
