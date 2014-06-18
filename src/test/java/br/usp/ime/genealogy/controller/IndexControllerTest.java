package br.usp.ime.genealogy.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;

public class IndexControllerTest {
	
	private MockResult result;
	private IndexController indexController;
	
	@Before
	public void setUp() {
		result = spy(new MockResult());
		indexController = new IndexController(result);
	}
	
	@Test
	public void index() {
		this.indexController.index();
		assertEquals(result.included("variable"),"VRaptor!");
	}

}
