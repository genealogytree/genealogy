package br.usp.ime.genealogy.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.InformationTypeDao;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.PersonInformationDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.InformationType;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonInformation;
import br.usp.ime.genealogy.entity.Relationship;
import br.usp.ime.genealogy.entity.Tree;

@Resource
public class PersonController {

	private final Result result;
	private PersonDao personDao;
	private TreeDao treeDao;
	private PersonInformationDao personInformationDao;
	private InformationTypeDao informationTypeDao;
	private RelationshipDao relationDao;
	
	public PersonController(Result result, PersonDao personDao, 
			TreeDao treeDao, PersonInformationDao personInformationDao,
			InformationTypeDao informationTypeDao,
			RelationshipDao relationDao) {
		this.result = result;
		this.personDao = personDao;
		this.treeDao = treeDao;
		this.personInformationDao = personInformationDao;
		this.informationTypeDao = informationTypeDao;
		this.relationDao = relationDao;
	}
	
	@Path("/person/save")
	public void save(Person person, Tree tree, 
			long [] idxs, String[] datas, String[] places, String [] descriptions,
			long relation_id, char relation_type){
		
		tree = treeDao.get(tree.getId());
		person.setTree(tree);
		this.personDao.save(person);
		for (int i = 0; i < idxs.length; i++) {
			InformationType infoType = informationTypeDao.get(idxs[i]);
			
			personInformationDao.getByPersonInformationType(person, infoType);
			PersonInformation personInformation = new PersonInformation();
			personInformation.setPlace(places[i]);
			personInformation.setDescription(descriptions[i]);
			personInformation.setPerson(person);
			personInformation.setType(infoType);
			personInformationDao.save(personInformation);
		}	
		
		if (relation_id > 0) {
			Person p = personDao.get(relation_id);
			Relationship relation = new Relationship();
			if (relation_type == 'F' || relation_type == 'M') {
				relation.setPerson1(person);
				relation.setPerson2(p);
				relation.setType(relation_type);
			}
			else if (relation_type == 'S') {
				if(p.getSex().equals("F")) {
					relation.setPerson1(person);
					relation.setPerson2(p);
				}
				else {
					relation.setPerson1(p);
					relation.setPerson2(person);
				}
				relation.setType('S');
			}
			else if (relation_type == 'C') {
				relation.setPerson1(p);
				relation.setPerson2(person);
				if (p.getSex().equals("M"))
					relation.setType('F');
				else if (p.getSex().equals("F"))
					relation.setType('M');
			}
			relationDao.saveRelationship(relation);
		}		
		result.redirectTo(TreeController.class).view(tree.getId(), person.getId());
	}
	
	@Path("/person/addPerson")
	public void addPerson(long tree_id, long person_id, long relation_id, char relation_type) {
		Tree tree;
		if(tree_id != 0)
			tree = treeDao.get(tree_id);
		else
			tree = new Tree();
		
		Person person;
		if(person_id != 0) 
			person = personDao.get(person_id);
		else
			person = new Person();
		
		List<InformationType> types = this.informationTypeDao.list();
		
		result.include("person", person);
		result.include("tree", tree);
		result.include("types", types);
		result.include("relation_id", relation_id);
		result.include("relation_type", relation_type);
	}

}
