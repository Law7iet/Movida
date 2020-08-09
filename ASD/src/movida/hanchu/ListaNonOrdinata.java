package movida.hanchu;

import java.util.LinkedList;

public class ListaNonOrdinata<V> implements Dizionario<V> {
	protected ListaNonOrdinataNodo<V> head;
	
	public ListaNonOrdinata() {
		// inizializza 'head'
		this.head = null;
	}

	public V search(V value) {
		ListaNonOrdinataNodo<V> tmp = this.head;
		while(tmp != null) {
			if(tmp.getValue() == value) {
				// tmp ha come valore l'oggetto ricercato
				return tmp.getValue();
			} else {
				// tmp non ha come valore l'oggetto ricercato
				// si itera su tmp
				tmp = tmp.getNext();
			}
		}
		return null;
	}

	public void insert(V value) {
		// si cerca l'oggetto
		V item = search(value);
		if(item == null) {
			// l'oggetto non è presente
			// quindi si inserisce l'oggetto in testa
			if(this.head == null) {
				// 'head' è vuoto
				this.head = new ListaNonOrdinataNodo<V>();
				this.head.setValue(value);
			} else {
				// si crea una copia di 'head'
				ListaNonOrdinataNodo<V> tmp = this.head;
				// si inserisce il nuovo oggetto in 'head'
				this.head = new ListaNonOrdinataNodo<V>();
				this.head.setValue(value);
				// si collegga 'head' e 'tmp'
				this.head.setNext(tmp);
				tmp.setPrec(this.head);
			}
		}
	}
	
	public void delete(V value) {
		ListaNonOrdinataNodo<V> tmp = this.head;
		// cerca l'oggetto iterando sulla lista 'tmp'
		while(tmp != null) {
			if(tmp.getValue() == value) {
				// 'tmp' è l'oggetto ricercato
				// per eliminarlo si sposta il puntatore 'next' di 'prec' al 'next' di 'tmp'
				tmp.getPrec().setNext(tmp.getNext());
				tmp = null;
			} else {
				// 'tmp' non è l'oggetto ricercato
				// si cerca nel suo 'next'
				tmp = tmp.getNext();
			}
		}
	}

	public int getSize() {
		int counter = 0;
		ListaNonOrdinataNodo<V> tmp = this.head;
		// conta il numero di elementi iterando su 'tmp'
		while(tmp != null) {
				counter++;
				tmp = tmp.getNext();
		}
		return counter;
	}
	
	public LinkedList<V> convertToList() {
		LinkedList<V> lista = new LinkedList<V>();
		ListaNonOrdinataNodo<V> tmp = this.head;
		// itera su 'tmp'
		while(tmp != null) {
			// aggiunge in fondo a 'lista' l'elemento corrente in 'tmp'
			lista.addLast(tmp.getValue());
			tmp = tmp.getNext();
		}
		return lista;
	}

	public void convertToDizionario(V[] values) {
		this.head = null;
		int index = 0;
		// inserisce partendo dalla fine dell'array perché 'insert' inserisce in testa
		for(index = values.length - 1; index >= 0; index--) {
			this.insert(values[index]);
		}
	}

}
