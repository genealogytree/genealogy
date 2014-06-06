package br.usp.ime.genealogy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.usp.ime.genealogy.entity.Name;

import com.google.inject.Inject;

public class NameDao {

	private Session session;
	
	@Inject
	public NameDao(Session session) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	public Name getByName(String nameString) {
		Query q = (Query) this.session.createQuery("from Name where name=?");
		q.setString(0, nameString);
		ArrayList<Name> l = (ArrayList<Name>) q.list();
		
		if(l.size() > 0)
				return l.get(0);
		else		
			return null;
	}
	
	public void save(Name name) {
		if(this.getByName(name.getName()) == null)
			this.session.save(name);
	}
	
	@SuppressWarnings("unchecked")
	public List<Name> listAll() {		
		return session.createCriteria(Name.class).list();
	}
}
