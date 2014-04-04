package br.usp.ime.genealogy.gedcom;

import java.io.IOException;

import org.gedcom4j.model.AbstractCitation;
import org.gedcom4j.model.CitationWithoutSource;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualAttribute;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.Place;
import org.gedcom4j.model.StringTree;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;

import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonInformation;

public class ParserGedcom {
	
	public static void main(String[] args) throws IOException, GedcomParserException {
		
		System.out.println("-------------Example 2-------------------");
		GedcomExtractor ge = new GedcomExtractor("src/main/java/br/usp/ime/genealogy/gedcom/example2.ged");
		for(Person p : ge.getPeople()){
			System.out.println(p.getName());
		}
		System.out.println("-------------Example 3-------------------");
		ge = new GedcomExtractor("src/main/java/br/usp/ime/genealogy/gedcom/example3.ged");
		for(Person p : ge.getPeople()){
			System.out.println(p.getName());
		}
		System.out.println("-------------Example 4-------------------");
		ge = new GedcomExtractor("src/main/java/br/usp/ime/genealogy/gedcom/example4.ged");
		for(Person p : ge.getPeople()){
			System.out.println(p.getName());
		}
		
		System.out.println("-----------------------------------------------");
        GedcomParser gp = new GedcomParser();
        gp.load("src/main/java/br/usp/ime/genealogy/gedcom/example2.ged");
        Gedcom g = gp.gedcom;
        Person person;
        PersonInformation personInfo;
        
        for (Individual i : g.individuals.values()) {
        	person = new Person();
        	person.setName(i.formattedName());
        	
        	for (IndividualEvent st : i.events) {
        		System.out.println("\t"+st.type);
        		if(st.date != null)
        			System.out.println("\t\t" + st.date);
        		if(st.place != null && st.place.placeName != null)
        			System.out.println("\t\t" + st.place.placeName);
        	}
        }
        
        /*
        for (Individual i : g.individuals.values()) {
        	
        	
            
        	personInfo = new PersonInformation();
        	personInfo.setTypeByIndex("sex");
            personInfo.setDescription(i.sex.toString());
        	        	
        	for (IndividualEvent st : i.events) {      
        		personInfo = new PersonInformation();
            	personInfo.setTypeByIndex(st.type.toString());
            	//personInfo.setDateTime(st.date);
            	personInfo.setPlace(st.place.placeName);
            	personInfo.setDescription(st.description.toString());
            }
        }
        */
    }
}