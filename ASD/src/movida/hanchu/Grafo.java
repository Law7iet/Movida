package movida.hanchu;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import movida.commons.Collaboration;
import movida.commons.Movie;
import movida.commons.Person;

public class Grafo {
	// il grafo è composto da un HashMap che ha come chiave una stringa e valore un array di collaborazioni
	// la chiave è il nome dell'attore
	// il valore è la lista di collaborazioni dell'attore il quale nome è la chiave
	// nonostante il grafo sia non orientato, la sua implementazione è orientata
	protected HashMap<String, ArrayList<Collaboration>> grafo;
	
	// il costruttore
	public Grafo() {
		this.grafo = new HashMap<String, ArrayList<Collaboration>>();
	}
	
	// aggiunge tutte le collaborazioni di un film
	public void addMovieCollaboration(Movie movie) {
		// per ogni attore del film
		for(Person actorA : movie.getCast()) {
			String key = actorA.getName();
			ArrayList<Collaboration> value = new ArrayList<Collaboration>();
			// si cerca se esiste già nel grafo l'attore (come collaborazione)
			if(this.grafo.containsKey(key) == true) {
				// si aggiunge a 'value' le collaborazioni precedenti
				value = this.grafo.get(key);
			}
			// per ogni attore del film
			for(Person actorB : movie.getCast()) {
				// si considera una coppia di attori
				// la coppia non è composta da due attori diversi
				if(actorA != actorB) {
					// sono diversi
					Collaboration tmp = new Collaboration(actorA, actorB);
					// cerca la collaborazione fra actorA e actorB fra quelli già esistenti
					for(Collaboration collaboration : value) {
						if(collaboration.getActorA().getName().equals(actorA.getName()) && collaboration.getActorB().getName().equals(actorB.getName())) {
							tmp = collaboration;
						}
					}
					// si aggiunge la nuova collaborazione a 'value'
					tmp.addMovie(movie);
					value.add(tmp);
				}
				
			}
			// si aggiunge la nuova chiave con i nuovi valori oppure si sovrascrivono i valori
			this.grafo.put(key, value);
		}
	}
	
	// cancella le collaborazioni di un film
	public void removeMovieCollaboration(Movie movie) {
		// per ogni attore del film
		for(Person actorA : movie.getCast()) {
			// per ogni attore del film
			for(Person actorB : movie.getCast()) {
				// si considera una coppia di attori
				// la coppia non è composta da due attori diversi
				if(actorA != actorB) {
					// sono diversi
					// si copia la lista di collaborazioni di actorA
					ArrayList<Collaboration> list = this.grafo.get(actorA.getName());
					int index = 0;
					// si intera sulla lista
					while(index < list.size()) {
						Collaboration tmp = list.get(index);
						// si cerca la collaborazione da cancellare
						if(tmp.getActorA() == actorA && tmp.getActorB() == actorB) {
							// si rimuove il film dalla collaborazione
							tmp.removeMovie(movie);
						}
						// se la collaborazione non ha nessun film
						// ovvero che i due attori non hanno mai collaborato
						if(tmp.getMovies().length == 0) {
							// si rimuove la collaborazione
							list.remove(tmp);
						} else {
							// se si elimina la collaborazione non c'è bisogno di muovere l'indice
							index++;
						}
					}
				}
			}
		}
	}
	
	// è la funzione 'getDirectCollaboratorsOf'
	public Person[] getDirect(Person actorA) {
		// prende la lista di collaborazioni che ha come actorA l'input
		ArrayList<Collaboration> collaborations = this.grafo.get(actorA.getName());
		// si crea l'array contenente i collaboratori diretti dell'attore
		ArrayList<Person> actorsB = new ArrayList<Person>();
		// per ogni actorB della lista si aggiunge all'array
		for(Collaboration element : collaborations) {
			actorsB.add(element.getActorB());
		}
		// si converte l'oggetto ArrayList in un array di Person
		Person[] array = actorsB.toArray(new Person [collaborations.size()]);
		return array;
	}
	
