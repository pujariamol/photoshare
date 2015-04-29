package com.photoshare.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "albumlist")
public class AlbumList {

	List<Album> albums = new ArrayList<Album>();

	public AlbumList() {

	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

}
