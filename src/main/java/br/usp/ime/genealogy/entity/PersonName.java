package br.usp.ime.genealogy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PersonName {
	
	@Id @GeneratedValue
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@ManyToOne
	@JoinColumn(name="person_id")
	private Person person;
	
	@ManyToOne
	@JoinColumn(name="name_id")
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