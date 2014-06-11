package br.usp.ime.genealogy.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;

import br.usp.ime.genealogy.entity.Merge;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.util.MergeStatus;

import com.google.inject.Inject;

public class MergeDao {
	
	private Session session;
	
	@Inject
	public MergeDao(Session session) {
		this.session = session;
	}	
	
	public Merge get(long id) {
		return (Merge) this.session.load(Merge.class,id);
	}
	
	@SuppressWarnings("unchecked")
	public Merge getbyPeople(Person person1, Person person2) {
		Query q = (Query) this.session.createQuery("from Merge where person1=? and person2=?");
		q.setParameter(0, person1);
		q.setParameter(1, person2);
		
		ArrayList<Merge> l = (ArrayList<Merge>) q.list();
		if(l.size() > 0)
			return l.get(0);
		else		
			return null;
	}
	
	public void save(Merge merge) {	
		Merge merge2 = this.getbyPeople(merge.getPerson1(), merge.getPerson2());
		
		if (merge2 == null) {		
			this.session.save(merge);
		}
		else {
			if (merge2.getStatus() == MergeStatus.MAYBE) {
				if (merge2.getRate() != merge.getRate()) {
					merge2.setStatus(MergeStatus.NONE);
					this.session.update(merge2);
				}
			}
		}
	}

}
