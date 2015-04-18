package com.photoshare.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

public class Album {

	private int id;
	
	private String name;
	
	private Date dateOfCreation;
	
	private Date dateOfModification;
	
	private String description;
	
	private String coverPhotoURL;
	
	private List<PhotoMeta> photos;
	
	private List<User> users;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	public Date getDateOfModification() {
		return dateOfModification;
	}
	public void setDateOfModification(Date dateOfModification) {
		this.dateOfModification = dateOfModification;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCoverPhotoURL() {
		return coverPhotoURL;
	}
	public void setCoverPhotoURL(String coverPhotoURL) {
		this.coverPhotoURL = coverPhotoURL;
	}
	public List<PhotoMeta> getPhotos() {
		return photos;
	}
	public void setPhotos(List<PhotoMeta> photos) {
		this.photos = photos;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
	
}
