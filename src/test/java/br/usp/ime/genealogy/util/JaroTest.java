package br.usp.ime.genealogy.util;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class JaroTest {

	private Jaro jaro;
	private ArrayList<String> names;
	
	@SuppressWarnings("resource")
	@Before
	public void setUp() {
		jaro = new Jaro();
		try {
			File file = new File("src/main/resources/names");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr); 
			names = new ArrayList<String>();
			for (String line; (line = br.readLine()) != null;)
				names.add(line);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void similarity_basic() {
		assertTrue("Equal",jaro.getSimilarity("Adriano", "Adriano") == 1.00);
		assertTrue("e or a",jaro.getSimilarity("Adrieno", "Adriano") > 0.90);
		assertTrue("Igor and Adriano",jaro.getSimilarity("Igor", "Adriano") < 0.80);
		assertTrue("diacritic",jaro.getSimilarity("Igôr", "Igor") > 0.90);
		
		assertTrue("mistyping with 7 chars",jaro.getSimilarity("Ardiano", "Adriano") > 0.80);
		assertTrue("mistyping with 4 chars",jaro.getSimilarity("Iogr", "Igor") > 0.80);
		assertTrue("mistyping with 2 chars",jaro.getSimilarity("da", "ad") > 0.80);
		assertTrue("mistyping with 2 chars and case insensitive ",jaro.getSimilarity("Da", "Ad") > 0.80);
	}
	
	@Test
	public void similarity_file() throws IOException {
		for (int i = 0; i < names.size(); i++) {
			String name1 = names.get(i);
			assertTrue("Equal "+name1,jaro.getSimilarity(name1, name1) == 1.00);
			for (int j = i+1; j < names.size(); j++) {
				String name2 = names.get(j);
				assertTrue(name1+" != "+name2,jaro.getSimilarity(name1, name2) < 1.00);
			}
		}
	}
}
