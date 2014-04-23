package br.usp.ime.genealogy.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.Entity;

@Entity
public class Relationship {

	/*
	public Relationship() {
		
	}

	public Relationship(Person p1, Person p2, char type){
		this.person1 = p1;
		//this.person2 = p2;
		this.type = type;
	}
	
	public Relationship(Person p1, Person p2, char type, MarriageInformation marriage){
		this.person1 = p1;
		//this.person2 = p2;
		this.type = type;
		this.relation = marriage;
	}
	*/
	
	@Id @GeneratedValue
	private Long id;
	
	/*
	@ManyToOne
	@JoinColumn(name="person1_id")
	private Person person1;
	
	@ManyToOne
	@JoinColumn(name="person2_id")
	private Person person2;
	
		public Person getPerson1() {
		return person1;
	}
	public void setPerson1(Person person1) {
		this.person1 = person1;
	}
	


	public Person getPerson2() {
		return person2;
	}
	public void setPerson2(Person person2) {
		this.person2 = person2;
	}
	
	*/
	
	private char type;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private MarriageInformation relation = null;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	
	public MarriageInformation getRelation() {
		return relation;
	}
	public void setRelation(MarriageInformation relation) {
		this.relation = relation;
	}

	
}
