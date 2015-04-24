package com.photoshare.dao;

import com.photoshare.model.PhotoMeta;

/**
 * @author Amol
 * 
 *         class for performing database operation related to Photo The class
 *         don't have to implement common operation method since they are
 *         included in CommonDAO. Only operations specific to Photo should be
 *         included here.
 * 
 */
public class PhotoDAO extends CommonDAO<PhotoMeta, Integer> {

	private static PhotoDAO instance = null;

	private PhotoDAO() {
		super();
	}

	public static PhotoDAO getInstance() {
		if (instance == null) {
			instance = new PhotoDAO();
		}
		return instance;
	}

	public PhotoMeta getPhotoById(int id) {
		return getObject(PhotoMeta.class, id);
	}

}
