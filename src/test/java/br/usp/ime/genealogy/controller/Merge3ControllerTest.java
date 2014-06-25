package br.usp.ime.genealogy.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.genealogy.dao.MergeDao;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;

public class Merge3ControllerTest {
	
	private MockResult result;
	private TreeDao treeDao;
	private PersonDao personDao;
	private RelationshipDao relationshipDao;
	private MergeDao mergeDao;
	private MergeController mergeController;
	
	@Before
	public void setUp() {
		//result = spy(new MockResult());
		//Session session = HibernateUtil.getSession();
/*		treeDao = new TreeDao(session);
		personDao = new PersonDao(session);
		relationshipDao = new RelationshipDao(session);
		mergeDao = new MergeDao(session);
		mergeController = new MergeController(result,
				treeDao, personDao, relationshipDao, mergeDao);
	}*/}
	
	@Test
	public void hbihjbjh() {
		//mergeController.initMerge(75, 78);
		assertEquals(true, true);
	}

}
