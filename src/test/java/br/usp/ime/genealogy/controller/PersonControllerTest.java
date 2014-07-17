package br.usp.ime.genealogy.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import br.usp.ime.genealogy.entity.InformationType;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonInformation;
import br.usp.ime.genealogy.entity.Relationship;
import br.usp.ime.genealogy.entity.Tree;

public class PersonControllerTest {

	private MockResult result;
	private TreeDao treeDao;
	private PersonDao personDao;
	private PersonController personController;
	private PersonInformationDao personInformationDao;
	private InformationTypeDao informationTypeDao;
	private RelationshipDao relationshipDao;
	private PersonNameDao personNameDao;
	private NameDao nameDao;

	private Tree tree;
	private Person person;
	private Person spouse;
	private Person child;
	
	private Tree newTree;
	private Person firstPerson;
	
	private List<InformationType> infotypes;

	@Before
	public void setUp() {
		result = spy(new MockResult());
		treeDao = mock(TreeDao.class);
		personDao = mock(PersonDao.class);
		personInformationDao = mock(PersonInformationDao.class);
		informationTypeDao = mock(InformationTypeDao.class);
		relationshipDao = mock(RelationshipDao.class);
		personNameDao = mock(PersonNameDao.class);
		nameDao = mock(NameDao.class);
		personController = new PersonController(
					result,
					personDao,
					treeDao,
					personInformationDao,
					informationTypeDao,
					relationshipDao, 
					personNameDao, 
					nameDao
				);

		tree = new Tree();
		tree.setId(1L);
		tree.setTitle("First Tree");
		
		newTree = new Tree();
		newTree.setId(2L);
		newTree.setTitle("Second Tree");

		person = new Person();
		person.setId(1L);
		person.setTree(tree);
		Set<PersonInformation> infos = new HashSet<PersonInformation>();
		PersonInformation info = new PersonInformation();
		InformationType type = new InformationType();
		type.setType("sex");
		info.setType(type);
		info.setDescription("M");
		infos.add(info);
		person.setPersonInfos(infos);
		
		spouse = new Person();
		spouse.setId(2L);
		spouse.setName("Maria do Carmo");
		spouse.setTree(tree);
		infos = (Set<PersonInformation>) new HashSet<PersonInformation>();
		info = new PersonInformation();
		type = new InformationType();
		type.setType("sex");
		info.setType(type);
		info.setDescription("F");
		infos.add(info);
		spouse.setPersonInfos(infos);
		
		child = new Person();
		child.setId(3L);
		child.setTree(tree);
		
		firstPerson = new Person();
		firstPerson.setId(0L);
		firstPerson.setTree(newTree);

		infotypes = new ArrayList<InformationType>();
		
		when(personDao.get(1)).thenReturn(person);
		when(personDao.get(2)).thenReturn(spouse);
		when(personDao.get(3)).thenReturn(child);
		when(treeDao.get(1)).thenReturn(tree);
		when(treeDao.get(2)).thenReturn(newTree);
		when(informationTypeDao.list()).thenReturn(infotypes);
	}

	@Test
	public void save() {
		long[] idxs = { 1 };
		String[] data = { "04/12/2012" };
		String[] places = { "Here" };
		String[] descriptions = { "Something" };

		this.personController.save(person, tree, idxs, data, places,
				descriptions,0, 0, 'Z', person.getName());

		verify(personDao).save(person);
		verify(result).redirectTo(TreeController.class);
	}
	
	@Test
	public void saveWife() {
		long[] idxs = { 1 };
		String[] data = { "04/12/2012" };
		String[] places = { "Here" };
		String[] descriptions = { "Something" };

		this.personController.save(person, tree, idxs, data, places,
				descriptions, 2, 0, 'S', person.getName());
		verify(relationshipDao).save(any(Relationship.class));
		verify(personDao).save(person);
		verify(result).redirectTo(TreeController.class);
	}
	
	@Test
	public void saveHusband() {
		long[] idxs = { 1 };
		String[] data = { "04/12/2012" };
		String[] places = { "Here" };
		String[] descriptions = { "Something" };

		this.personController.save(spouse, tree, idxs, data, places,
				descriptions,1, 0,'S', spouse.getName());
		verify(relationshipDao).save(any(Relationship.class));
		verify(personDao).save(spouse);
		verify(result).redirectTo(TreeController.class);
	}
	
	@Test
	public void saveChild() {
		long[] idxs = { 1 };
		String[] data = { "04/12/2012" };
		String[] places = { "Here" };
		String[] descriptions = { "Something" };

		this.personController.save(person, tree, idxs, data, places,
				descriptions,3, 0, 'F', person.getName());
		verify(relationshipDao).save(any(Relationship.class));
		verify(personDao).save(person);
		verify(result).redirectTo(TreeController.class);
	}
	
	@Test
	public void saveFather() {
		long[] idxs = { 1 };
		String[] data = { "04/12/2012" };
		String[] places = { "Here" };
		String[] descriptions = { "Something" };

		this.personController.save(child, tree, idxs, data, places,
				descriptions,1, 0, 'C', child.getName());
		verify(relationshipDao).save(any(Relationship.class));
		verify(personDao).save(child);
		verify(result).redirectTo(TreeController.class);
	}
	
	@Test
	public void saveMother() {
		long[] idxs = { 1 };
		String[] data = { "04/12/2012" };
		String[] places = { "Here" };
		String[] descriptions = { "Something" };

		this.personController.save(child, tree, idxs, data, places,
				descriptions,2, 0, 'C', child.getName());
		verify(relationshipDao).save(any(Relationship.class));
		verify(personDao).save(child);
		verify(result).redirectTo(TreeController.class);
	}
	
	@Test
	public void saveFirst() {
		this.personController.saveFirstPerson(firstPerson, newTree);

		verify(personDao).save(firstPerson);
		verify(result).redirectTo(TreeController.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void addPerson() {
		this.personController.addPerson(0, 0, 0, 0, 'Z');
		assertEquals((long) ((Person) result.included("person")).getId(), 0L);
		assertEquals((long) ((Tree) result.included("tree")).getId(), 0L);
		List<InformationType> types = (List<InformationType>) result.included("types");
		assertEquals(types.size(), 0);
		
		this.personController.addPerson(1, 1, 0, 0, '\u0000');
		assertEquals((long) ((Person) result.included("person")).getId(), 1L);
		assertEquals((long) ((Tree) result.included("tree")).getId(), 1L);
	}
}
