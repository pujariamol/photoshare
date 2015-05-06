package com.photoshare.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.photoshare.model.PhotoMeta;
import com.photoshare.utility.Constants;

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

	
	/**
	 * Returns the Photos with the given Id
	 * included deleted photos
	 * 
	 * @param id
	 * @return PhotoMeta
	 */
	public PhotoMeta getPhotoById(int id) {
		return getObject(PhotoMeta.class, id);
	}

	public Criteria getCriteriaForActiveRecords() {
		Criteria criteria = getCriteriaInstance();
		criteria.add(Restrictions.ne(Constants.STATUS, Constants.DISABLED));
		return criteria;
	}
	
	
	public Criteria getCriteriaInstance() {
		return getCriteriaInstance(PhotoMeta.class);
	}
}
