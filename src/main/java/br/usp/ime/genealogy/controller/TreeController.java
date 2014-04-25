package br.usp.ime.genealogy.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Tree;

@Resource
public class TreeController {

	private final Result result;
	private TreeDao treeDao;
	
	public TreeController(Result result, TreeDao treeDao) {
		this.result = result;
		this.treeDao = treeDao;
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

	@Path("/tree/view/{id}")
	public List<Person> view(long id) {
		return treeDao.getPeople(id);
	}
}
