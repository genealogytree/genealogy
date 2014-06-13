package br.usp.ime.genealogy.controller;

import java.util.ArrayList;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.MergeDao;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Tree;
import br.usp.ime.genealogy.util.PeopleComparator;

@Resource
public class MergeController {
	private final Result result;
	private final TreeDao treeDao;
	private final PersonDao personDao;
	private final PeopleComparator peopleComparator;
	
	
	public MergeController(Result result, TreeDao treeDao, 
			PersonDao personDao, RelationshipDao relationshipDao,
			MergeDao mergeDao) {
		this.result = result;
		this.treeDao = treeDao;
		this.personDao = personDao;
		this.peopleComparator = new PeopleComparator(relationshipDao,mergeDao);
	}
	
	@Path("/merge")
	public void index() {
		ArrayList<Tree> trees = (ArrayList<Tree>) this.treeDao.listAll();
		
		ArrayList<Person> people1 = null;
		ArrayList<Person> people2 = null;
		
		for (Tree tree1 : trees) {
			people1 = this.personDao.getByTree(tree1);
			for (Tree tree2 : trees) {				
				if (tree1.getId() >= tree2.getId()) 
					continue;
				
				people2 = this.personDao.getByTree(tree2);
				
				for (Person person1 : people1) {
					for (Person person2 : people2) {
						peopleComparator.comparePeople(person1, person2);
					}
				}
				
			}			
		}
		Tree tree = this.treeDao.get(1);
		this.personDao.getByTree(tree);
	}

}
