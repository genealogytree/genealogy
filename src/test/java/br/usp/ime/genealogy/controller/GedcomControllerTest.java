package br.usp.ime.genealogy.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.gedcom4j.parser.GedcomParserException;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.when;
import br.com.caelum.vraptor.core.RequestInfo;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.util.test.MockResult;
import br.usp.ime.genealogy.dao.InformationTypeDao;
import br.usp.ime.genealogy.dao.NameDao;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.PersonInformationDao;
import br.usp.ime.genealogy.dao.PersonNameDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Tree;
import br.usp.ime.genealogy.util.HibernateUtil;

public class GedcomControllerTest {
	
	private MockResult result;
	private Session session;
	private TreeDao treeDao;
	private PersonDao personDao;
	private RelationshipDao relationDao;
	private PersonInformationDao personInformationDao;
	private InformationTypeDao informationTypeDao;
	private PersonNameDao personNameDao;
	private NameDao nameDao;
	private GedcomController gedcomController;
	
	@Before
	public void SetUp() {
		result = spy(new MockResult());
		
		session = HibernateUtil.getSession();
		
		treeDao = new TreeDao(session);
		personDao = new PersonDao(session);
		relationDao = new RelationshipDao(session);
		personInformationDao = new PersonInformationDao(session);
		informationTypeDao = new InformationTypeDao(session);
		personNameDao = new PersonNameDao(session);
		nameDao = new NameDao(session);
		gedcomController = new GedcomController(result, treeDao,
				personDao, relationDao,
				personInformationDao, informationTypeDao,
				personNameDao, nameDao);		
	}
	
	@Test
	public void form() {
		this.gedcomController.form();
	}
	
	@Test
	public void upload() {
		RequestInfo info = mock(RequestInfo.class);
		UploadedFile gedcom = mock(UploadedFile.class);
		InputStream file = null;
		try {
			file = new FileInputStream("src/test/resources/example.ged");
		} catch (FileNotFoundException e1) {
			assertTrue(false);
		}
		when(gedcom.getFileName()).thenReturn("src/test/resources/example.ged");
		when(gedcom.getFile()).thenReturn(file);

		System.out.println(System.getProperty("user.dir")+"/src/test/resources/example.ged");
		
		Tree tree = new Tree();
		
		tree.setTitle("Teste Upload Gedcom");
		
		try {
			this.gedcomController.upload(info, gedcom, tree);
		} catch (IOException e) {
			assertTrue(false);
		} catch (GedcomParserException e) {
			assertTrue(false);
		}
	}

}
