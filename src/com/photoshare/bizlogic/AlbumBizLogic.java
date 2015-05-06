package com.photoshare.bizlogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.photoshare.dao.AlbumDAO;
import com.photoshare.dao.AlbumSharedUsersDAO;
import com.photoshare.model.Album;
import com.photoshare.model.AlbumSharedUsers;
import com.photoshare.utility.Constants;
import com.photoshare.wrappers.PhotoList;

public class AlbumBizLogic {

	private static AlbumDAO albumDAO = AlbumDAO.getInstance();

	/**
	 * Returns list of albums
	 * 
	 * @param userId
	 * @return List<Album>
	 */
	public List<Album> getAlbumsByUserId(int userId) {
		Criteria criteria = albumDAO.getCriteriaForActiveRecords();
		criteria.add(Restrictions.eq("owner.id", userId));
		return criteria.list();
	}

	/**
	 * @param userId
	 * @return
	 */
	public List<Album> getSharedAlbumsByUserId(int userId) {
		List<Album> albums = new ArrayList<Album>();

		Criteria criteria = AlbumSharedUsersDAO.getInstance()
				.getCriteriaInstance();
		criteria.add(Restrictions.eq("user.id", userId));
		List<AlbumSharedUsers> albumSharedUsers = criteria.list();
		for (AlbumSharedUsers albumSharedUser : albumSharedUsers) {
			albums.add(albumSharedUser.getAlbum());
		}

		return albums;
	}

	/**
	 * 
	 * @param album
	 */
	public void createAlbum(Album album) {
		UserBizLogic userBizLogic = new UserBizLogic();
		album.setOwner(userBizLogic.getUserById(album.getOwner().getId()));
		album.setDateOfCreation(new Date());
		albumDAO.insert(album);
	}

	/**
	 * @param albumId
	 * @return
	 */
	public PhotoList getPhotosByAlbumId(int albumId) {
		PhotoList photoList = new PhotoList();
		photoList.setPhotos(getAlbumById(albumId).getPhotos());
		return photoList;

	}

	public Album getAlbumById(int albumId) {
		Criteria criteria = albumDAO.getCriteriaForActiveRecords();
		criteria.add(Restrictions.eq("id", albumId));
		return (Album) criteria.list().get(0);
	}

	/**
	 * @param albumId
	 */
	public void deleteAlbum(int albumId) {
		Album album = albumDAO.getAlbumById(albumId);
		album.setStatus(Constants.DISABLED);
		albumDAO.update(album);
	}

}
