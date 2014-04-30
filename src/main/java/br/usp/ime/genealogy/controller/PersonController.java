package br.usp.ime.genealogy.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.InformationTypeDao;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.PersonInformationDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.InformationType;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonInformation;
import br.usp.ime.genealogy.entity.Tree;

@Resource
public class PersonController {

	private final Result result;
	private PersonDao personDao;
	private TreeDao treeDao;
	private PersonInformationDao personInformationDao;
	private InformationTypeDao informationTypeDao;
	
	public PersonController(Result result, PersonDao personDao, 
			TreeDao treeDao, PersonInformationDao personInformationDao,
			InformationTypeDao informationTypeDao) {
		this.result = result;
		this.personDao = personDao;
		this.treeDao = treeDao;
		this.personInformationDao = personInformationDao;
		this.informationTypeDao = informationTypeDao;
	}
	
	@Path("/person/save")
	public void save(Person person, Tree tree, 
			long [] idxs, String[] datas, String[] places, String [] descriptions){
		
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
		result.redirectTo(TreeController.class).view(tree.getId());
	}
	
	@Path("/person/addPerson")
	public void addPerson(long tree_id, long person_id) {
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
	}

}
