package br.usp.ime.genealogy.controller;

import static org.mockito.Mockito.spy;

import org.junit.Before;

import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.genealogy.dao.NameDao;
import br.usp.ime.genealogy.dao.NameMatchDao;

public class SearchControllerTest {
	
	private MockResult result;
	private NameDao nameDao;
	private NameMatchDao nameMatchDao;
	private SearchController searchController;
	
	@Before
	public void setUp() {
		result = spy(new MockResult());
		searchController = new SearchController(result, nameDao, nameMatchDao);
	}

}
