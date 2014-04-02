package br.usp.ime.genealogy.entity;

public class NameMatch {
	private Name name1;
	private Name name2;
	private float rate;

	
	
	public Name getName1() {
		return name1;
	}
	public void setName1(Name name1) {
		this.name1 = name1;
	}

	public Name getName2() {
		return name2;
	}
	public void setName2(Name name2) {
		this.name2 = name2;
	}
	
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	
}
