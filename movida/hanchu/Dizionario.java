package movida.hanchu;

import java.util.LinkedList;

public interface Dizionario <V> {
	// Cerca l'oggetto 'value'
	public V search(String type, V value) throws MovidaCompareException;
	// Inserisce l'oggetto 'value', se non è presente
	public void insert(String type, V value) throws MovidaCompareException;
	// Cancella l'oggetto 'value', se è presente
	public void delete(String type, V value) throws MovidaCompareException;
	// Ritorna il numero di oggetti presenti nel dizionario
	public int getSize();
	// Converte il dizionario in una LinkedList
	// Utilizzata per convertire i dati in un array
	public LinkedList<V> convertToList();
}
