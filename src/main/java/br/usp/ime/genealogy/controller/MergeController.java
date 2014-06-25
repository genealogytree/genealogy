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
	private ArrayList<Merge> peopleMerge;
	
	
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

	}
	
	public ArrayList<Merge> initMerge(long id1, long id2) {
		hashPeople = new HashMap<Long, Person>();
		peopleMerge = new ArrayList<Merge>();
		
		Person person1 = this.personDao.get(id1);
		Person person2 = this.personDao.get(id2);
		
		this.executeRecursive(person1, person2);
		return peopleMerge;
	}
	
	public void executeRecursive(Person person1, Person person2) {
		Merge merge = new Merge();
		merge.setPerson1(person1);
		merge.setPerson2(person2);
		merge.setStatus(MergeStatus.ACCEPT);
		peopleMerge.add(merge);	

		
		hashPeople.put(person1.getId(), person1);
		hashPeople.put(person2.getId(), person2);
		
		float mean;
		
		Person father1 = this.relationshipDao.getParent(person1, 'F');
		Person father2 = this.relationshipDao.getParent(person2, 'F');
		
		if (father1 != null && father2 != null &&
				hashPeople.containsKey(father1.getId()) == false &&
				hashPeople.containsKey(father2.getId()) == false) {
			mean = peopleComparator.comparePeople(father1, father2);
			if (mean >= this.thresholdSimilarity) {
				this.executeRecursive(father1, father2);
			}
		}
		else if (father1 == null && father2 != null) {
			if (hashPeople.containsKey(father2.getId())) {
				System.out.println("Father");
				this.newPerson(father2);
			}
		}
		
		Person mother1 = this.relationshipDao.getParent(person1, 'M');
		Person mother2 = this.relationshipDao.getParent(person2, 'M');
		
		if (mother1 != null && mother2 != null &&
				hashPeople.containsKey(mother1.getId()) == false &&
				hashPeople.containsKey(mother2.getId()) == false) {
			mean = peopleComparator.comparePeople(mother1, mother2);
			if (mean >= this.thresholdSimilarity) {
				this.executeRecursive(mother1, mother2);	
			}
		}
		else if (mother1 == null && mother2 != null) {
			if (hashPeople.containsKey(mother2.getId()) == false) {
				System.out.println("Mother");
				this.newPerson(mother2);				
			}
		}
		
		
		ArrayList<Person> spouses1 = this.relationshipDao.getSpouses(person1);
		ArrayList<Person> spouses2 = this.relationshipDao.getSpouses(person2);
		for (Person spouse1 : spouses1) {
			float max = 0.0f;
			Person finalSpouse2 = null;
			for (Person spouse2 : spouses2) {
				mean = peopleComparator.comparePeople(spouse1, spouse2);
				if(mean > max) {
					max = mean;
					finalSpouse2 = spouse2;
				}
			}		
			if (max >= this.thresholdSimilarity) {
				if(hashPeople.containsKey(spouse1.getId()) == false &&
				hashPeople.containsKey(finalSpouse2.getId()) == false)
					this.executeRecursive(spouse1, finalSpouse2);
			}
		}
		
		
		for (Person spouse2 : spouses2) {
			if (hashPeople.containsKey(spouse2.getId()) == false) {
				System.out.println("Spouse");
				this.newPerson(spouse2);
			}
		}
		
		
		ArrayList<Person> children1 = this.relationshipDao.getChildren(person1);
		ArrayList<Person> children2 = this.relationshipDao.getChildren(person2);
		for (Person child1 : children1) {
			float max = 0.0f;
			Person finalChild2 = null;
			for (Person child2 : children2) {
				mean = peopleComparator.comparePeople(child1, child2);
				if(mean > max) {
					max = mean;
					finalChild2 = child2;
				}
			}		
			if (max >= this.thresholdSimilarity) {
				if(hashPeople.containsKey(child1.getId()) == false &&
				hashPeople.containsKey(finalChild2.getId()) == false)
					this.executeRecursive(child1, finalChild2);
			}
		}
		
		
		for (Person child2 : children2) {
			if (hashPeople.containsKey(child2.getId()) == false) {
				System.out.println("Child");
				this.newPerson(child2);
			}
		}
		
	}
	
	public void newPerson(Person person) {
		Merge merge = new Merge();
		merge.setPerson1(null);
		merge.setPerson2(person);
		merge.setStatus(MergeStatus.NEW);
		peopleMerge.add(merge);	
		
		
		hashPeople.put(person.getId(), person);
		
		Person father = this.relationshipDao.getParent(person, 'F');
		Person mother = this.relationshipDao.getParent(person, 'M');
		ArrayList<Person> spouses = this.relationshipDao.getSpouses(person);
		ArrayList<Person> children = this.relationshipDao.getChildren(person);
		
		if (father != null && hashPeople.containsKey(father.getId()) == false) {
			this.newPerson(father);
		}
		
		if (mother != null && hashPeople.containsKey(mother.getId()) == false) {
			this.newPerson(mother);
		}
		
		for (Person spouse : spouses) {
			if (hashPeople.containsKey(spouse.getId()) == false) {
				this.newPerson(spouse);
			}
		}
		
		for (Person child : children) {
			if (hashPeople.containsKey(child.getId()) == false) {
				this.newPerson(child);
			}
		}		
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
