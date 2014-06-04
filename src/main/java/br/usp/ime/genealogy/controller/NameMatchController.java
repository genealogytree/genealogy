package br.usp.ime.genealogy.controller;

import java.util.ArrayList;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.NameDao;
import br.usp.ime.genealogy.dao.NameMatchDao;
import br.usp.ime.genealogy.entity.Name;
import br.usp.ime.genealogy.entity.NameMatch;
import br.usp.ime.genealogy.util.Jaro;
import br.usp.ime.genealogy.util.Similarity;

@Resource
public class NameMatchController {

	private final Result result;
	private NameDao nameDao;
	private NameMatchDao nameMatchDao;
	
	public NameMatchController (Result result, NameDao nameDao, NameMatchDao nameMatchDao) {
		this.nameDao = nameDao;
		this.nameMatchDao = nameMatchDao;
		this.result = result;
	}
	
	@Path("/namematch")
	public void completeNameMatch () {
		ArrayList<Name> comparedNames = (ArrayList<Name>) nameMatchDao.getComparedNames();
		ArrayList<Name> notComparedNames = (ArrayList<Name>) nameMatchDao.getNotComparedNames();
		for (int i = notComparedNames.size()-1; i >= 0; i--) {
			Name nameToBeCompared = notComparedNames.get(i);
			comparedNames.add(nameToBeCompared);
			for (Name name : comparedNames) {
				float rate = Jaro.getSimilarity(name.getName(),nameToBeCompared.getName());
				if(rate >= Similarity.LOW.getSimilarity()) {
					NameMatch nameMatch = new NameMatch();
					nameMatch.setName1(name);
					nameMatch.setName2(nameToBeCompared);
					nameMatch.setRate(rate);
					nameMatchDao.save(nameMatch);						
				}
			}
		}
		result.permanentlyRedirectTo(IndexController.class).index();
	}
	 
}
