package br.usp.ime.genealogy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import br.usp.ime.genealogy.entity.InformationType;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonInformation;

import com.google.inject.Inject;

public class PersonInformationDao {
	
private Session session;
	
	@Inject
	public PersonInformationDao(Session session) {
		this.session = session;
	}

	public void save(PersonInformation personInformation) {		
		this.session.save(personInformation);
	}
	
	@SuppressWarnings("unchecked")
	public PersonInformation getByPersonInformationType(Person person, InformationType type) {
		List<PersonInformation> infos = session.createQuery("from PersonInformation pi where pi.person.id=?")
				.setLong(0,person.getId())
				//.setLong(0, type.getId())
				.list();
		if (infos.size() > 0)
			return infos.get(0);
		else 
			return null;		
	}

}
