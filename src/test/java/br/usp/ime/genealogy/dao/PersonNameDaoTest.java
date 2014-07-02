package br.usp.ime.genealogy.dao;

import static org.junit.Assert.assertEquals;
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
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonName;

public class PersonNameDaoTest {

	
	PersonNameDao personNameDao;
	
	Person person;
	PersonName firstName;
	PersonName lastName;
	Name name;
	Name surname;
		
	private @Mock Session session;
	private @Mock Transaction tx;
	private @Mock Criteria criteria;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(session.beginTransaction()).thenReturn(tx);
		personNameDao = new PersonNameDao(session);
		
		person = new Person();
		person.setId(1L);
		
		name = new Name();
		name.setId(1L);
		name.setName("João");
		surname = new Name();
		surname.setId(2L);
		surname.setName("Silva");
		
		firstName = new PersonName();
		firstName.setId(1L);
		firstName.setName(name);
		firstName.setOrder(1);
		firstName.setPerson(person);
		lastName = new PersonName();
		lastName.setId(0L);
		lastName.setName(surname);
		lastName.setOrder(2);
		lastName.setPerson(person);
		
		when(session.createCriteria(PersonName.class)).thenReturn(criteria);
		//when(session.load(Person.class,1L)).thenReturn(person);
		//when(session.load(Tree.class,0L)).thenReturn(null);
	}
	
	@Test
	public void save_insertion() {
		personNameDao.save(lastName);
		verify(this.session).save(lastName);
	}
	@Test
	public void save_update() {
		personNameDao.save(firstName);
		verify(this.session).update(firstName);
	}
	@Test
	public void delete() {
		//personNameDao.delete(person, 2);
		//assertEquals(1,person.getNames().size());
	}
}
