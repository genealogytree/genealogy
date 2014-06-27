package br.usp.ime.genealogy.dao;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		relationshipDao.save(father_mother);
		verify(this.session).saveOrUpdate(father_mother);
	}
	
	@Test
	public void getParent() {
		Person son = new Person();
		Person father = new Person();
		Person mother = new Person();
		
		son.setId(1L);
		father.setId(2L);
		mother.setId(3L);
		
		Relationship fatherRelation = new Relationship();
		fatherRelation.setPerson1(father);
		fatherRelation.setPerson2(son);
		fatherRelation.setType('F');
		
		
		Relationship motherRelation = new Relationship();
		motherRelation.setPerson1(mother);
		motherRelation.setPerson2(son);
		motherRelation.setType('M');
		
		Set<Relationship> relations = new HashSet<Relationship>();
		relations.add(fatherRelation);
		relations.add(motherRelation);
		
		son.setRelationships2(relations);
		
		Person person = this.relationshipDao.getParent(son, 'F');		
		assertEquals(person.getId(), father.getId());
		
		person = this.relationshipDao.getParent(son, 'M');
		assertEquals(person.getId(), mother.getId());
	}
	
	@Test
	public void getChildren() {
		Person son1 = new Person();
		Person son2 = new Person();
		Person father = new Person();
		
		son1.setId(1L);
		son2.setId(2L);
		father.setId(3L);
		
		Relationship father1Relation = new Relationship();
		father1Relation.setPerson1(father);
		father1Relation.setPerson2(son1);
		father1Relation.setType('F');
		
		Relationship father2Relation = new Relationship();
		father2Relation.setPerson1(father);
		father2Relation.setPerson2(son2);
		father2Relation.setType('F');		

		
		Set<Relationship> relations = new HashSet<Relationship>();
		relations.add(father1Relation);
		relations.add(father2Relation);
		
		father.setRelationships1(relations);
		
		ArrayList<Person> children = relationshipDao.getChildren(father);
		
		assertEquals(2, children.size());		
	}
	
	@Test
	public void getSpouses() {
		Person husb = new Person();
		Person wife1 = new Person();
		Person wife2 = new Person();
		
		Relationship spouse1Relation = new Relationship();
		spouse1Relation.setPerson1(husb);
		spouse1Relation.setPerson2(wife1);
		spouse1Relation.setType('S');
		
		Relationship spouse2Relation = new Relationship();
		spouse2Relation.setPerson1(wife2);
		spouse2Relation.setPerson2(husb);
		spouse2Relation.setType('S');
		
		Set<Relationship> relations1 = new HashSet<Relationship>();
		relations1.add(spouse1Relation);
		
		Set<Relationship> relations2 = new HashSet<Relationship>();
		relations2.add(spouse2Relation);
		
		husb.setRelationships1(relations1);
		husb.setRelationships2(relations2);
		
		
		ArrayList<Person> spouses = relationshipDao.getSpouses(husb);
		
		assertEquals(2, spouses.size());
	}
	
	@Test
	public void getStepChildren() {
		Person son1 = new Person();
		Person son2 = new Person();
		Person father = new Person();
		Person mother = new Person();
		
		son1.setId(1L);
		son2.setId(2L);
		father.setId(3L);
		mother.setId(4L);
		
		Relationship father1Relation = new Relationship();
		father1Relation.setPerson1(father);
		father1Relation.setPerson2(son1);
		father1Relation.setType('F');
		
		Relationship father2Relation = new Relationship();
		father2Relation.setPerson1(father);
		father2Relation.setPerson2(son2);
		father2Relation.setType('F');	
		
		Relationship motherRelation = new Relationship();
		motherRelation.setPerson1(mother);
		motherRelation.setPerson2(son1);
		motherRelation.setType('F');

		Relationship spouseRelation = new Relationship();
		spouseRelation.setPerson1(father);
		spouseRelation.setPerson2(mother);
		spouseRelation.setType('S');		
		
		
		Set<Relationship> relations = new HashSet<Relationship>();
		relations.add(father1Relation);
		relations.add(father2Relation);
		relations.add(spouseRelation);
		father.setRelationships1(relations);
		father.setRelationships2(new HashSet<Relationship>());
		
		relations = new HashSet<Relationship>();
		relations.add(motherRelation);		
		mother.setRelationships1(relations);
		
		relations = new HashSet<Relationship>();
		relations.add(spouseRelation);
		mother.setRelationships2(relations);		
		
		ArrayList<Person> stepChildren = relationshipDao.getStepChildren(mother);
		assertEquals(1, stepChildren.size());
		assertEquals(son2.getId(), stepChildren.get(0).getId());
						
	}
	
}
