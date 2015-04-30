package com.photoshare.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Table(name = "COMMENT")
public class Comment {

	public Comment() {
		super();
		this.date = new Date();
	}

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "DATE")
	private Date date;

	@ManyToOne
	@JoinColumn(name = "PHOTO_ID")
	private PhotoMeta photo;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(name = "STATUS")
	private String status;

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

	// adding xmlTransient to this getter will exclude it while converting it to
	// json value in response
	@XmlTransient
	public PhotoMeta getPhoto() {
		return photo;
	}

	public void setPhoto(PhotoMeta photo) {
		this.photo = photo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
