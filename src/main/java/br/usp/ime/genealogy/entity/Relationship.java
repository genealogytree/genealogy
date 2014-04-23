package br.usp.ime.genealogy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
public class Relationship {

	@Id @GeneratedValue
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name="person1_id")
	private Person person1;
	
	public Person getPerson1() {
		return person1;
	}
	public void setPerson1(Person person1) {
		this.person1 = person1;
	}	
	
	
	
	@ManyToOne
	@JoinColumn(name="person2_id")
	private Person person2;
	 


	public Person getPerson2() {
		return person2;
	}
	public void setPerson2(Person person2) {
		this.person2 = person2;
	}
	
	
	@Column(name="tipo")
	private char type;
	
	
	/*
	@OneToOne
	@PrimaryKeyJoinColumn
	private MarriageInformation relation = null;
	*/
	
	
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
	
	/*
	public MarriageInformation getRelation() {
		return relation;
	}
	public void setRelation(MarriageInformation relation) {
		this.relation = relation;
	}
	*/
	

	
}
