package movida.hanchu;

public class ListaNonOrdinataNodo<V> {
	protected V value;
	protected ListaNonOrdinataNodo<V> prec;
	protected ListaNonOrdinataNodo<V> next;
	
	public ListaNonOrdinataNodo() {
		this.value = null;
		this.prec = null;
		this.next = null;
	}
	
	public void setValue(V value) {
		this.value = value;
	}
	
	public void setPrec(ListaNonOrdinataNodo<V> prec) {
		this.prec = prec;
	}
	
	public void setNext(ListaNonOrdinataNodo<V> next) {
		this.next = next;
	}
	
	public V getValue() {
		return this.value;
	}
	
	public ListaNonOrdinataNodo<V> getPrec() {
		return this.prec;
	}
	
	public ListaNonOrdinataNodo<V> getNext() {
		return this.next;
	}
}
