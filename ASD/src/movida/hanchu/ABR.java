package movida.hanchu;

import java.util.LinkedList;

public class ABR<V extends Comparable<V>> implements Dizionario<V> {
	protected ABRNodo<V> root;
	
	public ABR() {
		this.root = new ABRNodo<V>();
	}
	
	public ABRNodo<V> min(ABRNodo<V> node) {
		while(node != null && node.getSinistro() != null) {
			node = node.getSinistro();
		}
		return node;
	}
	
	public ABRNodo<V> max(ABRNodo<V> node) {
		while(node != null && node.getDestro() != null) {
			node = node.getDestro();
		}
		return node;
	}
	
	public ABRNodo<V> predecessore(ABRNodo<V> node) {
		if(node == null) {
			// il nodo è vuoto
			return null;
		} else {
			// il nodo non è vuoto
			if(node.getSinistro() != null) {
				// il nodo ha un figlio sinistro
				// allora è il massimo del figlio sinistro
				return max(node.getSinistro());
			} else {
				// non ha un figlio sinistro
				// lo si ricerca partendo dal suo genitore
				ABRNodo<V> parent = node.getPadre();
				// è il primo antenato che ha il nodo passato come parametro nel suo antenato sinistro
				while(node != null && node == parent.getSinistro()) {
					node = parent;
					parent = parent.getPadre();
				}
				return parent;
			}
		}
	}
	
	public ABRNodo<V> successore(ABRNodo<V> node) {
		if(node == null) {
			// il nodo è vuoto
			return null;
		} else {
			// il nodo non è vuoto
			if(node.getDestro() != null) {
				// il nodo ha un figlio destro
				// allora è il minimo del figlio sinistro
				return min(node.getDestro());
			} else {
				// non ha un figlio destro
				// lo si ricerca partendo dal suo genitore
				ABRNodo<V> parent = node.getPadre();
				// è il primo antenato che ha il nodo passato come parametro nel suo antenato destro
				while(node != null && node == parent.getDestro()) {
					node = parent;
					parent = parent.getPadre();
				}
				return parent;
			}
		}
	} 
	
	public V search(V value) {
		ABRNodo<V> node = this.root;
		// ricercca binaria
		while(node != null) {
			if(node.getValue().compareTo(value) == 0) {
				// il nodo è l'oggetto ricercato
				return (node.getValue());
			} else {
				if(node.getValue().compareTo(value) < 0) {
					// l'oggetto ricercato è più grande e si trova a destra
					node = node.destro;
				} else {
					// l'oggetto ricercato e più piccolo e si trova a sinistra
					node = node.sinistro;
				}
			}
		}
		// l'oggetto ricercato non è presente
		return null;
	}

	// l'ordine è in base al titolo del film
	public void insert(V value) {
		// cerca l'oggetto
		V item = search(value);
		if(item == null) {
			// l'oggetto non è presente
			// si cerca il ramo giusto per l'oggetto
			ABRNodo<V> node = this.root;
			ABRNodo<V> parent = null;
			while(node != null) {
				if(node.getValue().compareTo(value) < 0) {
					// si sposta sul ramo destro
					parent = node;
					node = node.destro;
				} else {
					// si sposta sul ramo sinistro
					parent = node;
					node = node.sinistro;
				}
			}
			// si crea il nuovo nodo e si aggiunge l'oggetto
			node = new ABRNodo<V>();
			node.setPadre(parent);
			node.setValue(value);
		}
	}

	public void delete(V value) {
		// si cerca l'oggetto
		V item = search(value);
		if(item != null) {
			// l'oggetto è presente
			// si cerca l'oggetto
			ABRNodo<V> nodo = this.root;
			ABRNodo<V> parent = null;
			// si intera finché non lo si trova
			// si usa la ricerca binaria
			while(nodo.getValue().compareTo(value) == 0) {
				if(nodo.getValue().compareTo(value) < 0) {
					parent = nodo;
					nodo = nodo.destro;
				} else {
					parent = nodo;
					nodo = nodo.sinistro;
				}
			}
			// l'oggetto da cancellare è 'node'
			if(nodo.getDestro() == null && nodo.getSinistro() == null) {
				// 'node' è una foglia
				// lo si cancella direttamente
				nodo = null;
			} else {
				if(nodo.getDestro() != null && nodo.getSinistro() != null) {
					// il nodo ha 2 figli 
					// si individua il predecessore
					ABRNodo<V> pred = predecessore(nodo);
					// il padre del preddecessore ha come figlio destro il figlio sinistro del predecessore
					parent.getPadre().setDestro(pred.getSinistro());
					// il nodo da cancellare assume valore del predecessore
					nodo.setValue(parent.getValue());
				} else {
					// il nodo ha un figlio solo
					if(nodo.getDestro() != null) {
						// il nodo ha un figlio destro
						nodo.getPadre().setDestro(nodo.getDestro());
						nodo.getDestro().setPadre(nodo.getPadre());
					} else {
						// il nodo ha figlio sinistro
						nodo.getPadre().setSinistro(nodo.getSinistro());
						nodo.getSinistro().setPadre(nodo.getPadre());
					}
				}
			}
		}
	}

	public int getSize() {
		// si fa una visita dell'albero utilizzando una coda che contiene i nodi non visitati
		LinkedList<ABRNodo<V>> coda = new LinkedList<ABRNodo<V>>();
		// si mette nella coda il nodo-radice
		coda.addFirst(this.root);
		int counter = 0;
		// se la coda è vuota significa che si ha visitato tutto l'albero
		while(!coda.isEmpty()) {
			// si aggiunge in coda i suoi figli
			ABRNodo<V> tmp = coda.getLast();
			if(tmp.getSinistro() != null) {
				coda.addFirst(tmp.getSinistro());
			}
			if(tmp.getDestro() != null) {
				coda.addFirst(tmp.getDestro());
			}
			// si visita il nodo e lo si cancella
			counter++;
			coda.removeLast();
		}
		return counter;
	}

	// funzione ausiliaria a convertList
	// ha in input un nodo e una lista
	// visita il nodo con una pre-visita e aggiunge ad ogni visita il nodo alla lista
	public void pre_visit(ABRNodo<V> tmp, LinkedList<V> lista) {
		if(tmp != null) {
			if(tmp.getSinistro() != null) {
				// 1 si visita prima il sotto-albero sinistro
				pre_visit(tmp.getSinistro(), lista);
			}
			// 2 si visita il nodo corrente
			lista.addLast(tmp.getValue());
			if(tmp.getDestro() != null) {
				// 3 si visita il sotto-albero destro
				pre_visit(tmp.getDestro(), lista);
			}
		}
	}
	
	// gli elemento dell'albero vengono inseriti nella lista in ordine pre-visita
	public LinkedList<V> convertToList() {
		LinkedList<V> lista = new LinkedList<V>();
		pre_visit(this.root, lista);
		return lista;
	}

	// !----------------------------------------------------------------------------------------------
	// se si converte l'albero in un array e lo si ordina
	// dopo aver convertito l'albero con i dati dell'array, futuri insert non rendono l'albero un ABR
	// !----------------------------------------------------------------------------------------------
	public void convertToDizionario(V[] values) {
		// essendo un ABR e l'array "ordinato" si inseriscono gli elementi dal minimo
		ABRNodo<V> tmp = min(this.root);
		for(V element : values) {
			tmp.value = element;
			// si passa al successivo elemento
			tmp = successore(tmp);		
		}
	}
}
