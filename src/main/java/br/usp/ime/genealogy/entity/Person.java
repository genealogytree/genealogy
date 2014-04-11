package br.usp.ime.genealogy.entity;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Person {
	
	private String name;	
	
	@Id @GeneratedValue
	private Long id;
	
	@OneToMany(mappedBy="information")
	private Set<PersonInformation> personInfos;

	@ManyToMany(mappedBy="persons")		
	private Set<PersonName> names;
	
	@ManyToOne
	private Tree tree;

	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	//	this.names = new HashSet<PersonName>();
	}
	public String getName(){
		return name;
	
	}
	
	public Set<PersonInformation> getPersonInfos() {
		return personInfos;
	}

	public void setPersonInfos(Set<PersonInformation> personInfos) {
		this.personInfos = personInfos;
	}
	
	
	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}
}
