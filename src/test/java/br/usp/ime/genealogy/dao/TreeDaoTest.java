package br.usp.ime.genealogy.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Tree;
import br.usp.ime.genealogy.util.HibernateUtil;

@SuppressWarnings("deprecation")
public class TreeDaoTest {
	
	TreeDao treeDao;
	List<Tree> trees;
	Tree tree1;
	Tree tree2;
	
	private @Mock Session session;
	private @Mock Transaction tx;
	private @Mock Criteria criteria;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(session.beginTransaction()).thenReturn(tx);
		treeDao = new TreeDao(session);
		
		tree1 = new Tree();
		tree1.setId(1L);
		tree1.setTitle("teste1");
		tree2 = new Tree();
		tree2.setId(0L);
		tree2.setTitle("teste2");
		trees = new ArrayList<Tree>();
		trees.add(tree1);
		trees.add(tree2);
		
		when(session.createCriteria(Tree.class)).thenReturn(criteria);
		when(criteria.list()).thenReturn(trees);
		when(session.load(Tree.class,1L)).thenReturn(tree1);
		when(session.load(Tree.class,0L)).thenReturn(null);
	}
	
	@Test
	public void listAll() {
		List<Tree> ts = treeDao.listAll();
		Assert.assertEquals(trees, ts);
		verify(session).createCriteria(Tree.class);
		verify(criteria).list();
	}
	
	@Test
	public void get_existing_tree() {
		Tree t = treeDao.get(tree1.getId());
		Assert.assertNotNull(t);
		Assert.assertEquals(tree1, t);
	}
	
	@Test
	public void get_not_existing_tree() {
		Tree t = treeDao.get(0L);
		Assert.assertNull(t);
	}
	
	@Test
	public void save_insertion() {
		treeDao.save(tree2);
		verify(this.session).save(tree2);
	}
	
	@Test
	public void save_update() {
		tree2.setId(2L);
		tree2.setTitle("verify");
		treeDao.save(tree2);
		verify(this.session).update(tree2);
	}
	
	@Test
	public void delete() {
		treeDao.delete(tree1);
		verify(this.session).delete(tree1);
	}
	
	@Test
	public void getPeople() {
		Session session = HibernateUtil.getSession();
		TreeDao treeDao = new TreeDao(session);
		PersonDao personDao = new PersonDao(session);
		
		Tree tree = new Tree();
		tree.setTitle("teste");
		
		treeDao.save(tree);
		
		Person person = new Person();
		person.setTree(tree);
		person.setName("teste");
		personDao.save(person);
		
		ArrayList<Person> people = treeDao.getPeople(tree.getId());
		
		for (Person person2 : people) {
			assertEquals("teste", person2.getName());
		}
		
	}

}
