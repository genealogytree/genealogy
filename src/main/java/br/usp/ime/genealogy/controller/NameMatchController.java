package br.usp.ime.genealogy.controller;

import java.util.ArrayList;

import org.hibernate.Query;

import antlr.collections.List;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.NameDao;
import br.usp.ime.genealogy.entity.Name;
import br.usp.ime.genealogy.entity.NameMatch;
import br.usp.ime.genealogy.util.Jaro;
import br.usp.ime.genealogy.util.Similarity;

@Resource
public class NameMatchController {

	private final Result result;
	private NameDao nameDao;
	
	public NameMatchController (Result result, NameDao nameDao) {
		this.nameDao = nameDao;
		this.result = result;
	}
	
	public void completeNameMatch () {
		ArrayList<Name> allNames = (ArrayList<Name>) nameDao.listAll();
		
		
		
		if(((List) qrMatch.list()).length() == 0)
		{
			Query qrNames = (Query) this.session.createQuery("select name from Name");
			
			//Para cada nome, verifica a taxa de similaridade
			for(int i = 0; i < ((List) qrNames.list()).length(); i++)
			{	
				Name comparedName = new Name();
				comparedName.setName(qrNames.list().get(i).toString());
				float rate = Jaro.getSimilarity(name.getName(),comparedName.getName()); 
				 
				if(rate < Similarity.EQUAL.getSimilarity())
				{
					if(rate >= Similarity.HIGH.getSimilarity())
					{
						NameMatch nameMatch = new NameMatch();
						nameMatch.setName1(name);
						nameMatch.setName2(comparedName);
						nameMatch.setRate(Similarity.HIGH);
						this.session.save(nameMatch);						
					}
					else if(rate >= Similarity.LOW.getSimilarity())
					{	
						NameMatch nameMatch = new NameMatch();
						nameMatch.setName1(name);
						nameMatch.setName2(comparedName);
						nameMatch.setRate(Similarity.LOW);
						this.session.save(nameMatch);							
					}
				}
			}
		}
	}
	 
}
