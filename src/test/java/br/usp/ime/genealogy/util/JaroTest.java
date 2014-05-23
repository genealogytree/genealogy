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

	private ArrayList<String> names;
	
	@SuppressWarnings("resource")
	@Before
	public void setUp() {
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
		assertTrue("Equal",Jaro.getSimilarity("Adriano", "Adriano") == 1.00);
		assertTrue("e or a",Jaro.getSimilarity("Adrieno", "Adriano") > 0.90);
		assertTrue("Igor and Adriano",Jaro.getSimilarity("Igor", "Adriano") < 0.80);
		assertTrue("diacritic",Jaro.getSimilarity("Igôr", "Igor") > 0.90);
		
		assertTrue("mistyping with 7 chars",Jaro.getSimilarity("Ardiano", "Adriano") > 0.80);
		assertTrue("mistyping with 4 chars",Jaro.getSimilarity("Iogr", "Igor") > 0.80);
		assertTrue("mistyping with 2 chars",Jaro.getSimilarity("da", "ad") > 0.80);
		assertTrue("mistyping with 2 chars and case insensitive ",Jaro.getSimilarity("Da", "Ad") > 0.80);
	}
	
	@Test
	public void similarity_file() throws IOException {
		for (int i = 0; i < names.size(); i++) {
			String name1 = names.get(i);
			assertTrue("Equal "+name1,Jaro.getSimilarity(name1, name1) == 1.00);
			for (int j = i+1; j < names.size(); j++) {
				String name2 = names.get(j);
				assertTrue(name1+" != "+name2,Jaro.getSimilarity(name1, name2) < 1.00);
				/*if(Jaro.getSimilarity(name1, name2) >= 0.94)
					System.out.println("\t"+name1+"--"+name2);
				else if(Jaro.getSimilarity(name1, name2) >= 0.87)
					System.out.println(name1+"--"+name2);*/				
			}
		}
	}
}
