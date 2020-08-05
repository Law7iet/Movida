package movida.hanchu;

import movida.commons.Movie;

public class ABR implements Dizionario {
	protected ABRNodo abr;
	
	public ABR() {
		this.abr = new ABRNodo();
	}
	
	public ABRNodo max(ABRNodo tmp) {
		while(tmp != null && tmp.getDestro() != null) {
			tmp = tmp.getDestro();
		}
		return tmp;
	}
	
	public ABRNodo predecessore(ABRNodo tmp) {
		if(tmp == null) {
			return null;
		} else {
			if(tmp.getSinistro() != null) {
				return max(tmp.getSinistro());
			} else {
				ABRNodo prec = tmp.getPadre();
				while(tmp != null && tmp == prec.getSinistro()) {
					tmp = prec;
					prec = prec.getPadre();
				}
				return prec;
			}
		}
	}
	
	public Movie search(Movie film) {
		ABRNodo tmp = this.abr;
		while(tmp != null) {
			if(tmp.getMovie().getTitle().compareTo(film.getTitle()) == 0) {
				// il nodo è il film ricercato
				return (tmp.getMovie());
			} else {
				if(tmp.getMovie().getTitle().compareTo(film.getTitle()) < 0) {
					// il film ricercato è più grande 
					tmp = tmp.destro;
				} else {
					// il film ricercato e più piccolo
					tmp = tmp.sinistro;
				}
			}
		}
		// il film ricercato non è presente
		return null;
	}

	public void insert(Movie film) {
		// cerca il film
		Movie item = search(film);
		if(item == null) {
			// il film non è presente
			ABRNodo tmp = this.abr;
			ABRNodo prec = null;
			// si cerca il ramo giusto per il film
			while(tmp != null) {
				if(tmp.getMovie().getTitle().compareTo(film.getTitle()) < 0) {
					// si sposta sul ramo destro
					prec = tmp;
					tmp = tmp.destro;
				} else {
					// si sposta sul ramo sinistro
					prec = tmp;
					tmp = tmp.sinistro;
				}
			}
			// si crea il nuovo nodo e si aggiunge il film
			tmp = new ABRNodo();
			tmp.setPadre(prec);
			tmp.setMovie(film);
		}
	}

	public void delete(Movie film) {
		// si cerca il film
		Movie item = null;
		item = search(film);
		if(item != null) {
			// il film è presente
			ABRNodo tmp = this.abr;
			ABRNodo prec = null;
			// si cerca il film
			while(tmp.getMovie().getTitle().compareTo(film.getTitle()) == 0) {
				if(tmp.getMovie().getTitle().compareTo(film.getTitle()) < 0) {
					prec = tmp;
					tmp = tmp.destro;
				} else {
					prec = tmp;
					tmp = tmp.sinistro;
				}
			}
			// trovato il film, il nodo tmp
			if(tmp.getDestro() == null && tmp.getSinistro() == null) {
				// il nodo è una foglia
				tmp = null;
			} else {
				if(tmp.getDestro() != null && tmp.getSinistro() != null) {
					// il nodo ha 2 figli 
					// si individua il predecessore
					ABRNodo pred = predecessore(tmp);
					// il padre del preddecessore ha come figlio destro il figlio sinistro del predecessore
					prec.getPadre().setDestro(pred.getSinistro());
					// il nodo da cancellare assume valore del predecessore
					tmp.setMovie(prec.getMovie());
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
}
