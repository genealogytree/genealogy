package br.usp.ime.genealogy.dao;

import org.hibernate.Session;

import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonInformation;

import com.google.inject.Inject;

public class PersonInformationDao {
	
private Session session;
	
	@Inject
	public PersonInformationDao(Session session) {
		this.session = session;
	}

	public void add(PersonInformation personInformation) {		
		this.session.save(personInformation);
	}

}
