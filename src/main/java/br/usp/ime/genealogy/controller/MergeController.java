package br.usp.ime.genealogy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
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
		ArrayList<Merge> merges = this.mergeDao.getMergePeople();
		ArrayList<Merge> results = null;
		for (Merge merge : merges) {
			results = initMerge(merge.getPerson1().getId(), merge.getPerson2().getId());			
			
			for (Merge result : results) {
				switch (result.getStatus()) {
				case ACCEPT:
					this.mergeTwoPerson(result.getPerson1(), result.getPerson2());					
					break;
				case REJECT:					
					break;
				case NEW:
					this.fixRelationship(result.getPerson2(), results);
					this.newPerson(merge.getPerson1().getTree(), result.getPerson2());
					break;

				default:
					System.out.println("Deu erro, não existe caso default para o merge merge!!!");
					break;
				}
				
				//apagar todas as pessoas da árvore da pessoa 2
				System.out.println(result.getStatus());
			}
		}
	
	}
	
	public ArrayList<Merge> initMerge(long id1, long id2) {
		hashPeople = new HashMap<Long, Person>();
		peopleMerge = new ArrayList<Merge>();
		
		Person person1 = this.personDao.get(id1);
		Person person2 = this.personDao.get(id2);
		
		this.executeRecursive(person1, person2, MergeStatus.ACCEPT);
		return peopleMerge;
	}
	
	public void executeRecursive(Person person1, Person person2, MergeStatus status) {
		Merge merge = new Merge();
		merge.setPerson1(person1);
		merge.setPerson2(person2);
		merge.setStatus(status);
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
				this.executeRecursive(father1, father2,MergeStatus.ACCEPT);
			}
			else {
				this.executeRecursive(father1, father2,MergeStatus.REJECT);
			}
		}
		else if (father1 == null && father2 != null) {
			if (hashPeople.containsKey(father2.getId())) {
				Merge merge2 = new Merge();
				merge2.setPerson1(null);
				merge2.setPerson2(father2);
				merge2.setStatus(MergeStatus.NEW);
				peopleMerge.add(merge2);
				
				hashPeople.put(father2.getId(), father2);				
			}
		}
		
		Person mother1 = this.relationshipDao.getParent(person1, 'M');
		Person mother2 = this.relationshipDao.getParent(person2, 'M');
		
		if (mother1 != null && mother2 != null &&
				hashPeople.containsKey(mother1.getId()) == false &&
				hashPeople.containsKey(mother2.getId()) == false) {
			mean = peopleComparator.comparePeople(mother1, mother2);
			if (mean >= this.thresholdSimilarity) {
				this.executeRecursive(mother1, mother2,MergeStatus.ACCEPT);	
			}
			else {
				this.executeRecursive(mother1, mother2,MergeStatus.REJECT);
			}
		}
		else if (mother1 == null && mother2 != null) {
			if (hashPeople.containsKey(mother2.getId()) == false) {						
				Merge merge2 = new Merge();
				merge2.setPerson1(null);
				merge2.setPerson2(mother2);
				merge2.setStatus(MergeStatus.NEW);
				peopleMerge.add(merge2);
				
				hashPeople.put(mother2.getId(), mother2);				
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
					this.executeRecursive(spouse1, finalSpouse2,MergeStatus.ACCEPT);
			}
		}
		
		
		for (Person spouse2 : spouses2) {
			if (hashPeople.containsKey(spouse2.getId()) == false) {				
				Merge merge2 = new Merge();
				merge2.setPerson1(null);
				merge2.setPerson2(spouse2);
				merge2.setStatus(MergeStatus.NEW);
				peopleMerge.add(merge2);
				
				hashPeople.put(spouse2.getId(), spouse2);
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
					this.executeRecursive(child1, finalChild2,MergeStatus.ACCEPT);
			}
		}
		
		for (Person child2 : children2) {
			if (hashPeople.containsKey(child2.getId()) == false) {
				ArrayList<Person> stepChildren = this.relationshipDao.getStepChildren(person1);
				mean = 0.0f;
				for (Person stepChild : stepChildren) {
					mean = peopleComparator.comparePeople(child2, stepChild);
					if(mean >= this.thresholdSimilarity)
						break;
				}
				
				if(mean < this.thresholdSimilarity) {
					Merge merge2 = new Merge();
					merge2.setPerson1(null);
					merge2.setPerson2(child2);
					merge2.setStatus(MergeStatus.NEW);
					peopleMerge.add(merge2);
					
					hashPeople.put(child2.getId(), child2);
				}
			}
		}
	}
	
	public void mergeTwoPerson(Person person1, Person person2) {
		//colocar as informações em person1
		
	}
	
	public Person findPersonAtMerge(Person person, ArrayList<Merge> merges) {
		for (Merge merge : merges) {
			if (merge.getPerson2().getId() == person.getId()) 
				return merge.getPerson1();
		}
		return null;
	}
	
	public void fixRelationship(Person person, ArrayList<Merge> merges) {
		Person father = this.relationshipDao.getParent(person, 'F');
		Person mother = this.relationshipDao.getParent(person, 'M');
		ArrayList<Person> spouses = this.relationshipDao.getSpouses(person);
		ArrayList<Person> children = this.relationshipDao.getChildren(person);
		
		if (father != null && hashPeople.containsKey(father.getId()) == true) {
			Person fatherFix = this.findPersonAtMerge(person, merges);
			//chamar o dao para atualizar o relacionamento de person -> father para person -> fatherFix
		}
		
		if (mother != null && hashPeople.containsKey(mother.getId()) == false) {
			
		}
		
		for (Person spouse : spouses) {
			if (hashPeople.containsKey(spouse.getId()) == false) {
				
			}
		}
		
		for (Person child : children) {
			if (hashPeople.containsKey(child.getId()) == false) {
				
			}
		}		
	}
		
	public void newPerson(Tree tree, Person person) {
		person.setTree(tree);
		this.personDao.save(person);
		
		this.hashPeople.put(person.getId(), person);
		
		
		
		Person father = this.relationshipDao.getParent(person, 'F');
		Person mother = this.relationshipDao.getParent(person, 'M');
		ArrayList<Person> spouses = this.relationshipDao.getSpouses(person);
		ArrayList<Person> children = this.relationshipDao.getChildren(person);
		
		if (father != null && hashPeople.containsKey(father.getId()) == false) {
			this.newPerson(tree, father);
		}
		
		if (mother != null && hashPeople.containsKey(mother.getId()) == false) {
			this.newPerson(tree, mother);
		}
		
		for (Person spouse : spouses) {
			if (hashPeople.containsKey(spouse.getId()) == false) {
				this.newPerson(tree, spouse);
			}
		}
		
		for (Person child : children) {
			if (hashPeople.containsKey(child.getId()) == false) {
				this.newPerson(tree, child);
			}
		}		
	}

	public void list() {
		ArrayList<Merge> candidates = this.mergeDao.getMergeCandidates();
		result.include("candidates", candidates);
		/*Long[] ids = new Long[candidates.size()];
		for (int i = 0; i < candidates.size(); i++) {
			ids[i] = candidates.get(i).getId();
		}
		result.include("candidates_ids", ids);*/
	}
	
	@Post
	public void save(String[] status, Long[] ids) {
		for (int i = 0; i < status.length; i++) {
			Long id = ids[i];
			Merge merge = mergeDao.get(id);
			switch (MergeStatus.valueOf(status[i].toUpperCase())) {
				case ACCEPT:
					merge.setStatus(MergeStatus.ACCEPT);
					mergeDao.save(merge);
					break;
				case REJECT:
					mergeDao.delete(merge);
					break;
				case MAYBE:
					merge.setStatus(MergeStatus.MAYBE);
					mergeDao.save(merge);
					break;
				default:
					break;
			}
		}
		/*result.include("candidates", ids);
		result.include("status", status);
		result.include("n", status.length);*/
	}
}
