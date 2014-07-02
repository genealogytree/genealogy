package br.usp.ime.genealogy.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.usp.ime.genealogy.entity.Name;
import br.usp.ime.genealogy.entity.NameMatch;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonName;
import br.usp.ime.genealogy.util.HibernateUtil;
import br.usp.ime.genealogy.util.Similarity;

public class NameMatchDaoTest {
	/*int num_tests = 5;
	Name testName[] = new Name[num_tests];
	NameMatch testNameMatch;
	Person testPerson[] = new Person[num_tests];
	PersonDao personDao;
	NameDao nameDao;
	NameMatchDao nameMatchDao;
	
	private Session session;*/
	
 	Name name;
 	NameDao nameDao;
 	NameMatchDao nameMatchDao;
 	private @Mock Session session;
 	private @Mock Transaction tx;
 	private @Mock Criteria criteria;

	@Before
	public void setUp(){
		/*session = HibernateUtil.getSession();
		nameMatchDao = new NameMatchDao(session);
		nameDao = new NameDao(session);
		personDao = new PersonDao(session);
		//num_tests == 5
		testName[0] = new Name();		
		testName[0].setName("__Adriano");		
		testName[1] = new Name();
		testName[1].setName("__Adrian");
		testName[2] = new Name();
		testName[2].setName("__Luciana");
		testName[3] = new Name();
		testName[3].setName("__Juciana");
		testName[4] = new Name();
		testName[4].setName("__Diogo");

		for (int i = 0; i < num_tests; i++) {
			nameDao.save(testName[i]);
		}
		for (int i = 0; i < num_tests; i++) {
			testPerson[i] = new Person();
			testPerson[i].setName(testName[0].getName());
			personDao.save(testPerson[i]);
		}
		
		testNameMatch = new NameMatch();
		testNameMatch.setName1(testName[0]);
		testNameMatch.setName2(testName[1]);
		testNameMatch.setRate(0.95F);
		nameMatchDao.save(testNameMatch);*/
		MockitoAnnotations.initMocks(this);
 		when(session.beginTransaction()).thenReturn(tx);
 		nameMatchDao = new NameMatchDao(session);
 		nameDao = new NameDao(session);
	}
		
	@Test
	public void save_insertion() {
		NameMatch nameMatch = new NameMatch();		
 		nameMatchDao.save(nameMatch);		
 		verify(this.session).saveOrUpdate(nameMatch);		
	/*	try{ 
			NameMatch nameMatch = new NameMatch();
			nameMatch.setName1(testName[2]);
			nameMatch.setName2(testName[3]);
			nameMatch.setRate(0.99F);
			nameMatchDao.save(nameMatch);
		}
		catch (HibernateException hbExcp) {
			assertTrue(false);
		}		
		assertTrue(true);

		NameMatch nameMatch = new NameMatch();		
		nameMatchDao.save(nameMatch);		
		verify(this.session).saveOrUpdate(nameMatch);	
		*/	
	}
	
	/*@Test
	public void relatedNames() {
		assertEquals(null, this.nameMatchDao.relatedNames(null));;
 	}
 	*/
	
	@Test
	public void getNotComparedNames() {
		Session session = HibernateUtil.getSession();
		NameDao nameDao = new NameDao(session);
		NameMatchDao nameMatchDao = new NameMatchDao(session);
				
		String nameStr = "teste";
		Random rand = new Random();
		while (nameDao.getByName(nameStr) != null) {
			nameStr = nameStr + (rand.nextInt() % 10);
		}
		
		Name name = new Name();
		name.setName(nameStr);
		nameDao.save(name);
		
		ArrayList<Name> names = nameMatchDao.getNotComparedNames();
		boolean nameInList = false;
		for (Name name2 : names) {
			if (name2.getId() == name.getId())
				nameInList = true;			
		}
		assertTrue(nameInList);
	}
/*	
	@Test
	public void notComparedNames() {
		int foundNames = 0;
		ArrayList<Name> comparedNames = nameMatchDao.getComparedNames();		
		for (Name name : comparedNames) {			
			if(name.getId() == testName[4].getId())
				foundNames++;			
		}
		assertEquals(0, foundNames);
	}
	*/
	
	@Test
	public void getComparedNames() {
		Session session = HibernateUtil.getSession();
		NameDao nameDao = new NameDao(session);
		NameMatchDao nameMatchDao = new NameMatchDao(session);
		
		Name name1 = new Name();
		Name name2 = new Name();
		name1.setName("TestName");
		name2.setName("TestName2");
		nameDao.save(name1);
		nameDao.save(name2);
		
		NameMatch nameMatch = new NameMatch();
		nameMatch.setName1(name1);
		nameMatch.setName2(name2);
		nameMatch.setRate(Similarity.HIGH.getSimilarity());
		nameMatchDao.save(nameMatch);
		
		ArrayList<Name> names = nameMatchDao.getComparedNames();
		
		boolean nameInList = false;
		for (Name n : names) {
			if (n.getId() == name1.getId() || n.getId() == name2.getId())
				nameInList = true;			
		}
		assertTrue(nameInList);
		//assertEquals(2, names.size());
	}
	/*	
	
	public void saveInsertion() {		
		
	}
	@Test
	public void comparedNames(){
		int foundNames = 0;
		ArrayList<Name> comparedNames = nameMatchDao.getComparedNames();		
		for (Name name : comparedNames) {			
			if(name.getId() == testName[0].getId())
				foundNames++;
			if(name.getId() == testName[1].getId())
				foundNames++;
		}
		assertEquals(2, foundNames);
	}
	
	@Test
	public void seachSimiliarPeople() {
		ArrayList<Name> searchedNames = new ArrayList<Name>();
		for (PersonName personName : testPerson[0].getNames()) {			
			searchedNames.add(personName.getName());
		}
		assertEquals(5, nameMatchDao.searchSimilarPeople(searchedNames, 0.96f).size());		
	}
	
	@After
	public void cleanUp(){		
		nameMatchDao.delete(testNameMatch);
		for (int i = 0; i < num_tests; i++) {
			personDao.delete(testPerson[i]);
			nameDao.delete(testName[i]);
		}
	}*/
}
