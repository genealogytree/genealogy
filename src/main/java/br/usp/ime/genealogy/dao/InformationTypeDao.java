package br.usp.ime.genealogy.dao;

import org.hibernate.Session;

import br.usp.ime.genealogy.entity.InformationType;

import com.google.inject.Inject;

public class InformationTypeDao {

private Session session;
	
	@Inject
	public InformationTypeDao(Session session) {
		this.session = session;
	}
	
	public void save(InformationType informationType) {
		this.session.save(informationType);
		
	}
}
