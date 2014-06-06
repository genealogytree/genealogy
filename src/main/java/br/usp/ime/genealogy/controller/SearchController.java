package br.usp.ime.genealogy.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.genealogy.util.Similarity;

@Resource
public class SearchController {
	
	private final Result result;
	
	public SearchController(Result result) {
		this.result = result;
	}
	
	@Path("/search")
	public void index(String name, float similarity) {
				
		result.include("name", name);
		result.include("equal", Similarity.EQUAL);
		result.include("high", Similarity.HIGH);
		result.include("low", Similarity.LOW);
	}
}
