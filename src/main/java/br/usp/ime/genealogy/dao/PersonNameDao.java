package br.usp.ime.genealogy.dao;

import org.hibernate.Session;

import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonName;

import com.google.inject.Inject;

public class PersonNameDao {

	private Session session;
	
	@Inject
	public PersonNameDao(Session session) {
		this.session = session;
	}
	
	public void save(PersonName personName) {
		if(personName.getId() == 0)
			this.session.save(personName);
		else
			this.session.update(personName);
	}
	
	public void delete(Person person, int ordem) {
		session.createQuery("delete from PersonName where person=:aPerson and order > :ordem")
		.setParameter("aPerson", person)
		.setParameter("ordem", ordem)
		.executeUpdate();
	}
}
