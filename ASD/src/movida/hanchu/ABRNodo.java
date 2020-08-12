package movida.hanchu;

public class ABRNodo<V> {
	protected V value;
	protected ABRNodo<V> parent;
	protected ABRNodo<V> left;
	protected ABRNodo<V> right;
	
	public ABRNodo(V value, ABRNodo<V> parent) {
		this.value = value;
		this.parent = parent;
		this.left = null;
		this.right = null;
	}
}
