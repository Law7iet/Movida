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
	protected HashMap<String, ArrayList<Collaboration>> grafo;
	
	public Grafo() {
		this.grafo = new HashMap<String, ArrayList<Collaboration>>();
	}
	
	public void addMovieCollaboration(Movie movie) {
		for(Person actorA : movie.getCast()) {
			String key = actorA.getName();
			ArrayList<Collaboration> value = new ArrayList<Collaboration>();
			if(this.grafo.containsKey(key) == true) {
				value = this.grafo.get(key);
			}
			for(Person actorB : movie.getCast()) {
				if(actorA != actorB) {
					Collaboration tmp = new Collaboration(actorA, actorB);
					tmp.addMovie(movie);
					value.add(tmp);
				}
			}
			this.grafo.put(key, value);
		}
	}
	
	public void removeMovieCollaboration(Movie movie) {
		for(Person actorA : movie.getCast()) {
			for(Person actorB : movie.getCast()) {
				if(actorA != actorB) {
					ArrayList<Collaboration> list = this.grafo.get(actorA.getName());
					int index = 0;
					while(index < list.size()) {
						Collaboration tmp = list.get(index);
						if(tmp.getActorA() == actorA && tmp.getActorB() == actorB) {
							tmp.removeMovie(movie);
						}
						if(tmp.getMovies().length == 0) {
							list.remove(tmp);
						} else {
							index++;
						}
					}
				}
			}
		}
	}
	
	public Person[] getDirect(Person actorA) {
		ArrayList<Collaboration> collaborations = this.grafo.get(actorA.getName());
		ArrayList<Person> actorsB = new ArrayList<Person>();
		Person[] array = new Person[collaborations.size()];
		for(Collaboration element : collaborations) {
			System.out.println(element.getActorB().getName() + " " + element.getScore());
			actorsB.add(element.getActorB());
		}
		return actorsB.toArray(array);
	}
	
	public Person[] getIndirect(Person root) {
		HashSet<String> marker = new HashSet<String>();
		ArrayList<Person> team = new ArrayList<Person>();
		ArrayDeque<Person> queue = new ArrayDeque<Person>();
		queue.add(root);
		while(!queue.isEmpty()) {
			Person actorA = queue.pollFirst();
			marker.add(actorA.getName());
			ArrayList<Collaboration> collaborations = this.grafo.get(actorA.getName());
			for(Collaboration element : collaborations) {
				Person actorB = element.getActorB();
				if(marker.contains(actorB.getName()) == false) {
					marker.add(actorB.getName());
					team.add(actorB);
					queue.add(actorB);
				}
			}
		}
		Person[] tmp = new Person[team.size()];
		return team.toArray(tmp);
	}
	
	
	public Collaboration[] maximunSpanningTree(Person root) {
		ArrayList<Collaboration> array = new ArrayList<Collaboration>();
		HashMap<String, Double> distance = new HashMap<String, Double>();
		Comparator<Collaboration> comparator = new CollaborationComparator();
		PriorityQueue<Collaboration> queue = new PriorityQueue<Collaboration>(comparator);
		distance.put(root.getName(), 0.0);
		for(Collaboration tmp : this.grafo.get(root.getName())) {
			queue.add(tmp);
		}
		while(!queue.isEmpty()) {
			Collaboration arco = queue.poll();
			Person actorB = arco.getActorB();
			if(distance.containsKey(actorB.getName()) == false) {
				array.add(arco);
				distance.put(actorB.getName(), arco.getScore());
				for(Collaboration tmp : this.grafo.get(actorB.getName())) {
					queue.add(tmp);
				}
			} else {
				if(distance.containsKey(actorB.getName()) == true) {
					if(arco.getScore() > distance.get(actorB.getName())) {
						queue.add(arco);
						distance.put(actorB.getName(), arco.getScore());
						array.remove(arco);
						
					}
				}
			}
		}
		return array.toArray(new Collaboration[array.size()]);
	}
}
