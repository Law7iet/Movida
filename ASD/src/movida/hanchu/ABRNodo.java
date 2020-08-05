package movida.hanchu;

import movida.commons.*;

public class ABRNodo {
	protected Movie movie;
	protected ABRNodo padre;
	protected ABRNodo sinistro;
	protected ABRNodo destro;
	
	public ABRNodo() {
		this.movie = null;
		this.padre = null;
		this.sinistro = null;
		this.destro = null;
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	public void setPadre(ABRNodo padre) {
		this.padre = padre;
	}
	
	public void setSinistro(ABRNodo sinistro) {
		this.sinistro = sinistro;
	}
	
	public void setDestro(ABRNodo destro) {
		this.destro = destro;
	}
	
	public Movie getMovie() {
		return this.movie;
	}
	
	public ABRNodo getPadre() {
		return this.padre;
	}
	
	public ABRNodo getSinistro() {
		return this.sinistro;
	}
	
	public ABRNodo getDestro() {
		return this.destro;
	}
}
