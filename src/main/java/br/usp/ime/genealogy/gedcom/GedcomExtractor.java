package br.usp.ime.genealogy.gedcom;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
//import org.gedcom4j.model.Gedcom;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;

import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonInformation;

public class GedcomExtractor {
	
	private GedcomParser gedparser = new GedcomParser();
	private Gedcom ged;
	
	public GedcomExtractor(String path) throws IOException, GedcomParserException{		
		gedparser.load(path);		
		ged = gedparser.gedcom;
		return;
	}
	
	public ArrayList<Person> getPeople()
	{
		ArrayList<Person> people = new ArrayList<Person>();
		
		for(Individual indiv : ged.individuals.values()){
			Person person = new Person();
			person.setName(indiv.formattedName());			
			person.setPersonInfos(extractPersonInformation(indiv));			
			people.add(person);						
		}
		
		return people;
	}
		
	private Set<PersonInformation> extractPersonInformation(Individual indiv){
		
		Set<PersonInformation> personInfos = new HashSet<PersonInformation>();
		
		
	 	for (IndividualEvent ievent : indiv.events) {
    		PersonInformation pinfo = new PersonInformation();
    		if(ievent.date != null)
    			pinfo.setDateTime(convertDateGedcomToDateTime(ievent.date.toString()));
    		
    		//Falta terminar a extração das informações variáveis
    		if(ievent.place != null && ievent.place.placeName != null)
    			System.out.println("\t\t" + ievent.place.placeName);
    		if(ievent.description != null)
    			System.out.println("blabla");
    			
    	}		
		
		personInfos.add(null);
		
		
		
		return null;
	}
	
	
	private Date convertDateGedcomToDateTime(String gedDate){
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		Date date = new Date();
		
		if(gedDate.substring(0,2).compareToIgnoreCase("abt") == 0)
			try {
				date = dateFormat.parse(gedDate.substring(4,gedDate.length()-4));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		
		return date;
		
	}
}
