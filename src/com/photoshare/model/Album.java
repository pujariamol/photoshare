package com.photoshare.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "album")
@Entity
@Table(name = "ALBUM")
public class Album {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DATE_OF_CREATION")
	private Date dateOfCreation;

	@Column(name = "DATE_OF_MODIFICATION")
	private Date dateOfModification;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "COVER_PHOTO_URL")
	private String coverPhotoURL;

	@OneToMany(mappedBy = "album")
	// @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private List<PhotoMeta> photos;

	@ManyToOne
	@JoinColumn(name = "OWNER_ID")
	private User owner;

	@Column(name = "STATUS")
	private String status;

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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
