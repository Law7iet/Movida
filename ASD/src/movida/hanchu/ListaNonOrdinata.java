package movida.hanchu;

import java.util.LinkedList;

public class ListaNonOrdinata<V extends ComparableType<V>> implements Dizionario<V> {
	protected ListaNonOrdinataNodo<V> root;
	
	public ListaNonOrdinata() {
		this.root = null;
	}

	public V search(String type, V value) throws MovidaCompareException {
		// si cerca il valore su 'tmp'
		ListaNonOrdinataNodo<V> tmp = this.root;
		while(tmp != null) {
			if(tmp.value.compareTo(type, value) == 0) {
				// 'tmp' ha come valore l'oggetto ricercato
				return tmp.value;
			} else {
				// 'tmp' non ha come valore l'oggetto ricercato
				// si itera su 'tmp'
				tmp = tmp.next;
			}
		}
		return null;
	}

	// si inserisce il nodo in testa
	public void insert(String type, V value) throws MovidaCompareException {
		// si cerca l'oggetto
		V item = search(type, value);
		if(item == null) {
			// l'oggetto non è presente
			if(this.root == null) {
				// la struttura è vuota
				// si inserisce il nodo
				this.root = new ListaNonOrdinataNodo<V>();
				this.root.value = value;
			} else {
				// si crea una copia della struttura
				ListaNonOrdinataNodo<V> tmp = this.root;
				// si crea una nuova struttura con il nuovo nodo
				this.root = new ListaNonOrdinataNodo<V>();
				this.root.value = value;
				// si collega in coda la vecchia struttura (copiata)
				this.root.next = tmp;
				tmp.prec = this.root;
			}
		}
	}
	
	public void delete(String type, V value) throws MovidaCompareException {
		ListaNonOrdinataNodo<V> tmp = this.root;
		// cerca l'oggetto iterando sulla lista 'tmp'
		while(tmp != null) {
			if(tmp.value.compareTo(type, value) == 0) {
				// 'tmp' è l'oggetto ricercato
				// per eliminarlo si sposta il puntatore 'next' di 'prec' al 'next' di 'tmp'
				tmp.prec.next = tmp.next;
				tmp = null;
			} else {
				// 'tmp' non è l'oggetto ricercato
				// si cerca nel suo 'next'
				tmp = tmp.next;
			}
		}
	}

	public int getSize() {
		int counter = 0;
		ListaNonOrdinataNodo<V> tmp = this.root;
		// conta il numero di elementi iterando su 'tmp'
		while(tmp != null) {
				counter++;
				tmp = tmp.next;
		}
		return counter;
	}
	
	public LinkedList<V> convertToList() {
		LinkedList<V> lista = new LinkedList<V>();
		ListaNonOrdinataNodo<V> tmp = this.root;
		// itera su 'tmp'
		while(tmp != null) {
			// aggiunge in fondo a 'lista' l'elemento corrente in 'tmp'
			lista.addFirst(tmp.value);
			tmp = tmp.next;
		}
		return lista;
	}
}
