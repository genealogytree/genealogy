package br.usp.ime.genealogy.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Relationship;
import br.usp.ime.genealogy.entity.Tree;

@Resource
public class TreeController {

	private final Result result;
	private TreeDao treeDao;
	private PersonDao personDao;
	private RelationshipDao relationshipDao;
	
	public TreeController(Result result, TreeDao treeDao, 
			PersonDao personDao, RelationshipDao relationshipDao) {
		this.result = result;
		this.treeDao = treeDao;
		this.personDao = personDao;
		this.relationshipDao = relationshipDao;
	}
	
	@Path("/tree")
	public List<Tree> index() {
		return treeDao.listAll();
	}	
	
	public Tree form(long id) {
		Tree tree;
		if(id != 0)
			tree = treeDao.get(id);
		else
			tree = new Tree();
		return tree;
	}

	public void save(Tree tree){
		this.treeDao.save(tree);
		result.redirectTo(TreeController.class).index();
	}
	
	public void delete(long id) {
		Tree tree = this.treeDao.get(id);
		this.treeDao.delete(tree);
		result.redirectTo(TreeController.class).index();
	}

	@Path("/tree/view/{tree_id}/{person_id}")
	public void view(long tree_id, long person_id) {
		List<Person> people = new ArrayList<Person>();
		List<Person> children;
		List<Person> spouses;
		
		Person person = personDao.get(person_id);
		
		people.add(0, person);
		children = relationshipDao.getChildren(person);
		spouses = relationshipDao.getSpouses(person);
		
		int klevel = 3;
		Person p;
		Person father;
		Person mother;
		for (int i=0; i < Math.pow(2, klevel-1)-1; i++) {
			p = people.get(i);
			father = null;
			mother = null;
			
			if (p != null) {
				father = relationshipDao.getParent(p, 'F');
				mother = relationshipDao.getParent(p, 'M');
			}
			
			people.add(2*i+1, father);
			people.add(2*i+2, mother);			
		}
		
		result.include("person", person);
		result.include("children", children);
		result.include("spouses", spouses);
		result.include("people", people);
		result.include("klevel", klevel);
		
	}
}
