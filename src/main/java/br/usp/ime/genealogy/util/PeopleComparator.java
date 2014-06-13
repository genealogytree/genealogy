package br.usp.ime.genealogy.util;

import java.util.List;

import br.usp.ime.genealogy.dao.MergeDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.entity.Merge;
import br.usp.ime.genealogy.entity.Person;

public class PeopleComparator {
	
	private final RelationshipDao relationshipDao;
	private final MergeDao mergeDao;
	
	public PeopleComparator(RelationshipDao relationshipDao,
			MergeDao mergeDao) {
		this.relationshipDao = relationshipDao;
		this.mergeDao = mergeDao;
	}

	public void comparePeople(Person person1, Person person2) {
		float rate, rate_max, weight, ratio, mean;
		
		Person father1, father2, mother1, mother2;

		List<Person> children1, children2, spouses1, spouses2;
		
		rate = Jaro.getSimilarity(person1.getName(), person2.getName());
		
		if (rate < Similarity.HIGH.getSimilarity()) 
			return;
		
		weight = 60;
		ratio = 60 * rate;
		
		father1 = this.relationshipDao.getParent(person1, 'F');
		father2 = this.relationshipDao.getParent(person2, 'F');
		
		mother1 = relationshipDao.getParent(person1, 'M');
		mother2 = relationshipDao.getParent(person2, 'M');
		
		spouses1 = relationshipDao.getSpouses(person1);
		spouses2 = relationshipDao.getSpouses(person2);
		
		children1 = relationshipDao.getChildren(person1);
		children2 = relationshipDao.getChildren(person2);
		
		if (father1 != null && father2 != null) {
			rate = Jaro.getSimilarity(father1.getName(), father2.getName());
			System.out.println(rate);
			
			weight += 15;
			ratio += 15 * rate;
		}	
		
		if (mother1 != null && mother2 != null) {
			rate = Jaro.getSimilarity(mother1.getName(), mother2.getName());
			System.out.println(rate);
			
			weight += 15;
			ratio += 15 * rate;
		}	
		
		if (spouses1 != null && spouses2 != null) {
			rate_max = 0;
			for (Person spouse1 : spouses1) {
				for (Person spouse2 : spouses2) {
					rate = Jaro.getSimilarity(spouse1.getName(), spouse2.getName());
					if (rate > rate_max) 
						rate_max = rate;									
				}
			}
			
			weight += 5;
			ratio += 5 * rate_max;
		}
		
		if (children1 != null && children2 != null) {
			rate_max = 0;
			for (Person child1 : children1) {
				for (Person child2 : children2) {
					rate = Jaro.getSimilarity(child1.getName(), child2.getName());
					if (rate > rate_max) 
						rate_max = rate;									
				}
			}
			
			weight += 5;
			ratio += 5 * rate_max;
		}						
		
		mean = ratio / weight;
		if (mean >= 80) {
			Merge merge = new Merge();
			merge.setPerson1(person1);
			merge.setPerson2(person2);
			merge.setRate(mean);
			merge.setStatus(MergeStatus.NONE);
			
			this.mergeDao.save(merge);
		}
	}
}
