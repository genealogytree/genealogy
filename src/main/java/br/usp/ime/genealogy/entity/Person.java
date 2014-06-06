package br.usp.ime.genealogy.entity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;




@Entity
public class Person {
	
	public Person () {
		this.personInfos = new HashSet<PersonInformation>();
		this.id = (long) 0;
	}
	
	@Id @GeneratedValue
	private Long id;
	
	@OneToMany(mappedBy="person")
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
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
		String[] names = name.split(" ");		
		
		if (this.names == null) {
			this.names = new ArrayList<PersonName>();
		}
		
		int j = 1;
		int k = this.names.size();
		for (int i = 0; i < names.length; i++) {
			if(names[i] == "" || names[i] == " ") 
				continue;
			
			PersonName personName;
			Name n;
			
			System.out.println("I:" + i);
			if (j <= k) {
				personName = this.names.get(j-1);
				n  = personName.getName();
				System.out.println("aqui");				
			}
			else {
				personName = new PersonName();				
				personName.setId(0L);	
				
				n = new Name();
				n.setId(0L);
				
				System.out.println("Ali");
			}
			
			System.out.println("Name!: " + names[i]);
			
			 
			
			n.setName(names[i]);
			
			personName.setName(n);
			personName.setOrder(j);
			if(this.names.size() < j)
				this.names.add(personName);
			else
				this.names.set(j-1, personName);
			
			j = j + 1;
		}
		
		for (int i=0; i < this.names.size(); i++) {
			System.out.println("Nome --- " + this.names.get(i).getName().getName());
		}
		
		System.out.println("J: " + j);
		System.out.println("Size: " + this.names.size());
		
		if (j-1 < k) {
			for (int i=j-1; i < k; i++) {
				this.names.remove(j-1);
				
				System.out.println("removendo ai=" + i);
			}
		}
		
		for (int i=0; i < this.names.size(); i++) {
			System.out.println("Nome -- " + this.names.get(i).getName().getName());
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
		for (Iterator<PersonInformation> i = this.personInfos.iterator(); i.hasNext();) {
			PersonInformation info = (PersonInformation) i.next();
			if(info.getType().getType().equalsIgnoreCase("sex")) {
				return info.getDescription();
			}
		}
		return null;
	}
	
	
	
}
