package br.usp.ime.genealogy.entity;

import org.hibernate.annotations.Entity;

@Entity
public class Relationship {

	//??? Falta arrumar com as anotações do hibernate 
	public Relationship(Person p1, Person p2, char grau){
		this.person1 = p1;
		this.person2 = p2;
		this.grau = grau;
	}
	
	public Relationship(Person p1, Person p2, char grau, MarriageInformation marriage){
		this.person1 = p1;
		this.person2 = p2;
		this.grau = grau;
		this.relation = marriage;
	}
	
	
	private Person person1;
	private Person person2;
	private char grau;
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
	
	public char getGrau() {
		return grau;
	}
	public void setGrau(char grau) {
		this.grau = grau;
	}
	
	public MarriageInformation getRelation() {
		return relation;
	}
	public void setRelation(MarriageInformation relation) {
		this.relation = relation;
	}

	
}
