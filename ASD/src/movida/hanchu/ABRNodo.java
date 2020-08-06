package movida.hanchu;

public class ABRNodo<T> {
	protected T value;
	protected ABRNodo<T> padre;
	protected ABRNodo<T> sinistro;
	protected ABRNodo<T> destro;
	
	public ABRNodo() {
		this.value = null;
		this.padre = null;
		this.sinistro = null;
		this.destro = null;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public void setPadre(ABRNodo<T> padre) {
		this.padre = padre;
	}
	
	public void setSinistro(ABRNodo<T> sinistro) {
		this.sinistro = sinistro;
	}
	
	public void setDestro(ABRNodo<T> destro) {
		this.destro = destro;
	}
	
	public T getValue() {
		return this.value;
	}
	
	public ABRNodo<T> getPadre() {
		return this.padre;
	}
	
	public ABRNodo<T> getSinistro() {
		return this.sinistro;
	}
	
	public ABRNodo<T> getDestro() {
		return this.destro;
	}
}
