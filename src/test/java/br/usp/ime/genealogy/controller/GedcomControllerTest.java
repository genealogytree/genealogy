package br.usp.ime.genealogy.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.genealogy.dao.InformationTypeDao;
import br.usp.ime.genealogy.dao.NameDao;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.PersonInformationDao;
import br.usp.ime.genealogy.dao.PersonNameDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;

public class GedcomControllerTest {
	
	private MockResult result;
	private TreeDao treeDao;
	private PersonDao personDao;
	private RelationshipDao relationDao;
	private PersonInformationDao personInformationDao;
	private InformationTypeDao informationTypeDao;
	private PersonNameDao personNameDao;
	private NameDao nameDao;
	private GedcomController gedcomController;
	
	@Before
	public void SetUp() {
		result = spy(new MockResult());
		treeDao = mock(TreeDao.class);
		personDao = mock(PersonDao.class);
		relationDao = mock(RelationshipDao.class);
		personInformationDao = mock(PersonInformationDao.class);
		informationTypeDao = mock(InformationTypeDao.class);
		personNameDao = mock(PersonNameDao.class);
		nameDao = mock(NameDao.class);
		gedcomController = new GedcomController(result, treeDao,
				personDao, relationDao,
				personInformationDao, informationTypeDao,
				personNameDao, nameDao);		
	}
	
	@Test
	public void form() {
		this.gedcomController.form();
	}

}
