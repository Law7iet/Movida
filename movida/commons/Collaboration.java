package movida.commons;

import java.util.ArrayList;

public class Collaboration {

	Person actorA;
	Person actorB;
	ArrayList<Movie> movies;

	public Collaboration(Person actorA, Person actorB) {
		this.actorA = actorA;
		this.actorB = actorB;
		this.movies = new ArrayList<Movie>();
	}
	public Person getActorA() {
		return actorA;
	}

	public Person getActorB() {
		return actorB;
	}

	public Movie[] getMovies() {
		Movie[] tmp = new Movie[movies.size()];
		return movies.toArray(tmp);
	}

	public Double getScore(){

		Double score = 0.0;

		for (Movie m : movies)
			score += m.getVotes();

		return score / movies.size();
	}

	public void addMovie(Movie movie) {
		if(this.movies.contains(movie) == false) {
			this.movies.add(movie);
		}
	}

	public void removeMovie(Movie movie) {
		if(this.movies.contains(movie) == true) {
			this.movies.remove(movie);
		}
	}
}
