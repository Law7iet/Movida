package movida.hanchu;

public interface Dizionario <T>{
	public T search(T value);
	public void insert(T value);
	public void delete(T value);
}
