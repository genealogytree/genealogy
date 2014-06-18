package br.usp.ime.genealogy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.MergeDao;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Merge;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Tree;
import br.usp.ime.genealogy.util.MergeStatus;
import br.usp.ime.genealogy.util.PeopleComparator;

@Resource
public class MergeController {
	private final Result result;
	private final TreeDao treeDao;
	private final PersonDao personDao;
	private final MergeDao mergeDao;
	private final RelationshipDao relationshipDao;
	private final PeopleComparator peopleComparator;
	
	private final float thresholdSimilarity = (float) 0.8;
	
	private Map<Long, Person> hashPeople;
	
	
	public MergeController(Result result, TreeDao treeDao, 
			PersonDao personDao, RelationshipDao relationshipDao,
			MergeDao mergeDao) {
		this.result = result;
		this.treeDao = treeDao;
		this.personDao = personDao;
		this.mergeDao = mergeDao;
		this.relationshipDao = relationshipDao;
		this.peopleComparator = new PeopleComparator(relationshipDao);
	}
	
	@Path("/merge")
	public void index() {
		ArrayList<Tree> trees = (ArrayList<Tree>) this.treeDao.listAll();
		
		ArrayList<Person> people1 = null;
		ArrayList<Person> people2 = null;
		
		float mean;
		
		for (Tree tree1 : trees) {
			people1 = this.personDao.getByTree(tree1);
			for (Tree tree2 : trees) {				
				if (tree1.getId() >= tree2.getId()) 
					continue;
				
				people2 = this.personDao.getByTree(tree2);
				
				for (Person person1 : people1) {
					for (Person person2 : people2) {						
						mean = peopleComparator.comparePeople(person1, person2);
						
						if (mean >= this.thresholdSimilarity) {
							Merge merge = new Merge();
							merge.setPerson1(person1);
							merge.setPerson2(person2);
							merge.setRate(mean);
							merge.setStatus(MergeStatus.NONE);
							
							this.mergeDao.save(merge);
						}
					}
				}
			}			
		}
		Tree tree = this.treeDao.get(1);
		this.personDao.getByTree(tree);
	}
	
	@Path("/merge/merge")
	public void merge() {
		hashPeople = new HashMap<Long, Person>();
		
		Person person1 = this.personDao.get(20);
		Person person2 = this.personDao.get(48);
		
		this.executeRecursive(person1, person2);
	}
	
	private void executeRecursive(Person person1, Person person2) {
		hashPeople.put(person1.getId(), person1);
		hashPeople.put(person2.getId(), person2);
		
		float mean;
		
		Person father1 = this.relationshipDao.getParent(person1, 'F');
		Person father2 = this.relationshipDao.getParent(person2, 'F');
		
		if (father1 != null && father2 != null) {
			mean = peopleComparator.comparePeople(father1, father2);
			if (mean >= this.thresholdSimilarity) {
				this.executeRecursive(father1, father2);
			}
		}
		
		//verificar pai
		
		//verificar mae
		
		//verificar spouses
		
		//verificar children
	}

	public void list() {
		ArrayList<Merge> candidates = this.mergeDao.getMergeCandidates();
		ArrayList<Long> ids = new ArrayList<Long>();
		for (Merge candidate : candidates) {
			ids.add(candidate.getId());
		}
		result.include("candidates", candidates);
		result.include("candidates_ids", ids);
	}
	
	public void save(String[] status) {
		//result.include("candidates", ids);
		result.include("status", status);
		result.include("n", status.length);
	}
}
