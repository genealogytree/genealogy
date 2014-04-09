package br.usp.ime.genealogy.gedcom;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilySpouse;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
//import org.gedcom4j.model.Gedcom;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;

import br.usp.ime.genealogy.entity.MarriageInformation;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonInformation;
import br.usp.ime.genealogy.entity.Relationship;

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
		Map<Person, Individual> map = new HashMap<Person, Individual>();
		
		ArrayList<Person> people = new ArrayList<Person>();
		
		for(Individual indiv : ged.individuals.values()){
			Person person = new Person();
			person.setName(indiv.formattedName());			
			person.setPersonInfos(extractPersonInformation(indiv));			
			//??? people.add(person); -> arvore.add(person);
			map.put(person, indiv);			
		}
		
		//???Varrer os individuos para criar os relacionamentos. Paramos nos casamentos.
		for(Individual indiv : ged.individuals.values()){
			for(Individual spouse : indiv.getSpouses()){
				FamilySpouse fs = new FamilySpouse();
				Family f = new Family();
			//	f.
				//MarriageInformation marriage = new MarriageInformation(indiv.familiesWhereSpouse[get])
			}
				
				
			
		}
		return people;
	}
		
	private Set<PersonInformation> extractPersonInformation(Individual indiv){
		
		Set<PersonInformation> personInfos = new HashSet<PersonInformation>();
		
		PersonInformation pinfo = new PersonInformation();
		pinfo.setTypeByIndex("SEX");
		pinfo.setDescription(indiv.sex.toString());
		personInfos.add(pinfo);
	 	for (IndividualEvent ievent : indiv.events) {
    		pinfo = new PersonInformation();
    		pinfo.setTypeByIndex(ievent.type.toString());
    		if(ievent.date != null)
    			pinfo.setDateTime(convertDateGedcomToDateTime(ievent.date.toString()));
    		if(ievent.place != null && ievent.place.placeName != null)
    			pinfo.setPlace(ievent.place.placeName);
    		if(ievent.description != null)
    			pinfo.setDescription(ievent.description.toString());
    		
    		personInfos.add(pinfo);
    	}		
		return personInfos;
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
