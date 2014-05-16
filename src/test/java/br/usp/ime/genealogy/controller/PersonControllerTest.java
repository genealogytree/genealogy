package br.usp.ime.genealogy.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.genealogy.dao.InformationTypeDao;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.PersonInformationDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.InformationType;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Tree;

public class PersonControllerTest {

	private MockResult result;
	private TreeDao treeDao;
	private PersonDao personDao;
	private PersonController personController;
	private PersonInformationDao personInformationDao;
	private InformationTypeDao informationTypeDao;
	private RelationshipDao relationshipDao;

	private Tree tree;
	private Person person;
	private List<InformationType> infotypes;

	@Before
	public void setUp() {
		result = spy(new MockResult());
		treeDao = mock(TreeDao.class);
		personDao = mock(PersonDao.class);
		personInformationDao = mock(PersonInformationDao.class);
		informationTypeDao = mock(InformationTypeDao.class);
		relationshipDao = mock(RelationshipDao.class);
		personController = new PersonController(
					result,
					personDao,
					treeDao,
					personInformationDao,
					informationTypeDao,
					relationshipDao
				);

		tree = new Tree();
		tree.setId(1);
		tree.setTitle("First Tree");

		person = new Person();
		person.setId(1L);
		person.setTree(tree);

		infotypes = new ArrayList<InformationType>();
		
		when(personDao.get(1)).thenReturn(person);
		when(treeDao.get(1)).thenReturn(tree);
		//when(informationTypeDao.list()).thenReturn(infotypes);
	}

	@Test
	public void save() {
		Person person = new Person();
		person.setId(10L);
		person.setName("TESTE");

		long[] idxs = { 1 };
		String[] data = { "04/12/2012" };
		String[] places = { "Here" };
		String[] descriptions = { "Something" };

		this.personController.save(person, tree, idxs, data, places,
				descriptions,0,'Z');

		verify(personDao).save(person);
		verify(result).redirectTo(TreeController.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void addPerson() {
		this.personController.addPerson(0, 0, 0, 'Z');
		assertEquals((long) ((Person) result.included("person")).getId(), 0L);
		assertEquals((long) ((Tree) result.included("tree")).getId(), 0L);
		List<InformationType> types = (List<InformationType>) result.included("types");
		assertEquals(types.size(), 0);
		
		this.personController.addPerson(1, 1, 0, 'Z');
		assertEquals((long) ((Person) result.included("person")).getId(), 1L);
		assertEquals((long) ((Tree) result.included("tree")).getId(), 1L);
	}
}
