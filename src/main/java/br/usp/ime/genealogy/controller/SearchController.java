package br.usp.ime.genealogy.controller;

import java.util.ArrayList;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.NameDao;
import br.usp.ime.genealogy.dao.NameMatchDao;
import br.usp.ime.genealogy.entity.Name;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.util.Similarity;

@Resource
public class SearchController {
	
	private final Result result;
	private NameDao nameDao;
	private NameMatchDao nameMatchDao;
	
	public SearchController(Result result, NameDao nameDao, NameMatchDao nameMatchDao) {
		this.nameDao = nameDao;
		this.nameMatchDao = nameMatchDao;
		this.result = result;
	}
	
	@Path("/search")
	public void index(String name, float similarity) {
				
		result.include("name", name);
		result.include("equal", Similarity.EQUAL);
		result.include("high", Similarity.HIGH);
		result.include("low", Similarity.LOW);
	}
	
	public void search(String name_search, float rate) {
		ArrayList<Name> names = new ArrayList<Name>();
		Name new_name;
		if(name_search == null) 
			return;
		for (String name : name_search.split(" ")) {
			if(name == "" && name == " ")
				continue;
			new_name = this.nameDao.getByName(name);
			if(new_name == null) {
				new_name = new Name();
				new_name.setName(name);
				new_name.setId(0L);
				
				this.nameDao.save(new_name);
			}
			names.add(new_name);
		}
		this.nameMatchDao.completeNameMatch();
		ArrayList<Person> people = this.nameMatchDao.searchSimilarPeople(names,rate);
		System.out.println("#pessoas"+people.size());
		for (Person person : people) {
			System.out.println(person.getId());
		}
	}
}
