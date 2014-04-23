package br.usp.ime.genealogy.gedcom;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import org.gedcom4j.model.AbstractCitation;
import org.gedcom4j.model.CitationWithoutSource;
import org.gedcom4j.model.FamilySpouse;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualAttribute;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.Place;
import org.gedcom4j.model.StringTree;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.parser.GedcomParserException;
import org.gedcom4j.relationship.Relationship;

import br.usp.ime.genealogy.entity.Person;
import br.usp.ime.genealogy.entity.PersonInformation;

public class ParserGedcom {
	
	public static void main(String[] args) throws IOException, GedcomParserException {

		GedcomExtractor ge = new GedcomExtractor("src/main/java/br/usp/ime/genealogy/gedcom/example2.ged");
		
		ge.doParse();
		
	}
}