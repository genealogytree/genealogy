package br.usp.ime.genealogy.entity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
public class Person {
	
	@Id @GeneratedValue
	private Long id;
	
	@OneToMany(mappedBy="information")
	private Set<PersonInformation> personInfos;
	
	@ManyToMany(mappedBy="persons")		
	private Set<PersonName> names;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
	//	this.names = new HashSet<PersonName>();
	}
	
}
