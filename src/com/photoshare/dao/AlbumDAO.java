package com.photoshare.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javassist.tools.rmi.ObjectNotFoundException;

import com.photoshare.model.Album;
import com.photoshare.utility.Constants;

/**
 * @author Amol
 * 
 *         class for performing database operation related to Album The class
 *         don't have to implement common operation method since they are
 *         included in CommonDAO. Only operations specific to Album should be
 *         included here.
 * 
 */
public class AlbumDAO extends CommonDAO<Album, Integer> {

	private static AlbumDAO instance = null;

	private AlbumDAO() {
		super();
	}

	public static AlbumDAO getInstance() {
		if (instance == null) {
			instance = new AlbumDAO();
		}
		return instance;
	}

	/**
	 * Returns the ALbum with the given Id
	 * included deleted Albums
	 * 
	 * @param id
	 * @return Album
	 */
	public Album getAlbumById(int id) {
		return getObject(Album.class, id);
	}

	/**
	 * deleting the album like // Album album = new Album(); // album.setId(1);
	 * // delete(album); will create problem i.e. if the album is referenced in
	 * other table then it won't work right
	 * 
	 * @param album
	 * @throws ObjectNotFoundException
	 */
	public void deleteAlbum(Album album) throws ObjectNotFoundException {
		int id = album.getId();
		album = getAlbumById(id);
		if (album == null) {
			throw new ObjectNotFoundException("The album with " + id
					+ " id does not exists!!");
		}
		delete(album);
	}

	/**
	 * Used for getting records which has status != DISABLED
	 * Whenever a record is deleted we are setting its status as disabled
	 * 
	 * @return criteria
	 */
	public Criteria getCriteriaForActiveRecords() {
		Criteria criteria = getCriteriaInstance();
		criteria.add(Restrictions.ne(Constants.STATUS, Constants.DISABLED));
		return criteria;
	}

	/**
	 * can be used for getting the criteria for album object
	 * 
	 * @return Criteria
	 */
	public Criteria getCriteriaInstance() {
		return getCriteriaInstance(Album.class);
	}
}
