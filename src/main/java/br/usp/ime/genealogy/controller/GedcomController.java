package br.usp.ime.genealogy.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;




import org.gedcom4j.parser.GedcomParserException;

import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.RequestInfo;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.usp.ime.genealogy.gedcom.GedcomExtractor;


@Resource
public class GedcomController {

	private final Result result;
	
	public GedcomController(Result result) {
		this.result = result;
	}
	
	@Post("/gedcom/upload")
	public void upload(RequestInfo info, UploadedFile gedcom) throws IOException, GedcomParserException {		
		//result.redirectTo(TreeController.class);
		
		
		//String folder = "src/main/java/br/usp/ime/genealogy/gedcom/upload";
		//String folder = info.getServletContext().getRealPath("/WEB-INF");
		String folder = "/tmp";
		File destination = new File(folder, gedcom.getFileName());
		
		try {
			IOUtils.copy(gedcom.getFile(), new FileOutputStream(destination));
		} catch (IOException e) {
			throw new RuntimeException("Error: copy failed!", e);			
		}
		
		GedcomExtractor gedcomExtractor = new GedcomExtractor(folder + "/" + gedcom.getFileName());
		gedcomExtractor.doParse();
	}	

}
