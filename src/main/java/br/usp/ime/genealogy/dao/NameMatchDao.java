package br.usp.ime.genealogy.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import antlr.collections.List;
import br.usp.ime.genealogy.entity.Name;
import br.usp.ime.genealogy.entity.NameMatch;

import com.google.inject.Inject;

public class NameMatchDao {
	
	private Session session;
	
	@Inject
	public NameMatchDao(Session session) {
		this.session = session;
	}

	public void save(NameMatch nameMatch) {		
		this.session.saveOrUpdate(nameMatch);	
	}
	
	public ArrayList<Name> relatedNames (Name name) {
		//Query qrMatch = (Query) this.session.createQuery("select count(*) from NameMatch where name1 = ? or name2 = ?").setString(0, name.getName()).setString(1, name.getName());
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Name> notComparedNames(){
		Query qrMatch = this.session.createQuery("select name1 from Name inner join NameMatch on name1.name = name.name or name2.name = name.name where name1.name = name2.name");
		return (ArrayList<Name>) qrMatch.list();
	}
	
}