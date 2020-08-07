package movida.hanchu;

import java.util.LinkedList;

public class ABR<T extends Comparable<T>> implements Dizionario<T> {
	protected ABRNodo<T> abr;
	
	public ABR() {
		this.abr = new ABRNodo<T>();
	}
	
	public ABRNodo<T> max(ABRNodo<T> tmp) {
		while(tmp != null && tmp.getDestro() != null) {
			tmp = tmp.getDestro();
		}
		return tmp;
	}
	
	public ABRNodo<T> predecessore(ABRNodo<T> tmp) {
		if(tmp == null) {
			return null;
		} else {
			if(tmp.getSinistro() != null) {
				return max(tmp.getSinistro());
			} else {
				ABRNodo<T> prec = tmp.getPadre();
				while(tmp != null && tmp == prec.getSinistro()) {
					tmp = prec;
					prec = prec.getPadre();
				}
				return prec;
			}
		}
	}
	
	public T search(T value) {
		ABRNodo<T> tmp = this.abr;
		while(tmp != null) {
			if(tmp.getValue().compareTo(value) == 0) {
				// il nodo è l'oggetto ricercato
				return (tmp.getValue());
			} else {
				if(tmp.getValue().compareTo(value) < 0) {
					// l'oggetto ricercato è più grande 
					tmp = tmp.destro;
				} else {
					// l'oggetto ricercato e più piccolo
					tmp = tmp.sinistro;
				}
			}
		}
		// l'oggetto ricercato non è presente
		return null;
	}

	public void insert(T value) {
		// cerca l'oggetto
		T item = search(value);
		if(item == null) {
			// l'oggetto non è presente
			ABRNodo<T> tmp = this.abr;
			ABRNodo<T> prec = null;
			// si cerca il ramo giusto per l'oggetto
			while(tmp != null) {
				if(tmp.getValue().compareTo(value) < 0) {
					// si sposta sul ramo destro
					prec = tmp;
					tmp = tmp.destro;
				} else {
					// si sposta sul ramo sinistro
					prec = tmp;
					tmp = tmp.sinistro;
				}
			}
			// si crea il nuovo nodo e si aggiunge l'oggetto
			tmp = new ABRNodo<T>();
			tmp.setPadre(prec);
			tmp.setValue(value);
		}
	}

	public void delete(T value) {
		// si cerca l'oggetto
		T item = null;
		item = search(value);
		if(item != null) {
			// l'oggetto è presente
			ABRNodo<T> tmp = this.abr;
			ABRNodo<T> prec = null;
			// si cerca il film
			while(tmp.getValue().compareTo(value) == 0) {
				if(tmp.getValue().compareTo(value) < 0) {
					prec = tmp;
					tmp = tmp.destro;
				} else {
					prec = tmp;
					tmp = tmp.sinistro;
				}
			}
			// l'oggetto da cancellare è il nodo tmp
			if(tmp.getDestro() == null && tmp.getSinistro() == null) {
				// il nodo è una foglia
				tmp = null;
			} else {
				if(tmp.getDestro() != null && tmp.getSinistro() != null) {
					// il nodo ha 2 figli 
					// si individua il predecessore
					ABRNodo<T> pred = predecessore(tmp);
					// il padre del preddecessore ha come figlio destro il figlio sinistro del predecessore
					prec.getPadre().setDestro(pred.getSinistro());
					// il nodo da cancellare assume valore del predecessore
					tmp.setValue(prec.getValue());
				} else {
					// il nodo ha un figlio solo
					if(tmp.getDestro() != null) {
						// il nodo ha un figlio destro
						tmp.getPadre().setDestro(tmp.getDestro());
						tmp.getDestro().setPadre(tmp.getPadre());
					} else {
						// il nodo ha figlio sinistro
						tmp.getPadre().setSinistro(tmp.getSinistro());
						tmp.getSinistro().setPadre(tmp.getPadre());
					}
				}
			}
		}
	}

	// NON UTILIZZATO ----------------------------------------------------
	public int getSize() {
		LinkedList<ABRNodo<T>> coda = new LinkedList<ABRNodo<T>>();
		coda.addFirst(this.abr);
		int counter = 0;
		while(!coda.isEmpty()) {
			ABRNodo<T> tmp = coda.getLast();
			if(tmp.getSinistro() != null) {
				coda.addFirst(tmp.getSinistro());
			}
			if(tmp.getDestro() != null) {
				coda.addFirst(tmp.getDestro());
			}
			counter++;
			coda.removeLast();
		}
		return counter;
	}
	// --------------------------------------------------------------------

	public void pre_visit(ABRNodo<T> tmp, LinkedList<T> lista){
		if(tmp != null) {
			if(tmp.getSinistro() != null) {
				pre_visit(tmp.getSinistro(), lista);
			}
			lista.addLast(tmp.getValue());
			if(tmp.getDestro() != null) {
				pre_visit(tmp.getDestro(), lista);
			}
		}
	}
	
	public LinkedList<T> convertList() {
		ABRNodo<T> tmp = new ABRNodo<T>();
		tmp = this.abr;
		LinkedList<T> lista = new LinkedList<T>();
		pre_visit(tmp, lista);
		return lista;
	}
}
