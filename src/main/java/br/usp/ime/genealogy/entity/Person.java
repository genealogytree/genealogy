package br.usp.ime.genealogy.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;


@Entity
public class Person {
	
	@Id @GeneratedValue
	private Long id;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
