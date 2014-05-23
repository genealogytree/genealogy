package br.usp.ime.genealogy.util;

public enum Similarity {

	EQUAL(1.00f),
	HIGH (0.94f),
	LOW  (0.87f),
	NONE (0.00f);
	
	private float similarity;
	
	Similarity(float similarity) {
        this.similarity = similarity;
    }

	public float getSimilarity() {
		return similarity;
	}
	
	public static Similarity getSimilarity(float f) {
		for (Similarity s : Similarity.values()) {
			if(f >= s.getSimilarity())
				return s;
		}
		return NONE;
	}
}
