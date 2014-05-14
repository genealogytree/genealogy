package br.usp.ime.genealogy.util;

public enum RelationType {
	FATHER ('F'),
	MOTHER ('M'),
	SPOUSE ('S');
	
	private final char gedcom_type;
	
	RelationType(char gedcom_type) {
		this.gedcom_type = gedcom_type;
	}
	
	public char toChar() {
		return gedcom_type;
	}
	
	public static boolean isType(char c) {
		for (RelationType rt : values()) {
			if(rt.toChar() == c)
				return true;
		}
		return false;
	}
}
