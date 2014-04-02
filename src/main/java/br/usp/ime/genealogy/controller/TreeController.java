package br.usp.ime.genealogy.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class TreeController {

	private final Result result;

	public IndexController(Result result) {
		this.result = result;
	}
	
}
