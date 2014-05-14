package br.usp.ime.genealogy.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;

public class GedcomControllerTest {
	
	private MockResult result;
	private TreeDao treeDao;
	private PersonDao personDao;
	private RelationshipDao relationDao;
	private GedcomController gedcomController;
	
	@Before
	public void SetUp() {
		result = spy(new MockResult());
		treeDao = mock(TreeDao.class);
		personDao = mock(PersonDao.class);
		relationDao = mock(RelationshipDao.class);
		
		gedcomController = new GedcomController(result, treeDao, personDao, relationDao);		
	}
	
	@Test
	public void form() {
		this.gedcomController.form();
	}

}
