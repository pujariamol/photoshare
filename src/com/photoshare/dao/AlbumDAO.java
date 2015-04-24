package com.photoshare.dao;

import javassist.tools.rmi.ObjectNotFoundException;

import com.photoshare.model.Album;

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
}
