package movida.hanchu;

import java.util.LinkedList;

public class ABR<V extends ComparableType<V>> implements Dizionario<V> {
	protected ABRNodo<V> root;
	
	public ABR() {
		this.root = null;
	}
	
	public ABRNodo<V> getRoot() {
		return this.root;
	}
	
	// ritorna il valore più piccolo della struttura dati
	// è per definizione di ABR il nodo più a sinistra
	public ABRNodo<V> min(ABRNodo<V> node) {
		if(node == null) {
			return null;
		} else {
			if(node.left != null) {
				return min(node.left);
			} else {
				return node;
			}
		}
	}
	
	// ritorna il valore più grande della struttura dati
	// è per definizione di ABR il nodo più a destra
	public ABRNodo<V> max(ABRNodo<V> node) {
		if(node == null) {
			return null;
		} else {
			if(node.right != null) {
				return max(node.right);
			} else {
				return node;
			}
		}
	}
	
	public ABRNodo<V> predecessore(ABRNodo<V> node) {
		if(node == null || min(this.root) == node) {
			// il nodo è vuoto
			return null;
		} else {
			// il nodo non è vuoto
			if(node.left != null) {
				// il nodo ha un figlio sinistro
				// allora è il massimo del figlio sinistro
				return max(node.left);
			} else {
				// non ha un figlio sinistro
				// lo si ricerca partendo dal suo genitore
				ABRNodo<V> parent = node.parent;
				// è il primo antenato che ha il nodo passato come parametro nel suo antenato sinistro
				while(node != null && node == parent.left) {
					node = parent;
					parent = parent.parent;
				}
				return parent;
			}
		}
	}
	
	public ABRNodo<V> successore(ABRNodo<V> node) {
		if(node == null || max(this.root) == node) {
			// il nodo è vuoto
			return null;
		} else {
			// il nodo non è vuoto
			if(node.right != null) {
				// il nodo ha un figlio destro
				// allora è il minimo del figlio sinistro
				return min(node.right);
			} else {
				// non ha un figlio destro
				// lo si ricerca partendo dal suo genitore
				ABRNodo<V> parent = node.parent;
				// è il primo antenato che ha il nodo passato come parametro nel suo antenato desstro
				while(node != null && node == parent.right) {
					node = parent;
					parent = parent.parent;
				}
				return parent;
			}
		}
	}
	
	public V search(String type, V value) throws MovidaCompareException {
		return search(this.root, type, value);
	}
	
	// cerca ricorsivamente il nodo
	private V search(ABRNodo<V> node, String type, V value) throws MovidaCompareException {
		if(node == null) {
			// non esiste il nodo ricercato
			return null;
		} else {
			if(node.value.compareTo(type, value) == 0) {
				// trovato il nodo
				return node.value;
			} else {
				if(node.value.compareTo(type, value) > 0) {
					// si cerca il nodo nel sotto-albero sinistro
					return search(node.left, type, value);
				} else {
					// si cerca il nodo nel sotto-albero destro
					return search(node.right, type, value);
				}
			}
		}
	}

	public void insert(String type, V value) throws MovidaCompareException {
        this.root = insert(this.root, null, type, value);
	}
	
	// inserisce un nodo cercando ricorsivamente il posto corretto
	private ABRNodo<V> insert(ABRNodo<V> node, ABRNodo<V> parent, String type, V value) throws MovidaCompareException {
		if(node == null) {
			// il nodo è vuoto
			// quindi si inserisce il nodo
			node = new ABRNodo<V>(value, parent);
		} else {
			if(node.value.compareTo(type, value) > 0) {
				// si inserisce il nodo nel sotto-albero sinitro
				node.left = insert(node.left, node, type, value);
			} else {
				// si inserisce il nodo nel sotto-albero destro
				node.right = insert(node.right, node, type, value);
			}
		}
		return node;
	}

	public void delete(String type, V value) throws MovidaCompareException {
		this.root = delete(this.root, type, value);
	}
	
	// cancella un nodo ricercandolo ricorsivamente
	private ABRNodo<V> delete(ABRNodo<V> node, String type, V value) throws MovidaCompareException {
		if(node == null) {
			// l'oggetto non è presente
			return null;
		} else {
			// si cerca l'oggetto
			if(node.value.compareTo(type, value) > 0) {
				// si trova a sinistra
	            node.left = delete(node.left, type, value);
			} else {
				if(node.value.compareTo(type, value) < 0)  {
		            // si trova a destra
					node.right = delete(node.right, type, value);
				} else {
					// 'node' è l'oggetto da cancellare
					if(node.left == null && node.right == null) {
						// 'node' è una foglia
						// lo si cancella direttamente
						node = null;
					} else {
						if(node.left != null && node.right != null) {
							// il nodo ha 2 figli 
							// si individua il predecessore
							ABRNodo<V> pred = predecessore(node);
							// il nodo da cancellare assume valore del predecessore
							node.value = pred.value;

							// il padre del predecessore ha come figlio destro il figlio sinistro del predecessore
							node.left = pred.left;
						} else {
							// il nodo ha un figlio solo
							if(node.left != null) {
								// il nodo ha figlio sinistro
								node.parent.left = node.left;
								node.left.parent = node.parent;
							} else {
								// il nodo ha un figlio destro
								node.parent.right = node.right;
								node.right.parent = node.left;
							}
						}
					}
				}
			}		
		}
		return node;
	}

	public int getSize() {
		return getSize(this.root);
	}
	
	// conta il numero dei nodo nella struttura ricorsivamente
	private int getSize(ABRNodo<V> node) {
		if(node == null) {
			// non esiste il nodo
			return 0;
		} else {
			// esiste il nodo
			// quindi si aggiunge +1 e si cerca nei suoi sotto-alberi
			return 1 + getSize(node.left) + getSize(node.right);
		}
	}

	// funzione ausiliaria a convertList
	// ha in input un nodo e una lista
	// visita il nodo con una pre-visita e aggiunge ad ogni visita il nodo alla lista
	public void pre_visit(ABRNodo<V> tmp, LinkedList<V> lista) {
		if(tmp != null) {
			if(tmp.left != null) {
				// 1 si visita prima il sotto-albero sinistro
				pre_visit(tmp.left, lista);
			}
			// 2 si visita il nodo corrente
			lista.addLast(tmp.value);
			if(tmp.right != null) {
				// 3 si visita il sotto-albero destro
				pre_visit(tmp.right, lista);
			}
		}
	}
	
	// gli elemento dell'albero vengono inseriti nella lista in ordine pre-visita
	public LinkedList<V> convertToList() {
		LinkedList<V> lista = new LinkedList<V>();
		pre_visit(this.root, lista);
		return lista;
	}
}
