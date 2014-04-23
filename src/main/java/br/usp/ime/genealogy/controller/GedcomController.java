package br.usp.ime.genealogy.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Relationship;
import br.usp.ime.genealogy.entity.Tree;

@Resource
public class GedcomController {

	private final Result result;
	private RelationshipDao relDao;
	
	public GedcomController(Result result, RelationshipDao relDao) {
		this.result = result;
		this.relDao = relDao;
	}
	
	@Path("/rel")
	public List<Relationship> index() {
		return relDao.listAll();
	}	
	
	/*public Tree form(long id) {
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

	public List<Person> view(long id) {
		return treeDao.getPeople(id);
	}*/
}
