package br.usp.ime.genealogy.dao;

import static org.junit.Assert.*;
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
import br.usp.ime.genealogy.entity.Tree;

@SuppressWarnings("deprecation")
public class PersonDaoTest {
	
	PersonDao personDao;
	List<Person> people;
	Person person1;
	Person person2;
	
	private @Mock Session session;
	private @Mock Transaction tx;
	private @Mock Criteria criteria;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(session.beginTransaction()).thenReturn(tx);
		personDao = new PersonDao(session);
		
		person1 = new Person();
		person1.setId(1L);
		person1.setName("teste1");
		person2 = new Person();
		person2.setId(0L);
		person2.setName("teste2");
		people = new ArrayList<Person>();
		people.add(person1);
		people.add(person2);
		
		when(session.createCriteria(Person.class)).thenReturn(criteria);
		when(criteria.list()).thenReturn(people);
		when(session.load(Person.class,1L)).thenReturn(person1);
		when(session.load(Person.class,0L)).thenReturn(null);
	}
	
	@Test
	public void save_insertion() {
		personDao.save(person2);
		verify(this.session).save(person2);
	}
	
	@Test
	public void save_update() {
		person2.setId(2L);
		person2.setName("verify");
		personDao.save(person2);
		verify(this.session).update(person2);
	}

	@Test
	public void get_existing_person() {
		Person p = personDao.get(person1.getId());
		assertNotNull(p);
		assertEquals(person1, p);
	}
	
	@Test
	public void get_not_existing_person() {
		Person p = personDao.get(0L);
		assertNull(p);
	}

	@Test
	public void lisAll() {	
		ArrayList<Person> plist = personDao.listAll();
		assertEquals(people, plist);
		verify(session).createCriteria(Person.class);
		verify(criteria).list();
	}
	
	@Test
	public void getByTree() {
		TreeDao treeDao = new TreeDao(session);
		Tree tree = new Tree();
		tree.setId(0L);
		tree.setTitle("Teste");
		treeDao.save(tree);
		
		person1.setTree(tree);
		person2.setTree(tree);
		
		ArrayList<Person> plist = personDao.getByTree(tree);
		assertEquals(2, plist.size());
		for( Person p : plist ) {
			if (p.getId() != person1.getId() || p.getId() != person2.getId())
				assertTrue(false);
		}
	}
}
