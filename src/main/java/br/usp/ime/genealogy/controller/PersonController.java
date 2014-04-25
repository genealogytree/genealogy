package br.usp.ime.genealogy.controller;

import java.util.Iterator;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonInformation;
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
	public void save(Person person, Tree tree, 
			String[] infotypes, String[] infos){
		tree = treeDao.get(tree.getId());
		for (int i = 0; i < infos.length; i++) {
			System.out.println(infotypes[i] + infos[i]);
			PersonInformation pi = new PersonInformation();
			pi.setTypeByIndex(infotypes[i]);
			pi.setDescription(infos[i]);
			person.getPersonInfos().add(pi);
		}
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
