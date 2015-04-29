package com.photoshare.bizlogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import com.photoshare.dao.AlbumDAO;
import com.photoshare.dto.ResponseDTO;
import com.photoshare.model.Album;
import com.photoshare.model.AlbumList;
import com.photoshare.model.PhotoList;
import com.photoshare.model.PhotoMeta;
import com.photoshare.model.User;
import com.photoshare.utility.Constants;

public class AlbumBizLogic {
	private static AlbumDAO albumDAO = AlbumDAO.getInstance();

	/**
	 * @param userId
	 * @return
	 */
	public List<Album> getAlbumsByUserId(int userId) {
		List<Album> albums = new ArrayList<Album>();

		Criteria criteria = albumDAO.getCriteriaInstance();
		criteria.add(Restrictions.eq("owner.id", userId));
		albums = criteria.list();

		System.out.println(albums);

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

	public PhotoList getPhotosByAlbumId(int albumId) {

		return (PhotoList) new PhotoBizLogic().getPhotosByAlbumId(albumId);

	}
	
	public Album getAlbumById(int albumId) {
		Album album = albumDAO.getAlbumById(albumId);
		return album;
	}
}
