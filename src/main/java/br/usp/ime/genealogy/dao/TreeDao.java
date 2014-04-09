package br.usp.ime.genealogy.dao;

import java.util.List;

import org.hibernate.Session;

import br.usp.ime.genealogy.entity.Tree;

import com.google.inject.Inject;


public class TreeDao {

	private Session session;
	
	@Inject
	public TreeDao(Session session) {
		this.session = session;
	}
	
	public void save(Tree tree){
		this.session.save(tree);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tree> listAll() {
		return session.createCriteria(Tree.class).list();
	}
}
