package br.usp.ime.genealogy.entity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Person {
	
	public Person () {
		this.personInfos = new HashSet<PersonInformation>();
		this.id = (long) 0;
	}
	
	@Id @GeneratedValue
	private Long id;
	
	@OneToMany(mappedBy="person")	
	private Set<PersonInformation> personInfos;

	
	@OneToMany(mappedBy="person")	
	private List<PersonName> names;
	
	@ManyToOne
	@JoinColumn(name="tree_id")
	private Tree tree;
	
	
	
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
	

	
	public List<PersonName> getNames() {
		return names;
	}

	public void setNames(List<PersonName> names) {
		this.names = names;
	}

	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName(){
		if (this.names == null) {
			return "";
		}
		
		String name = "";
		for (PersonName n : this.names) {
			name += n.getName().getName() + " ";
		}
		return name.trim();
	}
	
	public void setName(String name) {		
		String[] newNamesContent = name.split(" ");	
		
		if (this.names == null) {
			this.names = new ArrayList<PersonName>();
		}
		
		int order_currentName = 0;
		for (int i = 0; i < newNamesContent.length; i++) {
			if(newNamesContent[i] == "" || newNamesContent[i] == " ") 
				continue;
			else 
				order_currentName++;
			
			PersonName personName;
			Name newName = new Name();
			
			personName = new PersonName();
			personName.setId(0L);
			
			newName.setId(0L);			
			newName.setName(newNamesContent[i]);
			
			personName.setName(newName);
			personName.setOrder(order_currentName);
			if(this.names.size() < order_currentName)
				this.names.add(personName);
			else
			
				this.names.set(order_currentName-1, personName);
		}
		
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
	
	public String getSex() {
		System.out.println("SEXO?");
		for (Iterator<PersonInformation> i = this.personInfos.iterator(); i.hasNext();) {
			PersonInformation info = (PersonInformation) i.next();
			System.out.println(info.getType().getType());
			if(info.getType().getType().equalsIgnoreCase("sex")) {
				return info.getDescription();
			}
		}
		return null;
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Person))
            return false;
		Person person = (Person) obj;
		return this.getId() == person.getId(); 
	}

	
}
