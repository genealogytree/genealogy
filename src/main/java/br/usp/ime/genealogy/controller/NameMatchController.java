package br.usp.ime.genealogy.controller;

import java.util.ArrayList;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.dao.NameDao;
import br.usp.ime.genealogy.dao.NameMatchDao;
import br.usp.ime.genealogy.entity.Name;
import br.usp.ime.genealogy.entity.NameMatch;
import br.usp.ime.genealogy.entity.Person;
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
	 
}
