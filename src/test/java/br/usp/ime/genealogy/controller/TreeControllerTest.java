package br.usp.ime.genealogy.controller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Tree;

public class TreeControllerTest {
	
	private MockResult result;
	private TreeDao treeDao;
	private PersonDao personDao;
	private RelationshipDao relationshipDao;
	private TreeController treeController;
	
	private Tree tree;
	private List<Tree> trees;
	
	private Person person;
	private List<Person> people;
	
	
	@Before
	public void SetUp() {
		result = spy(new MockResult());
		treeDao = mock(TreeDao.class);
		personDao = mock(PersonDao.class);
		relationshipDao = mock(RelationshipDao.class);
		treeController = new TreeController(result, treeDao,
				personDao, relationshipDao);
		
		
		trees = new ArrayList<Tree>();
		people = new ArrayList<Person>();
		
		tree = new Tree();
		tree.setId(1);
		tree.setTitle("First Tree");
		
		person = new Person();
		person.setId(1L);
		person.setName("Victor Corleone");
		person.setTree(tree);
		
		tree.setRootPerson(person);
		
		trees.add(tree);
		people.add(person);
		
		when(treeDao.get(1)).thenReturn(tree);
		when(relationshipDao.getChildren(person)).thenReturn(null);
		when(relationshipDao.getSpouses(person)).thenReturn(null);
		when(relationshipDao.getParent(person, 'F')).thenReturn(null);
		when(relationshipDao.getParent(person, 'M')).thenReturn(null);
	}
	
	@Test
	public void index() {
		when(treeDao.listAll()).thenReturn(new ArrayList<Tree>());
		assertTrue(this.treeController.index().size() == 0);
		when(treeDao.listAll()).thenReturn(trees);
		assertTrue(this.treeController.index().size() > 0);
	}
	
	@Test
	public void form() {
		Tree t;
		
		t = this.treeController.form(0);
		assertEquals(t.getId(), 0);
		
		t = this.treeController.form(tree.getId());
		assertEquals(t.getId(), tree.getId());
		assertEquals(t.getTitle(), "First Tree");
	}
	
	@Test
	public void save() {
		Tree tree = new Tree();		
		tree.setTitle("Tree");
		
		this.treeController.save(tree,person);
		
		//verify(treeDao).save(tree);
		verify(result).redirectTo(PersonController.class);		
	}
	
	@Test
	public void delete() {		
		this.treeController.delete(this.tree.getId());
		verify(treeDao).delete(tree);
		verify(result).redirectTo(TreeController.class);
	}
	
	@Test
	public void view() {		
		this.treeController.view(1, 1L);
		
		//testday		
	}
	
	@Test
	public void search() {
		String name = "victor";
		List<Person> people;
		
		people = this.treeController.search(name);		
		assertEquals(people.get(0).getName(), person.getName());
		
		name = "fabiano";
		people = this.treeController.search(name);
		System.out.println("NOME:"+people.get(0).getName());
		assertEquals(people.size(), 0);
	}
	
}
