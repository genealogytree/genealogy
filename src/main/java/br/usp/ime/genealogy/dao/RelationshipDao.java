package br.usp.ime.genealogy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Relationship;
import br.usp.ime.genealogy.entity.Tree;

import com.google.inject.Inject;

public class RelationshipDao {

	private Session session;
	
	@Inject
	public RelationshipDao(Session session) {
		this.session = session;
	}
	
	public void saveRelationship(Relationship rel) {
		this.session.saveOrUpdate(rel);	
	}
	
	@SuppressWarnings("unchecked")
	public List<Relationship> listAll() {
		return session.createCriteria(Relationship.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public Person getParent(Person person, char type) {
		for (Relationship relation : person.getRelationships2()) {
			if (relation.getType() == type)
				return relation.getPerson1();
		}
	
		return null; 
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Person> getChildren(Person person) {
		ArrayList<Person> children = new ArrayList<Person>();
		for (Relationship relation : person.getRelationships1()) {
			if (relation.getType() == 'F' ||
					relation.getType() == 'M' )
				children.add(relation.getPerson2());
		}	
		return children; 
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
