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
		Query qrMatch = this.session.createQuery("select * from Name where Name.id not in "
				+ "(select Name.id from Name,NameMatch where Name.id=name1_id or Name.id=name2_id)");
		return (ArrayList<Name>) qrMatch.list();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Name> comparedNames(){
		Query qrMatch = this.session.createQuery("select * from Name where Name.id in "
				+ "(select Name.id from Name,NameMatch where Name.id=name1_id or Name.id=name2_id)");
		return (ArrayList<Name>) qrMatch.list();
	}
	
}