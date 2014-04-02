package br.usp.ime.genealogy.entity;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Person {
	
	@Id @GeneratedValue
	private Long id;
	
	@OneToMany(mappedBy="person")
	private Set<PersonInformation> personInfos; 
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
