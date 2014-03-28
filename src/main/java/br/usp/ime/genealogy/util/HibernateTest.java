package br.usp.ime.genealogy.util;

import org.hibernate.*;

import br.usp.ime.genealogy.entity.Person;

public class HibernateTest {

public static void main(String[] args) {
         
        Session session = HibernateUtil.getSessionFactory().openSession();
 
        session.beginTransaction();
        
        Person pessoa = new Person();
        session.save(pessoa);

     
        session.getTransaction().commit();


    }
   
}

