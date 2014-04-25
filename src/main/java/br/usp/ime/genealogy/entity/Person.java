package br.usp.ime.genealogy.entity;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Person {
	
	private String name;	
	
	@Id @GeneratedValue
	private Long id;
	
	private HashMap<String, String> information;
	
	@OneToMany(mappedBy="person")
	private Set<PersonInformation> personInfos;

	
	@OneToMany(mappedBy="person")		
	private Set<PersonName> names;
	
	@ManyToOne
	@JoinColumn(name="tree_id")
	private Tree tree;
	
	public Person () {
		this.personInfos = new HashSet<PersonInformation>();
	}
	
	@OneToMany(mappedBy="person1")
	private Set<Relationship> relationships1;
	public Set<Relationship> getRelationships1() {
		return relationships1;
	}

	public void setRelationships1(Set<Relationship> relationships1) {
		this.relationships1 = relationships1;
	}
	
	@OneToMany(mappedBy="person2")
	private Set<Relationship> relationships2;
	public Set<Relationship> getRelationships2() {
		return relationships2;
	}

	public void setRelationships2(Set<Relationship> relationships2) {
		this.relationships2 = relationships2;
	}	
	

	
	public Set<PersonName> getNames() {
		return names;
	}

	public void setNames(Set<PersonName> names) {
		this.names = names;
	}

	
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
