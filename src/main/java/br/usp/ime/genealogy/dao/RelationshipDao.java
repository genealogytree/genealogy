package br.usp.ime.genealogy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Relationship;

import com.google.inject.Inject;

public class RelationshipDao {

	private Session session;
	
	@Inject
	public RelationshipDao(Session session) {
		this.session = session;
	}
	
	public void save(Relationship rel) {
		//if(RelationType.isType(rel.getType()))
		this.session.saveOrUpdate(rel);	
	}
	
	@SuppressWarnings("unchecked")
	public List<Relationship> listAll() {
		return session.createCriteria(Relationship.class).list();
	}
	
	public Person getParent(Person person, char type) {
		for (Relationship relation : person.getRelationships2()) {
			if (relation.getType() == type)
				return relation.getPerson1();
		}
	
		return null; 
	}

	public ArrayList<Person> getChildren(Person person) {
		ArrayList<Person> children = new ArrayList<Person>();
		for (Relationship relation : person.getRelationships1()) {
			if (relation.getType() == 'F' ||
					relation.getType() == 'M' )
				children.add(relation.getPerson2());
		}	
		return children; 
	}
	
	public ArrayList<Person> getStepChildren(Person person) {
		ArrayList<Person> stepChildren = new ArrayList<Person>();
		ArrayList<Person> children = this.getChildren(person);
		ArrayList<Person> spouses = this.getSpouses(person);
		for (Person spouse : spouses) {
			ArrayList<Person> spouseChildren = this.getChildren(spouse);
			for (Person spouseChild : spouseChildren) {
				if(children.contains(spouseChild) == false)
					stepChildren.add(spouseChild);
			}
		}		
		return stepChildren;
	}
	
	public ArrayList<Person> getSpouses(Person person) {
		ArrayList<Person> spouses = new ArrayList<Person>();
		for (Relationship relation : person.getRelationships1()) {
			if (relation.getType() == 'S')
				spouses.add(relation.getPerson2());
		}
		for (Relationship relation : person.getRelationships2()) {
			if (relation.getType() == 'S')
				spouses.add(relation.getPerson1());
		}	
		return spouses; 
	}
}
