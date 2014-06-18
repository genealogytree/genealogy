package br.usp.ime.genealogy.dao;

import org.junit.Before;
import org.junit.Test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.usp.ime.genealogy.entity.Name;

public class TesteTest {

	@Before
	public void setUp(){
		
		
		
	}
	
	@Test
	public void teste() {
		SessionFactory sessionFactory;
		sessionFactory = new AnnotationConfiguration().configure()
				.buildSessionFactory();
		
		
		sessionFactory = new AnnotationConfiguration().configure("hibernate.cfg.xml").buildSessionFactory();
		
		Session session = sessionFactory.openSession();
		
		//Name name = (Name) session.load(Name.class, 1L);
		
		//System.out.println(name.getName());
	}
}
