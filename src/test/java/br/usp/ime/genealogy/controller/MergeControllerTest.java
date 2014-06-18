package br.usp.ime.genealogy.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.junit.Before;

import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.genealogy.dao.MergeDao;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;

public class MergeControllerTest {
	
	private MockResult result;
	private TreeDao treeDao;
	private PersonDao personDao;
	private RelationshipDao relationshipDao;
	private MergeDao mergeDao;
	private MergeController mergeController;
	
	@Before
	public void setUp() {
		result = spy(new MockResult());
		treeDao = mock(TreeDao.class);
		personDao = mock(PersonDao.class);
		relationshipDao = mock(RelationshipDao.class);
		mergeDao = mock(MergeDao.class);
		mergeController = new MergeController(result,
				treeDao, personDao, relationshipDao, mergeDao);
	}

}
