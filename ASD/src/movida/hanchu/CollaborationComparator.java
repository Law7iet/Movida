package movida.hanchu;

import java.util.Comparator;

import movida.commons.Collaboration;

public class CollaborationComparator implements Comparator<Collaboration> {
	public int compare(Collaboration actorA, Collaboration actorB) {
		if(actorA.getScore() > actorB.getScore()) {
			return -1;
		} else {
			if(actorA.getScore() < actorB.getScore()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}
