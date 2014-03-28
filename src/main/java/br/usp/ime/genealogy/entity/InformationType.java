package br.usp.ime.genealogy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class InformationType {

	@Id @GeneratedValue   
	private long id;
	private String type;
	private boolean show;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
		
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	
	
}
