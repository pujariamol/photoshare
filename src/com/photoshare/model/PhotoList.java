package com.photoshare.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PhotoList {

	List<PhotoMeta> photos = new ArrayList<PhotoMeta>();

	public List<PhotoMeta> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PhotoMeta> photos) {
		this.photos = photos;
	}
	
	
	
}
