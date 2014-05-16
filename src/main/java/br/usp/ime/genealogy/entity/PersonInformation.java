package br.usp.ime.genealogy.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.usp.ime.genealogy.entity.*;

@Entity
public class PersonInformation {
	
	@Id @GeneratedValue
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name="person_id")
	private Person person;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public InformationType getType() {
		return type;
	}
	public void setType(InformationType type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name="information_type_id")
	private InformationType type;
	
	
	private Date dateTime;
	private String place;
	private String description;
	
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setTypeByIndex(String type) {
		this.type = new InformationType();
		this.type.setType(type);
	}
	
}
