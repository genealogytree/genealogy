package br.usp.ime.genealogy.controller;

import org.hibernate.Session;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Tree;
import br.usp.ime.genealogy.util.HibernateUtil;

@Resource
public class TreeController {

	private final Result result;
	private TreeDao treeDao;

	public TreeController(Result result, TreeDao treeDao) {
		this.result = result;
		this.treeDao = treeDao;
	}
	
	@Path("/tree")
	public void index() {
		result.include("variable", "VRaptor!");
	}	
	
	public void form() {
		result.include("variable", "VRaptor!");
	}
	
	public void save(Tree tree){
		System.out.println("Estou aqui!");
		 Session session = HibernateUtil.getSessionFactory().openSession();
		 
		 /*
	     session.beginTransaction();	     
	     session.save(tree); 
	     session.getTransaction().commit();
	     */
		
		result.redirectTo(TreeController.class).index();
	}
	
}
