package br.usp.ime.genealogy.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Tree;

@Resource
public class PersonController {

	private final Result result;
	private PersonDao personDao;
	private TreeDao treeDao;
	
	public PersonController(Result result, PersonDao personDao, 
			TreeDao treeDao) {
		this.result = result;
		this.personDao = personDao;
		this.treeDao = treeDao;
	}
	
	@Path("/person/save")
	public void save(Person person, Tree tree){
		tree = treeDao.get(tree.getId());
		this.personDao.add(person, tree);
		result.redirectTo(TreeController.class).view(tree.getId());
	}
	
	@Path("/person/addPerson")
	public Tree addPerson(long tree_id) {
		
		Tree tree;
		if(tree_id != 0)
			tree = treeDao.get(tree_id);
		else
			tree = new Tree();
		return tree;
	}

}
