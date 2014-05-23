package br.usp.ime.genealogy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.usp.ime.genealogy.util.Similarity;

@Entity
public class NameMatch {
	
	@Id @GeneratedValue   
	private long id;
	@ManyToOne
	@JoinColumn(name="name1_id")
	private Name name1;
	@ManyToOne
	@JoinColumn(name="name2_id")
	private Name name2;
	private Similarity rate;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
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
	
	public Similarity getRate() {
		return rate;
	}
	public void setRate(Similarity rate) {
		this.rate = rate;
	}
	
	
}
