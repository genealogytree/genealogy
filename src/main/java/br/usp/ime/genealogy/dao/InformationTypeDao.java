package br.usp.ime.genealogy.dao;

import java.util.List;

import org.hibernate.Session;

import br.usp.ime.genealogy.entity.InformationType;

import com.google.inject.Inject;

public class InformationTypeDao {

private Session session;
	
	@Inject
	public InformationTypeDao(Session session) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	public List<InformationType> list() {
		return this.session.createCriteria(InformationType.class).list();
	}
	
	public InformationType get(long id) {
		return (InformationType) this.session.load(InformationType.class, id);
	}
	
	public void save(InformationType informationType) {
		this.session.save(informationType);
	}
}
