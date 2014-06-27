package br.usp.ime.genealogy.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.usp.ime.genealogy.entity.Name;
import br.usp.ime.genealogy.entity.NameMatch;
import br.usp.ime.genealogy.util.HibernateUtil;
import br.usp.ime.genealogy.util.Similarity;


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
		
				
	}
	
	@Test
	public void save_insertion() {
		NameMatch nameMatch = new NameMatch();		
		nameMatchDao.save(nameMatch);		
		verify(this.session).saveOrUpdate(nameMatch);		
	}
	
	@Test
	public void relatedNames() {
		assertEquals(null, this.nameMatchDao.relatedNames(null));
	}
	
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
		assertEquals(2, names.size());
	}
}
