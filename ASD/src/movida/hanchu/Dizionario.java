package movida.hanchu;

import java.util.LinkedList;

public interface Dizionario <V>{
	// Cerca l'oggetto 'value'
	public V search(V value);
	// Inserisce l'oggetto 'value', se non è presente
	public void insert(V value);
	// Cancella l'oggetto 'value', se è presente
	public void delete(V value);
	// Ritorna il numero di oggetti presenti nel dizionario
	public int getSize();
	// Converte il dizionario in una LinkedList
	// Utilizzata per convertire i dati in un array
	public LinkedList<V> convertToList();
	// Converte un array in un dizionario
	public void convertToDizionario(V[] values);
}
