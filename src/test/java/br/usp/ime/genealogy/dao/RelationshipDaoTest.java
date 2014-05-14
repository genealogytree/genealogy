package br.usp.ime.genealogy.dao;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Relationship;
import br.usp.ime.genealogy.util.RelationType;

@SuppressWarnings("deprecation")
public class RelationshipDaoTest {

	RelationshipDao relationshipDao;
		
	Person child;
	Person father;
	Person mother;
	
	Relationship father_mother;
	Relationship father_child;
	Relationship mother_child;
	List<Relationship> relationships;
	
	private @Mock Session session;
	private @Mock Transaction tx;
	private @Mock Criteria criteria;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(session.beginTransaction()).thenReturn(tx);
		relationshipDao = new RelationshipDao(session);
		
		child = new Person();
		child.setId(0L);
		mother = new Person();
		mother.setId(0L);
		father = new Person();
		father.setId(0L);
		
		father_mother = new Relationship();
		father_mother.setId(0L);
		father_mother.setPerson1(father);
		father_mother.setPerson2(mother);
		father_mother.setType(RelationType.SPOUSE.toChar());
		
		father_child = new Relationship();
		father_child.setId(0L);
		father_child.setPerson1(father);
		father_child.setPerson2(child);
		father_child.setType(RelationType.FATHER.toChar());
		
		mother_child = new Relationship();
		mother_child.setId(0L);
		mother_child.setPerson1(mother);
		mother_child.setPerson2(child);
		mother_child.setType(RelationType.MOTHER.toChar());
		
		relationships = new ArrayList<Relationship>();
		relationships.add(father_mother);
		relationships.add(father_child);
		relationships.add(mother_child);
		
		when(session.createCriteria(Relationship.class)).thenReturn(criteria);
		when(criteria.list()).thenReturn(relationships);
	}
	
	@Test
	public void listAll() {
		List<Relationship> rels = relationshipDao.listAll();
		Assert.assertEquals(relationships, rels);
		verify(session).createCriteria(Relationship.class);
		verify(criteria).list();
	}
	

	@Test
	public void save() {
		relationshipDao.saveRelationship(father_mother);
		verify(this.session).saveOrUpdate(father_mother);
	}
	
}
