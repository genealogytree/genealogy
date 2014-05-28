package br.usp.ime.genealogy.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import antlr.collections.List;
import br.usp.ime.genealogy.entity.Name;
import br.usp.ime.genealogy.entity.NameMatch;
import br.usp.ime.genealogy.util.Jaro;
import br.usp.ime.genealogy.util.Similarity;

import com.google.inject.Inject;

public class NameMatchDao {
	
	private Session session;
	
	@Inject
	public NameMatchDao(Session session) {
		this.session = session;
	}

	public void save(Name name) {		
		Query qrMatch = (Query) this.session.createQuery("select count(*) from NameMatch where name1 = ? or name2 = ?").setString(0, name.getName()).setString(1, name.getName());
				
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