package br.usp.ime.genealogy.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.genealogy.dao.NameDao;
import br.usp.ime.genealogy.dao.NameMatchDao;
import br.usp.ime.genealogy.entity.Name;
import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.util.Similarity;

public class SearchControllerTest {
	
	private MockResult result;
	private NameDao nameDao;
	private NameMatchDao nameMatchDao;
	private SearchController searchController;
	
	private Name name1;
	private Name name2;
	
	@Before
	public void setUp() {
		result = spy(new MockResult());
		nameDao = mock(NameDao.class);
		nameMatchDao = mock(NameMatchDao.class);
		searchController = new SearchController(result, nameDao, nameMatchDao);
		
		this.name1 = new Name();
		this.name1.setId(1L);
		this.name1.setName("Teste");
		
		this.name2 = new Name();
		this.name2.setId(0L);
		this.name2.setName("");
		
		when(nameDao.getByName("Teste")).thenReturn(name1);
		when(nameDao.getByName("Teste2")).thenReturn(null);
		/*try {
			PowerMockito.whenNew(Name.class).withNoArguments().thenReturn(name2);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	@Test
	public void nullNameTest() {
		List<Person> list = this.searchController.index(null,0.8f,'M');
		assertNull(list);
	}
	
	@Test
	public void emptyNameTest() {
		List<Person> list = this.searchController.index("",0.8f,'M');
		assertEquals(0,list.size());
	}
	
	@Test
	public void existentNameTest() {
		this.searchController.index("Teste",0.8f,'M');
		verify(nameMatchDao).completeNameMatch();
		verify(result).include("name", "Teste");
		verify(result).include("equal", Similarity.EQUAL.getSimilarity());
		verify(result).include("high", Similarity.HIGH.getSimilarity());
		verify(result).include("low", Similarity.LOW.getSimilarity());
		verify(result).include("male", 'M');
		verify(result).include("female", 'M');
		
		ArrayList<Name> names = new ArrayList<Name>();
		names.add(name1);
		verify(nameMatchDao).searchSimilarPeople(names,0.8f);
	}
	
	@Test
	public void nonExistentNameTest() {
		this.searchController.index("Teste2",0.8f,'M');
		verify(nameMatchDao).completeNameMatch();
		verify(nameDao).save(any(Name.class));
	}
	
	@Test
	public void spacedNameTest() {
		this.searchController.index(" Teste ",0.8f,'M');
		verify(nameMatchDao).completeNameMatch();	
		
		verify(nameDao).getByName("Teste");
		verify(nameDao,atMost(1)).getByName(any(String.class));
		verify(nameDao,atMost(0)).save(any(Name.class));
	}
}
