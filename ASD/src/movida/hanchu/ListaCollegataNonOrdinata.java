package movida.hanchu;

import java.util.LinkedList;

public class ListaCollegataNonOrdinata<T> implements Dizionario<T> {
	protected LinkedList<T> value;
	
	public ListaCollegataNonOrdinata() {
		this.value = new LinkedList<T>();
	}

	public T search(T value) {
		int index = this.value.indexOf(value);
		if(index == -1) {
			return null;
		} else {
			return this.value.get(index);
		}
	}

	public void insert(T value) {
		T item = null;
		item = search(value);
		if (item == null) {
			this.value.addFirst(value);
		}
	}

	public void delete(T film) {
		T item = null;
		item = search(film);
		if (item != null) {
			this.value.remove(film);
		}
	}

	public int getSize() {
		return this.value.size();
	}

	public LinkedList<T> convertList() {
		return this.value;
	}

}
