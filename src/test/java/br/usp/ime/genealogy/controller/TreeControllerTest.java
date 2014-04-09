package br.usp.ime.genealogy.controller;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.util.test.MockResult;

import org.junit.Before;
import org.junit.Test;

import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Tree;

public class TreeControllerTest {
	
	private MockResult result;
	private TreeDao treeDao;
	private TreeController treeController;
	
	private Tree tree;
	private List<Tree> trees;
	
	
	@Before
	public void SetUp() {
		result = spy(new MockResult());
		treeDao = mock(TreeDao.class);
		treeController = new TreeController(result, treeDao);
		
		
		trees = new ArrayList<Tree>();		
		
		tree = new Tree();
		tree.setId(1);
		tree.setTitle("First Tree");
		
		trees.add(tree);
		
		when(treeDao.get(1)).thenReturn(tree);		
		
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
	}
	
	@Test
	public void save() {
		Tree tree = new Tree();		
		tree.setTitle("Tree");
		
		this.treeController.save(tree);
		
		verify(treeDao).save(tree);
		verify(result).redirectTo(TreeController.class);		
	}
	
	@Test
	public void delete() {		
		this.treeController.delete(this.tree.getId());
		verify(treeDao).delete(tree);
		verify(result).redirectTo(TreeController.class);
	}
	
	
	
}
