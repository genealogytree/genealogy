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
	
	@OneToMany(mappedBy="name")
	private Set<PersonName> personsNames;
	
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
}
