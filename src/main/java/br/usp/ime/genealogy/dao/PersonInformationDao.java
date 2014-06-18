package br.usp.ime.genealogy.dao;

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
		List<PersonInformation> infos = session.createQuery("from PersonInformation where person like ? and type like ?")
				.setParameter(0,person)
				.setParameter(1,type)
				.list();
		if (infos.size() > 0)
			return infos.get(0);
		else 
			return null;		
	}

}
