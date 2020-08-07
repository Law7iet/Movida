package movida.hanchu;

import java.util.LinkedList;

public interface Dizionario <T>{
	public T search(T value);
	public void insert(T value);
	public void delete(T value);
	public int getSize();
	public LinkedList<T> convertList();
}
