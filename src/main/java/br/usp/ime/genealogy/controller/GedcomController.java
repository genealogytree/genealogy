package br.usp.ime.genealogy.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;




import org.gedcom4j.parser.GedcomParserException;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.RequestInfo;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.usp.ime.genealogy.dao.InformationTypeDao;
import br.usp.ime.genealogy.dao.NameDao;
import br.usp.ime.genealogy.dao.PersonDao;
import br.usp.ime.genealogy.dao.PersonInformationDao;
import br.usp.ime.genealogy.dao.PersonNameDao;
import br.usp.ime.genealogy.dao.RelationshipDao;
import br.usp.ime.genealogy.dao.TreeDao;
import br.usp.ime.genealogy.entity.Tree;
import br.usp.ime.genealogy.gedcom.GedcomExtractor;


@Resource
public class GedcomController {

	private final Result result;
	private final TreeDao treeDao;
	private final PersonDao personDao;	
	private final RelationshipDao relationDao;
	private final PersonInformationDao personInformationDao;
	private final InformationTypeDao informationTypeDao;
	private final PersonNameDao personNameDao;
	private final NameDao nameDao;
	
	public GedcomController(Result result, TreeDao treeDao,
							PersonDao personDao, RelationshipDao relationDao, PersonInformationDao personInformationDao, 
							InformationTypeDao informationTypeDao, PersonNameDao personNameDao, NameDao nameDao) {
		this.result = result;
		
		this.treeDao = treeDao;
		this.personDao = personDao;
		this.relationDao = relationDao;
		this.personInformationDao = personInformationDao;
		this.informationTypeDao = informationTypeDao;		
		this.personNameDao = personNameDao;
		this.nameDao = nameDao;		
	}
	
	@Get("/gedcom/import")
	public void form(){
		
	}
	
	@Post("/gedcom/upload")
	public void upload(RequestInfo info, UploadedFile gedcom, Tree tree) throws IOException, GedcomParserException {		
		
		String folder = "/tmp";
		File destination = new File(folder, gedcom.getFileName());
		String file_name = folder + "/" + gedcom.getFileName();
		System.out.println(file_name);
		System.out.println(gedcom.getFile().toString());
		try {
			IOUtils.copy(gedcom.getFile(), new FileOutputStream(destination));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error: copy failed!", e);			
		}
		
		GedcomExtractor gedcomExtractor = new GedcomExtractor(file_name, tree, this.result, treeDao, personDao, relationDao,
				this.personInformationDao, this.informationTypeDao, this.personNameDao, this.nameDao);
		
		gedcomExtractor.doParse();
		
		result.redirectTo(TreeController.class).view(tree.getId(), tree.getRootPerson().getId());;
	}	

}
