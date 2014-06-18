package br.usp.ime.genealogy.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.usp.ime.genealogy.entity.InformationType;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonInformation;

public class PersonInformationDaoTest {

	
	PersonInformationDao personInformationDao;
	
	Person person;
	PersonInformation personInformation;
	InformationType informationType;
	
	private @Mock Session session;
	private @Mock Transaction tx;
	private @Mock Criteria criteria;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(session.beginTransaction()).thenReturn(tx);
		personInformationDao = new PersonInformationDao(session);
		
		person = new Person();
		person.setId(1L);
		person.setName("teste1");
		
		informationType = new InformationType();
		informationType.setType("SEX");
		
		personInformation = new PersonInformation();
		personInformation.setId(1L);
		personInformation.setPerson(person);
		personInformation.setType(informationType);
		personInformation.setDescription("F");
		personInformation.setDateTime(null);
		personInformation.setPlace("place");		
		
		when(session.createCriteria(PersonInformation.class)).thenReturn(criteria);
		//when(session.load(Person.class,1L)).thenReturn(person);
		//when(session.load(Tree.class,0L)).thenReturn(null);
	}
	
	@Test
	public void get_existing_person() {
		PersonInformation pi = personInformationDao.getByPersonInformationType(person, informationType);
		assertNotNull(pi);
		assertEquals(personInformation, pi);
	}
	
	@Test
	public void get_not_existing_person() {
		InformationType infotype = new InformationType();
		infotype.setId(0L);
		PersonInformation pi = personInformationDao.getByPersonInformationType(person, infotype);
		assertNull(pi);
	}
	
	@Test
	public void save_insertion() {
		personInformationDao.save(personInformation);
		verify(this.session).save(personInformation);
	}
}
