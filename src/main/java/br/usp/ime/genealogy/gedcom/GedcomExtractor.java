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
import org.gedcom4j.model.FamilyEvent;
import org.gedcom4j.model.FamilyEventType;
import org.gedcom4j.model.FamilySpouse;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.Place;
//import org.gedcom4j.model.Gedcom;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonInformation;
import br.usp.ime.genealogy.entity.Relationship;
import br.usp.ime.genealogy.entity.Tree;



//1: Faz o parse de um arquivo GEDCOM
public class GedcomExtractor {
	
	private GedcomParser gedparser = new GedcomParser();
	private Gedcom ged;
	private Map<Integer, Person> hashPeople;
	Tree tree;
	TreeDao treeDao;
	PersonDao personDao;
	RelationshipDao relationDao;
	
	public GedcomExtractor(String path, Tree tree, TreeDao treeDao, PersonDao personDao, RelationshipDao relationDao) throws IOException, GedcomParserException{		
		gedparser.load(path);
		ged = gedparser.gedcom;
		
		this.tree = tree;
		this.treeDao = treeDao;
		this.personDao = personDao;
		this.relationDao = relationDao;
	}
	
	public void doParse() {
		this.setPeople();		
		this.setRelationships();
	}
	
	private void setPeople() {
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure();
		
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.openSession();
		
		//Cria árvore para este gedCom
		tree.setRootPerson(null);
		this.treeDao.save(tree);
		
				
		hashPeople = new HashMap<Integer, Person>();
		
		Person person = null;
				//2: Varre o conjunto dos indivíduos de um GEDCOM		
		for(Individual ind : ged.individuals.values()){
			person = new Person();
			person.setName(ind.formattedName());			
			person.setPersonInfos(setPersonInformation(ind));		
			//salva a pessoa			
			
			person.setTree(tree);
			this.personDao.save(person);
			
			if (tree.getRootPerson() == null){		
				tree.setRootPerson(person);
				this.treeDao.save(tree);
			}
			
			hashPeople.put(ind.hashCode(), person);			
		}
		
		
	}
	
	//adiociona os relacionamentos de casamento e pai-filho
	private void setRelationships() {
		//Varre os individuos para criar os relacionamentos
		for(Individual ind1 : ged.individuals.values()){
			//varre todos os casamentos
			for (FamilySpouse fam : ind1.familiesWhereSpouse) {
				Individual husb = null;
				Individual wife = null;
				
				//tenta adicionar o relacionamento de casamento				
				if (fam.family.husband != null) {
					if (fam.family.husband.hashCode() == ind1.hashCode()) {
						husb = ind1;
						if (fam.family.wife != null) {							
							wife = fam.family.wife;
							
							Relationship rel = new Relationship();
							rel.setPerson1(hashPeople.get(husb.hashCode()));
							rel.setPerson2(hashPeople.get(wife.hashCode()));
							rel.setType('S');
							//salva o casamento
							
							relationDao.save(rel);
							
							for (FamilyEvent event: fam.family.events) {
								if (event.type == FamilyEventType.MARRIAGE) {
									//MarriageInformation marriageInfo = new MarriageInformation();
									//marriageInfo.setDateTime(convertDate(event.date.toString()));
									//marriageInfo.setPlace(event.place.placeName);
									
									//salva os dados do casamento
								}
							}
							
						}
					} 
				} else {					
					if (fam.family.wife != null) {
						wife = fam.family.wife;
					}					
				}
				
				
				// varre o cjt de filhos de uma família e adiciona a relação Pai-Filho
				for (Individual child : fam.family.children) {
					if (husb !=  null) {
						if (husb.hashCode() == ind1.hashCode()) {
							Relationship rel = new Relationship();
							rel.setPerson1(hashPeople.get(husb.hashCode()));
							rel.setPerson2(hashPeople.get(child.hashCode()));
							rel.setType('F');
							
							//salva o pai
							this.relationDao.save(rel);
						}
					}
					
					if (wife != null) {
						if (wife.hashCode() == ind1.hashCode()) {
							Relationship rel = new Relationship();
							rel.setPerson1(hashPeople.get(wife.hashCode()));
							rel.setPerson2(hashPeople.get(child.hashCode()));
							rel.setType('M');
							
							//salva a mae
							this.relationDao.save(rel);
						}
					}
				}
			}
				
			
		}
	}
		
	private Set<PersonInformation> setPersonInformation(Individual indiv){
		
		Set<PersonInformation> personInfos = new HashSet<PersonInformation>();
		
		PersonInformation pinfo = new PersonInformation();
		pinfo.setTypeByIndex("SEX");
		pinfo.setDescription(indiv.sex.toString());
		personInfos.add(pinfo);
	 	for (IndividualEvent ievent : indiv.events) {
    		pinfo = new PersonInformation();
    		pinfo.setTypeByIndex(ievent.type.toString());
    		if(ievent.date != null)
    			pinfo.setDateTime(convertDate(ievent.date.toString()));
    		if(ievent.place != null && ievent.place.placeName != null)
    			pinfo.setPlace(ievent.place.placeName);
    		if(ievent.description != null)
    			pinfo.setDescription(ievent.description.toString());
    		
    		personInfos.add(pinfo);
    	}		
		return personInfos;
	}
	
	
	private Date convertDate(String gedDate){
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
