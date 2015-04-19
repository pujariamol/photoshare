package com.photoshare.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="COMMENT")
public class Comment {

	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	
	@Column(name="COMMENT")
	private String comment;
	
	@Column(name="DATE")
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="PHOTO_ID")
	private PhotoMeta photo;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User users;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public PhotoMeta getPhoto() {
		return photo;
	}

	public void setPhoto(PhotoMeta photo) {
		this.photo = photo;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}
	
	
	
}
