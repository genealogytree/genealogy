package br.usp.ime.genealogy.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Name {
	@Id @GeneratedValue   
	private long id;
	
	private String name;
	
	@OneToMany(mappedBy="name1")	
	private Set<NameMatch> matches1;
	
	@OneToMany(mappedBy="name2")	
	private Set<NameMatch> matches2;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<NameMatch> getMatches1() {
		return matches1;
	}
	public void setMatches1(Set<NameMatch> matches1) {
		this.matches1 = matches1;
	}
	public Set<NameMatch> getMatches2() {
		return matches2;
	}
	public void setMatches2(Set<NameMatch> matches2) {
		this.matches2 = matches2;
	}
}
