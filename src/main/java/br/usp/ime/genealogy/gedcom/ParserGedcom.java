package br.usp.ime.genealogy.gedcom;

import java.io.IOException;

import org.gedcom4j.parser.GedcomParserException;


public class ParserGedcom {
	
	public static void main(String[] args) throws IOException, GedcomParserException {

		GedcomExtractor ge = new GedcomExtractor("src/main/java/br/usp/ime/genealogy/gedcom/example2.ged");
		
		ge.doParse();
		
	}
}