package br.usp.ime.genealogy.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class RelationTypeTest {

	@Test
	public void isType() {
		assertTrue(RelationType.isType('F'));
		assertTrue(RelationType.isType('M'));
		assertTrue(RelationType.isType('S'));
		assertFalse(RelationType.isType('f'));
		assertFalse(RelationType.isType('m'));
		assertFalse(RelationType.isType('s'));
		assertFalse(RelationType.isType('C'));
		assertFalse(RelationType.isType('P'));
		assertFalse(RelationType.isType('E'));
		assertFalse(RelationType.isType('c'));
		assertFalse(RelationType.isType('p'));
		assertFalse(RelationType.isType('e'));
		assertFalse(RelationType.isType((char) 0));
	}
}
