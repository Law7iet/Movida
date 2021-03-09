package movida.hanchu;

import java.util.Comparator;

public class CollaborationComparator implements Comparator<QueueElement> {
	public int compare(QueueElement o1, QueueElement o2) {
		if(o1.getValue() > o2.getValue()) {
			return -1;
		}
		if(o1.getValue() < o2.getValue()) {
			return 1;
		}
		return 0;
	}

}
