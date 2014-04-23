package br.usp.ime.genealogy.dao;

import java.util.List;

import org.hibernate.Session;

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
}
