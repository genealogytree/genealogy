package br.usp.ime.genealogy.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.usp.ime.genealogy.entity.Name;
import br.usp.ime.genealogy.entity.Tree;
import br.usp.ime.genealogy.util.HibernateUtil;

public class NameDaoTest {
	
	NameDao nameDao;
	List<Name> names;
	Name name1;
	Name name2;
	
	private @Mock Session session;
	private @Mock Transaction tx;
	private @Mock Criteria criteria;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(session.beginTransaction()).thenReturn(tx);
		nameDao = new NameDao(session);
		
		name1 = new Name();
		name1.setId(1L);
		name1.setName("teste1");
		
		name2 = new Name();
		name2.setId(0L);
		name2.setName("teste2");
		
		names = new ArrayList<Name>();
		names.add(name1);
		names.add(name2);
		
		when(session.createCriteria(Name.class)).thenReturn(criteria);
		when(criteria.list()).thenReturn(names);
		when(session.load(Tree.class,1L)).thenReturn(name1);
		when(session.load(Tree.class,0L)).thenReturn(null);
	}
	
	@Test
	public void getByName() {
		Name name = new Name();
		name.setName("teste");
		
		Session session = HibernateUtil.getSession();
		NameDao nameDao = new NameDao(session);
		
		nameDao.save(name);
		
		Name namedb = nameDao.getByName("teste");
		
		assertEquals("teste", namedb.getName());
	}
	
	@Test
	public void listAll() {
		List<Name> ns = nameDao.listAll();
		assertEquals(names, ns);
		verify(session).createCriteria(Name.class);
		verify(criteria).list();
	}
	
	@Test
	public void save() {
		//when(nameDao.getByName(name2.getName())).then(null);
		//nameDao.save(name2);
		//verify(this.session).save(name2);
	}
	

}
