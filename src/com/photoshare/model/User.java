package com.photoshare.model;

import javax.persistence.Column;
import javax.persistence.Table;



@Table(name="USER")
public class User {

	@Column(name="FIRST_NAME")
	private String firstname;
	
	@Column(name="LAST_NAME")
	private String lastname;
	
	@Column(name="EMAILID")
	private String emailId;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
