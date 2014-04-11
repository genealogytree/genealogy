package br.usp.ime.genealogy.dao;

import org.hibernate.Session;

import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.Tree;

import com.google.inject.Inject;

public class PersonDao {
	
	private Session session;
	
	@Inject
	public PersonDao(Session session) {
		this.session = session;
	}

	public void add(Person person, Tree tree) {
		person.setTree(tree);
		if(tree.getId() == 0)
			this.session.save(person);
		else
			this.session.update(person);
	}
}
