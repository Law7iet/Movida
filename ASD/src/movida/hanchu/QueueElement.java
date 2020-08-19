package movida.hanchu;

import movida.commons.Person;

public class QueueElement {
	protected Person key;
	protected Double value;
	
	public QueueElement(Person key, Double value) {
		this.key = key;
		this.value = value;
	}
	
	public Person getPerson() {
		return this.key;
	}
	
	public Double getValue() {
		return this.value;
	}
}
