package br.usp.ime.genealogy.util;

public enum MergeStatus {
	
	NONE(0),
	ACCEPT(1),
	REJECT(2),
	MAYBE(3);
	

	private int status;
	
	
	MergeStatus (int i) {
		this.status = i;
	}
	
	public float getStatus() {
		return status;
	}
	
	public static MergeStatus getStatus(int i) {
		for (MergeStatus s : MergeStatus.values()) {
			if(i == s.getStatus())
				return s;
		}
		return NONE;
	}
}
