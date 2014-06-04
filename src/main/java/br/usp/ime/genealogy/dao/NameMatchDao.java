package br.usp.ime.genealogy.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
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
	public ArrayList<Name> getNotComparedNames(){
		Query qrMatch = this.session.createQuery("from Name name where name.id not in (select n.id from Name n, NameMatch nm where n like nm.name1 or n like nm.name2)");
		return (ArrayList<Name>) qrMatch.list();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Name> getComparedNames(){
		Query qrMatch = this.session.createQuery("from Name name where name.id in (select n.id from Name n, NameMatch nm where n like nm.name1 or n like nm.name2)");
		return (ArrayList<Name>) qrMatch.list();
	}
	
}