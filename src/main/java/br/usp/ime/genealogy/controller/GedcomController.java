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
import br.usp.ime.genealogy.dao.PersonDao;
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
	
	public GedcomController(Result result, TreeDao treeDao,
							PersonDao personDao, RelationshipDao relationDao) {
		this.result = result;
		
		this.treeDao = treeDao;
		this.personDao = personDao;
		this.relationDao = relationDao;
	}
	
	@Get("/gedcom/import")
	public void form(){
		
	}
	
	@Post("/gedcom/upload")
	public void upload(RequestInfo info, UploadedFile gedcom, Tree tree) throws IOException, GedcomParserException {		
		
		String folder = "/tmp";
		File destination = new File(folder, gedcom.getFileName());
		String file_name = folder + "/" + gedcom.getFileName();
		
		try {
			IOUtils.copy(gedcom.getFile(), new FileOutputStream(destination));
		} catch (IOException e) {
			throw new RuntimeException("Error: copy failed!", e);			
		}
		
		GedcomExtractor gedcomExtractor = new GedcomExtractor(file_name, tree, treeDao, personDao, relationDao);
		gedcomExtractor.doParse();
		
		result.redirectTo(TreeController.class).view(tree.getId(), tree.getRootPerson().getId());;
	}	

}
