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
					// si aggiunge la nuova collaborazione a 'value'
					Collaboration tmp = new Collaboration(actorA, actorB);
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
	
	// 
	public Collaboration[] maximunSpanningTree(Person root) {
		ArrayList<Collaboration> array = new ArrayList<Collaboration>();
		HashMap<String, Double> distance = new HashMap<String, Double>();
		Comparator<QueueElement> comparator = new CollaborationComparator();
		PriorityQueue<QueueElement> queue = new PriorityQueue<QueueElement>(comparator);
		distance.put(root.getName(), 0.0);
		queue.add(new QueueElement(root, distance.get(root.getName())));
		while(!queue.isEmpty()) {
			Person actorA = queue.poll().getPerson();
			for(Collaboration tmp : this.grafo.get(actorA.getName())) {
				Person actorB = tmp.getActorB();
				if(distance.containsKey(actorB.getName()) == false) {
					distance.put(actorB.getName(), tmp.getScore());
					queue.add(new QueueElement(actorB, distance.get(actorB.getName())));
					array.add(tmp);
				} else {
					if(tmp.getScore() > distance.get(actorB.getName())) {
						queue.
						QueueElement x = new QueueElement(actorB, distance.get(actorB.getName()));
						queue.remove(x);
						queue.add(new QueueElement(actorB, tmp.getScore()));
						distance.put(actorB.getName(), tmp.getScore());
						for(Collaboration element : array) {
							if(element.getActorB() == actorB) {
								array.remove(element);
								array.add(tmp);
							}
						}
					}
				}
			}
			
		}
		return array.toArray(new Collaboration[array.size()]);
	}
}
