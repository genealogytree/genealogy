package br.usp.ime.genealogy.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
public class Tree {
	@Id @GeneratedValue   
	private long id;
	private String title; 
	
	/*
	@ManyToMany
	@JoinTable(name="tree_user", 
    joinColumns={@JoinColumn(name="tree_id")}, 
    inverseJoinColumns={@JoinColumn(name="user_id")})
	private Set<User> users;
	
	 @ManyToMany
	 @JoinTable(name="tree_person", 
	            joinColumns={@JoinColumn(name="tree_id")}, 
	            inverseJoinColumns={@JoinColumn(name="person_id")})	
	private Set<Person> persons;
	 */
	
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
	
}
