package br.usp.ime.genealogy.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.genealogy.dao.MergeDao;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Merge;
import br.usp.ime.genealogy.util.HibernateUtil;
import br.usp.ime.genealogy.util.MergeStatus;

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
		Session session = HibernateUtil.getSession();
		treeDao = new TreeDao(session);
		personDao = new PersonDao(session);
		relationshipDao = new RelationshipDao(session);
		mergeDao = new MergeDao(session);
		mergeController = new MergeController(result,
				treeDao, personDao, relationshipDao, mergeDao);
	}

	@Test
	public void threeIdenticalPeople() {
		ArrayList<Merge> mergePeople = mergeController.initMerge(75, 78);
		assertEquals(3, mergePeople.size());
		for (Merge merge : mergePeople) {
			assertEquals(MergeStatus.ACCEPT, merge.getStatus());
		}
	}
	
	@Test
	public void fourIdenticalPeople() {
		ArrayList<Merge> mergePeople = mergeController.initMerge(81,87);
		assertEquals(4, mergePeople.size());
		for (Merge merge : mergePeople) {
			assertEquals(MergeStatus.ACCEPT, merge.getStatus());
		}
	}
	
	@Test
	public void fiveIdenticalPeople() {
		ArrayList<Merge> mergePeople = mergeController.initMerge(91,97);
		assertEquals(5, mergePeople.size());
		for (Merge merge : mergePeople) {
			System.out.println(merge.getPerson2().getId()+merge.getStatus().toString());
			assertEquals(MergeStatus.ACCEPT, merge.getStatus());
		}
	}
	
	@Test
	public void desbalanceado() {
		ArrayList<Merge> mergePeople = mergeController.initMerge(101,103);
		assertEquals(5, mergePeople.size());
		
		int i = 0;
		for (Merge merge : mergePeople) {
			if (merge.getStatus() == MergeStatus.ACCEPT) {
				i++;
			}			
		}
		assertEquals(2, i);
	}
}