	// è la funzione 'getTeamOf'
	// è una BFS
	// i nodi sono le persone
	// gli archi sono le collaborazioni
	public Person[] getIndirect(Person root) {
		// si usa un HashSet che contiene gli attori già visitati
		HashSet<String> marker = new HashSet<String>();
		// si crea l'array contenente gli attori visitati
		ArrayList<Person> team = new ArrayList<Person>();
		// si crea una coda, è una struttura dati ausiliarria
		ArrayDeque<Person> queue = new ArrayDeque<Person>();
		queue.add(root);
		while(!queue.isEmpty()) {
			Person actorA = queue.pollFirst();
			// si marca la persona estratta
			marker.add(actorA.getName());
			// si prende la sua lista di collaborazioni, ossia i suoi archi
			ArrayList<Collaboration> collaborations = this.grafo.get(actorA.getName());
			for(Collaboration element : collaborations) {
				// si prende l'actorB, ossia il nodo opposto
				Person actorB = element.getActorB();
				// si verifica se è marcato/visitato o meno
				if(marker.contains(actorB.getName()) == false) {
					// non è marcato
					// lo si marca
					marker.add(actorB.getName());
					// si aggiunge all'array
					team.add(actorB);
					// si aggiunge alla coda
					queue.add(actorB);
				}
			}
		}
		Person[] tmp = new Person[team.size()];
		return team.toArray(tmp);
	}
	
	// è la funzione 'maximizeCollaborationsInTheTeamOf'
	// il problema è risolvibile tramite un Maximun Spanning Tree
	// i nodi sono le persone
	// gli archi sono le collaborazioni
	public Collaboration[] maximunSpanningTree(Person root) {
		// creazione delle strutture dati ausiliarie:
		// array è il vettore contenente le collaborazioni/archi che rappresentano la soluzione del grafo
		ArrayList<Collaboration> array = new ArrayList<Collaboration>();
		// distance è la struttura che contiene le distanze dal nodo-radice
		HashMap<String, Double> distance = new HashMap<String, Double>();
		// è un comparatore per la coda con priorità
		Comparator<QueueElement> comparator = new CollaborationComparator();
		// il tipo QueueElement è composto da una persona e una chiave che serve per il confronto
		PriorityQueue<QueueElement> queue = new PriorityQueue<QueueElement>(comparator);
		// si pone la radice con distanza massima
		distance.put(root.getName(), Double.MAX_VALUE);
		// si aggiunge la radice alla coda
		queue.add(new QueueElement(root, distance.get(root.getName())));
		
		while(!queue.isEmpty()) {
			// si estrae la prima persona con priorità maggiore
			// la priorità è misurata con 'comparator'
			// esso da priorità a chi ha il valore più alto
			// il valore è dato dal 'getScore' di una collaborazione
			Person actorA = queue.poll().getPerson();
			// si itera sugli archi del nodo
			for(Collaboration tmp : this.grafo.get(actorA.getName())) {
				Person actorB = tmp.getActorB();
				// controlla se 'distance' contiene o meno il nodo d'arrivo
				if(distance.containsKey(actorB.getName()) == false) {
					// se non lo contiene, significa che è nuovo e bisogna aggiungerlo
					distance.put(actorB.getName(), tmp.getScore());
					queue.add(new QueueElement(actorB, distance.get(actorB.getName())));
					array.add(tmp);
				} else {
					// se lo contiene
					// si controlla quale arco ha il valore più grande
					if(tmp.getScore() > distance.get(actorB.getName())) {
						// l'arco nuovo è più grande allora si cambia l'arco per raggiungere il nodo d'arrivo
						queue.add(new QueueElement(actorB, tmp.getScore()));
						distance.put(actorB.getName(), tmp.getScore());
						int index = 0;
						// si cerca l'arco da cambiare nel vettore delle collaborazioni
						while(index < array.size()) {
							if(array.get(index).getActorB().getName().equals(actorB.getName())) {
								// si sostituisce la collaborazione
								array.remove(index);
								array.add(tmp);
							}
							index++;
						}						
					}
				}
			}
		}
		
		return array.toArray(new Collaboration[array.size()]);
	}
}
