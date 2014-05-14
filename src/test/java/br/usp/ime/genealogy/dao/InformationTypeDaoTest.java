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

import br.usp.ime.genealogy.entity.InformationType;

public class InformationTypeDaoTest {
	
	InformationTypeDao infotypeDao;
	List<InformationType> infotypes;
	InformationType infotype1;
	InformationType infotype2;
	
	private @Mock Session session;
	private @Mock Transaction tx;
	private @Mock Criteria criteria;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(session.beginTransaction()).thenReturn(tx);
		infotypeDao = new InformationTypeDao(session);
		
		infotype1 = new InformationType();
		infotype1.setId(1L);
		infotype1.setType("BIRTH");
		infotype1.setVisible(true);
		
		infotype2 = new InformationType();
		infotype2.setId(0L);
		infotype2.setType("ADDRESS");
		infotype2.setVisible(false);
		
		infotypes = new ArrayList<InformationType>();
		infotypes.add(infotype1);
		infotypes.add(infotype2);
		
		when(session.createCriteria(InformationType.class)).thenReturn(criteria);
		when(criteria.list()).thenReturn(infotypes);
		when(session.load(InformationType.class,1L)).thenReturn(infotype1);
		when(session.load(InformationType.class,0L)).thenReturn(null);
	}
	
	@Test
	public void list() {
		List<InformationType> ts = infotypeDao.list();
		Assert.assertEquals(infotypes, ts);
		verify(session).createCriteria(InformationType.class);
		verify(criteria).list();
	}
	
	@Test
	public void get_existing_infotype() {
		InformationType t = infotypeDao.get(infotype1.getId());
		Assert.assertNotNull(t);
		Assert.assertEquals(infotype1, t);
	}
	
	@Test
	public void get_not_existing_infotype() {
		InformationType t = infotypeDao.get(0L);
		Assert.assertNull(t);
	}
	
	@Test
	public void save_insertion() {
		infotypeDao.save(infotype1);
		verify(this.session).save(infotype1);
	}
}
