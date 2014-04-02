package br.usp.ime.genealogy.gedcom;

import java.io.IOException;

import org.gedcom4j.model.AbstractCitation;
import org.gedcom4j.model.CitationWithoutSource;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualAttribute;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.StringTree;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;

import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonInformation;

public class ParserGedcom {
	public static void main(String[] args) throws IOException, GedcomParserException {
        GedcomParser gp = new GedcomParser();
        gp.load("src/main/java/br/usp/ime/genealogy/gedcom/example.ged");
        Gedcom g = gp.gedcom;
        Person person;
        PersonInformation personInfo;
        
        for (Individual i : g.individuals.values()) {
        	person = new Person();
        	person.setName(i.formattedName());
        	
            
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
    }
}