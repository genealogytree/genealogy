package br.usp.ime.genealogy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.usp.ime.genealogy.util.MergeStatus;

@Entity
public class Merge {	
	@Id @GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn(name="person1_id")
	private Person person1;
	
	@ManyToOne
	@JoinColumn(name="person2_id")
	private Person person2;
	
	private float rate;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="status1")
	private MergeStatus status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Person getPerson1() {
		return person1;
	}
	public void setPerson1(Person person1) {
		this.person1 = person1;
	}
	
	public Person getPerson2() {
		return person2;
	}
	public void setPerson2(Person person2) {
		this.person2 = person2;
	}
	
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	
	public MergeStatus getStatus() {
		return status;
	}
	public void setStatus(MergeStatus status) {
		this.status = status;
	}

}
