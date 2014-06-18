package br.usp.ime.genealogy.controller;

import static org.mockito.Mockito.spy;

import org.junit.Before;

import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.genealogy.dao.NameDao;
import br.usp.ime.genealogy.dao.NameMatchDao;

public class NameMatchControllerTest {
	
	private MockResult result;
	private NameDao nameDao;
	private NameMatchDao nameMatchDao;
	private NameMatchController nameMatchController;
	
	@Before
	public void setUp() {
		result = spy(new MockResult());
		nameMatchController = new NameMatchController(result,
				nameDao, nameMatchDao);
	}

}
