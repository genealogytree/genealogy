package br.usp.ime.genealogy.dao;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.usp.ime.genealogy.entity.Name;


/*Teste ainda não funciona, pois não sabemos fazer testes com querys*/
public class NameMatchDaoTest {
		
	Name name;
	NameDao nameDao;
	NameMatchDao nameMatchDao;
	
	private @Mock Session session;
	private @Mock Transaction tx;
	private @Mock Criteria criteria;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(session.beginTransaction()).thenReturn(tx);
		nameMatchDao = new NameMatchDao(session);
		nameDao = new NameDao(session);
		
		name = new Name();
		name.setName("Adriano");
		nameDao.save(name);		
		name.setName("Adrian");
		nameDao.save(name);		
		name.setName("Luciana");
		nameDao.save(name);		
		name.setName("Juciana");
		nameDao.save(name);		
		name.setName("Diogo");
		nameDao.save(name);		
		
		
		when(nameDao.getByName("Diogo")).thenReturn(null);		
		
		//when(session.createCriteria(NameMatch.class)).thenReturn(criteria);
		//when(criteria.list()).thenReturn(people);
		//when(session.load(Person.class,1L)).thenReturn(person1);
		//when(session.load(Person.class,0L)).thenReturn(null);
		
	}
	
	@Test
	public void save_insertion() {
		//nameMatchDao.save(name);
		//verify(this.session).save(name);
	}
	/*
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
		Assert.assertNotNull(p);
		Assert.assertEquals(person1, p);
	}
	
	@Test
	public void get_not_existing_person() {
		Person p = personDao.get(0L);
		Assert.assertNull(p);
	}
	*/
	
	@Test
	public void getNotComparedNames() {
		
	}
}
