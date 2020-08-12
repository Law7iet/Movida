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
}
