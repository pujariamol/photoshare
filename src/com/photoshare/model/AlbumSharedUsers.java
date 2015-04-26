package com.photoshare.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "ALBUM_SHARED_USERS")
public class AlbumSharedUsers {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@ManyToOne
	@JoinColumn(name = "ALBUM_ID")
	private Album album;

	@Column(name = "FRIENDS_EMAIL_ID")
	private String friendsEmailId;

	@Column(name = "ACCESS_LEVEL")
	private String accessLevel;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public String getFriendsEmailId() {
		return friendsEmailId;
	}

	public void setFriendsEmailId(String friendsEmailId) {
		this.friendsEmailId = friendsEmailId;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
