package br.usp.ime.genealogy.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.usp.ime.genealogy.entity.Name;
import br.usp.ime.genealogy.entity.NameMatch;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.util.Jaro;
import br.usp.ime.genealogy.util.Similarity;

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
	
	@SuppressWarnings("unchecked")
	public ArrayList<Person> searchSimilarPeople(ArrayList<Name> names, float rate) {
		String query_str = "from Person as p "
				+ "inner join fetch p.names as pn "
				+ "inner join fetch pn.name as n "
				+ "inner join fetch n.matches1 as nm1 "
				+ "inner join fetch n.matches2 as nm2 "
				+ "where (nm1.rate >= ? or nm2.rate >= ?) ";
		for (int j = 0; j < names.size(); j++)
			query_str += "and (nm1.name1 = ? or nm2.name2 = ?) ";
		query_str += "group by p";
		Query q = this.session.createQuery(query_str);
		q.setFloat(0, rate);
		q.setFloat(1, rate);
		int i = 2;
		for (Name name : names) {
			q.setParameter(i++, name);
			q.setParameter(i++, name);
		}
		return (ArrayList<Person>) q.list();
	}
	
	public void completeNameMatch () {
		ArrayList<Name> comparedNames = (ArrayList<Name>) this.getComparedNames();
		ArrayList<Name> notComparedNames = (ArrayList<Name>) this.getNotComparedNames();
		for (int i = notComparedNames.size()-1; i >= 0; i--) {
			Name nameToBeCompared = notComparedNames.get(i);
			comparedNames.add(nameToBeCompared);
			for (Name name : comparedNames) {
				float rate = Jaro.getSimilarity(name.getName(),nameToBeCompared.getName());
				if(rate >= Similarity.LOW.getSimilarity()) {
					NameMatch nameMatch = new NameMatch();
					nameMatch.setName1(name);
					nameMatch.setName2(nameToBeCompared);
					nameMatch.setRate(rate);
					this.save(nameMatch);						
				}
			}
		}				
	}
}