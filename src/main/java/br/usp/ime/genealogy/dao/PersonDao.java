package br.usp.ime.genealogy.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import antlr.collections.List;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Tree;

import com.google.inject.Inject;

public class PersonDao {
	
	private Session session;
	
	@Inject
	public PersonDao(Session session) {
		this.session = session;
	}

	public void save(Person person) {
		if(person.getId() == 0)
			this.session.save(person);
		else
			this.session.update(person);
	}
	
	public Person get(long id) {
		return (Person) this.session.load(Person.class, id);
	}
	
	public boolean exists(String name) {
		Query q = (Query) this.session.createQuery("from Person");
		//q.setString(0, name);
		List l = (List) q.list();
		return l.length() == 0;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Person> listAll() {		
		return (ArrayList<Person>) session.createCriteria(Person.class).list();
	}	

	@SuppressWarnings("unchecked")
	public ArrayList<Person> getByTree(Tree tree) {
		Query q = (Query) this.session.createQuery("from Person where tree=?");
		q.setParameter(0, tree);
		return (ArrayList<Person>) q.list();
	}
}
