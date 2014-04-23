package br.usp.ime.genealogy.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Entity;

@Entity
public class Relationship {
	
	public Relationship() {
		
	}
	
	private Person person1;
	private Person person2;
	private char type;
	private MarriageInformation relation = null;
	
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
