package br.usp.ime.genealogy.entity;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

public class PersonName {
	
	@ManyToOne
	@Column(name="person_id")
	private Person person;
	
	@ManyToOne
	@Column(name="name_id")
	private Name name;
	
	private int order;
	
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}

	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}

}
