package movida.hanchu;

import java.util.ArrayList;
import movida.commons.Movie;
import movida.commons.Person;

public class MergeSort extends Ordinamento {
		
	public MergeSort() {
		super();
	}
	
	public Movie[] merge(Movie[] values, int i1, int f1, int i2, int f2) {
		int distance = f2 - i1;
		int tmp = i1;
		int index = 0;
		ArrayList<Movie> A = new ArrayList<Movie>(distance);
		while(i1 <= f1 && i2 <= f2) {
			if(confronta(values[i1], values[i2]) < 0) {
				A.add(index, values[i1]);
				i1++;
			} else {
				A.add(index, values[i2]);
				i2++;
			}
			index++;
		}
		while(i1 <= f1) {
			A.add(index, values[i1]);
			i1++;
			index++;
		}
		while(i2 <= f2) {
			A.add(index, values[i2]);
			i2++;
			index++;
		}
		for(index = 0; index <= distance; index++) {
			values[tmp] = A.get(index);
			tmp++;
		}
		return values;
	}
	
	public Person[] merge(Person[] values, int i1, int f1, int i2, int f2) {
		int distance = f2 - i1;
		int tmp = i1;
		int index = 0;
		ArrayList<Person> A = new ArrayList<Person>(distance);
		while(i1 <= f1 && i2 <= f2) {
			if(confronta(values[i1], values[i2]) < 0) {
				A.add(index, values[i1]);
				i1++;
			} else {
				A.add(index, values[i2]);
				i2++;
			}
			index++;
		}
		while(i1 <= f1) {
			A.add(index, values[i1]);
			i1++;
			index++;
		}
		while(i2 <= f2) {
			A.add(index, values[i2]);
			i2++;
			index++;
		}
		for(index = 0; index <= distance; index++) {
			values[tmp] = A.get(index);
			tmp++;
		}
		return values;
	}
	
	public Movie[] mergeSort(Movie[] values, int i, int f) {
		if (i < f) {
	        int m = (int)((i+f)/2);
	        values = mergeSort(values, i, m);
	        values = mergeSort(values, m + 1, f);
	        values = merge(values, i, m, m + 1, f);
	    }
		return values;
	}
	
	public Person[] mergeSort(Person[] values, int i, int f) {
		if (i < f) {
	        int m = (int)((i+f)/2);
	        values = mergeSort(values, i, m);
	        values = mergeSort(values, m + 1, f);
	        values = merge(values, i, m, m + 1, f);
		}
        return values;
	}
	
	public Movie[] ordina(Movie[] values) {
		return mergeSort(values, 0, values.length - 1);
	}
	
	public Person[] ordina(Person[] values) {
		return mergeSort(values, 0, values.length - 1);
	}
}
