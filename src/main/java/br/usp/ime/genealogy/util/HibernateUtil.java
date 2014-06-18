package br.usp.ime.genealogy.util;

import org.hibernate.*;  
import org.hibernate.cfg.*;  

public final class HibernateUtil {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() throws MappingException {
		if (sessionFactory == null) {
			sessionFactory = new AnnotationConfiguration().configure()
					.buildSessionFactory();
		}
		return sessionFactory;
	}

	public static Session getSession() {
		return getSessionFactory().openSession();
	}

}
