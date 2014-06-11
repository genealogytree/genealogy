package br.usp.ime.genealogy.controller;

import java.util.ArrayList;
import java.util.List;

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
	public List<Person> index(String name, float similarity, char sex) {
				
		System.out.println(Similarity.EQUAL.getSimilarity());
		System.out.println("nome:" + name + "similaridade: "+ similarity);
		result.include("name", name);
		result.include("equal", Similarity.EQUAL.getSimilarity());
		result.include("high", Similarity.HIGH.getSimilarity());
		result.include("low", Similarity.LOW.getSimilarity());
		result.include("male", sex);
		result.include("female", sex);
		return this.search(name, similarity);
		
	}
	
	private List<Person> search(String name_search, float rate) {
		ArrayList<Name> names = new ArrayList<Name>();
		Name new_name;
		if(name_search == null) 
			return null;
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
		return this.nameMatchDao.searchSimilarPeople(names,rate);
	}

}
