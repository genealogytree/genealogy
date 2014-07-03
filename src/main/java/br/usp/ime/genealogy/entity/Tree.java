package br.usp.ime.genealogy.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Tree {
	@Id @GeneratedValue   
	private long id;
	private String title; 
	
	@OneToOne(cascade=CascadeType.ALL)  
    @JoinColumn
    private Person rootPerson;
	

	@ManyToMany
	@JoinTable(name="tree_user", 
    	joinColumns={@JoinColumn(name="tree_id")}, 
    	inverseJoinColumns={@JoinColumn(name="user_id")})
	private Set<User> users;
	
	@OneToMany(mappedBy="tree",cascade=CascadeType.ALL) 	
	private Set<Person> persons;
	 
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Person getRootPerson() {
		return rootPerson;
	}
	public void setRootPerson(Person rootPerson) {
		this.rootPerson = rootPerson;
	}
	
}
