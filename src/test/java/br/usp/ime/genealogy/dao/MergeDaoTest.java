package br.usp.ime.genealogy.dao;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.usp.ime.genealogy.entity.Merge;
import br.usp.ime.genealogy.entity.Name;

public class MergeDaoTest {

	MergeDao mergeDao;
	Merge merge;
	private @Mock Session session;
	private @Mock Transaction tx;
	private @Mock Criteria criteria;
	
	@Before
	public void SetUp() {
		MockitoAnnotations.initMocks(this);
		when(session.beginTransaction()).thenReturn(tx);
		when(session.createCriteria(Name.class)).thenReturn(criteria);
		mergeDao = new MergeDao(session);
		
		merge = new Merge();
		merge.setId(1L);
		
		when(session.load(Merge.class, 1L)).thenReturn(merge);
	}
	
	@Test
	public void get() {
		Merge m = mergeDao.get(merge.getId());
		assertEquals(m,merge);
	}

}
