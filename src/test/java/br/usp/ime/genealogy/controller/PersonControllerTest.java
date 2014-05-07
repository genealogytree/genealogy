package br.usp.ime.genealogy.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.genealogy.dao.InformationTypeDao;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.PersonInformationDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Tree;

public class PersonControllerTest {

	private MockResult result;
	private TreeDao treeDao;
	private PersonDao personDao;
	private PersonController personController;
	private PersonInformationDao personInformationDao;
	private	InformationTypeDao informationTypeDao;

	private Tree tree;
	// private List<Tree> trees;

	private Person person;

	// private List<Person> people;

	@Before
	public void setUp() {
		result = spy(new MockResult());
		treeDao = mock(TreeDao.class);
		personDao = mock(PersonDao.class);
		personInformationDao = mock(PersonInformationDao.class);
		informationTypeDao = mock(InformationTypeDao.class);
		personController = new PersonController(result, personDao, treeDao, personInformationDao, informationTypeDao);

		// trees = new ArrayList<Tree>();
		// people = new ArrayList<Person>();

		tree = new Tree();
		tree.setId(1);
		tree.setTitle("First Tree");

		person = new Person();
		person.setId(1L);
		person.setTree(tree);

		// trees.add(tree);
		// people.add(person);

		when(treeDao.get(1)).thenReturn(tree);
	}

	@Test
	public void save() {
		Person person = new Person();
		person.setId(10L);
		person.setName("TESTE");

		long[] idxs = {1};
		String[] data = {"04/12/2012"};
		String[] places = {"Here"};
		String[] descriptions = {"Something"};

		this.personController.save(person, tree, idxs, data, places,
				descriptions);

		verify(personDao).save(person);
		verify(result).redirectTo(TreeController.class);
	}

	@Test
	public void addPerson() {
//		Tree t;
//		Person p;
		
//		t = this.treeController.form(0,0);
//		assertEquals(t.getId(), 0);
//		
//		t = this.treeController.form(tree.getId());
//		assertEquals(t.getId(), tree.getId());
	}
}
