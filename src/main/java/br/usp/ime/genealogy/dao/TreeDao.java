package br.usp.ime.genealogy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Tree;

import com.google.inject.Inject;


public class TreeDao {

	private Session session;
	
	@Inject
	public TreeDao(Session session) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tree> listAll() {		
		return session.createCriteria(Tree.class).list();
	}
	
	public Tree get(long id) {
		return (Tree) this.session.load(Tree.class,id);
	}
	
	public void save(Tree tree){
		if(tree.getId() == 0)
			this.session.save(tree);
		else
			this.session.update(tree);
	}
	
	public void delete(Tree tree){
		this.session.delete(tree);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Person> getPeople(long treeId) {
		return (ArrayList<Person>) session.createQuery("from Person p where p.tree.id=?").
				setLong(0,treeId).
				list();
	}
}
