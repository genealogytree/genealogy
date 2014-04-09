package br.usp.ime.genealogy.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.TreeDao;
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
		result.include("variable", "VRaptor!");
		return treeDao.listAll();
	}	
	
	public void form() {
		result.include("variable", "VRaptor!");
	}
	
	@Path("/tree/save")
	public void save(Tree tree){
		Tree t = new Tree();
		System.out.println("Estou aqui!");
		System.out.println(tree.getTitle());
		System.out.println("Teste");
		t.setTitle("Teste");
		this.treeDao.save(t);
		//AnnotationConfiguration configuration = new AnnotationConfiguration();
		
	//	 Session session = HibernateUtil.getSessionFactory().openSession();
		 
		 /*
	     session.beginTransaction();	     
	     session.save(tree); 
	     session.getTransaction().commit();
	     */
		
		result.redirectTo(TreeController.class).index();
	}
	
}
