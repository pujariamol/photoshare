package com.photoshare.bizlogic;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.photoshare.dao.PhotoDAO;
import com.photoshare.model.Album;
import com.photoshare.model.PhotoMeta;
import com.photoshare.utility.Constants;

/**
 * @author Amol
 *
 */
public class PhotoBizLogic {

	private static PhotoDAO photoDAO = PhotoDAO.getInstance();

	public List<PhotoMeta> getPhotosByAlbumId(int albumId) {
		Criteria photoCriteria = getCriteriaInstance();
		photoCriteria.add(Restrictions.eq("album.id", albumId));
		List<PhotoMeta> photos = photoCriteria.list();
		return photos;
	}

	private Criteria getCriteriaInstance() {
		return photoDAO.getCriteriaInstance(PhotoMeta.class);
	}

	public PhotoMeta getPhotoById(int photoId) {
		return photoDAO.getPhotoById(photoId);
	}

	/**
	 * @param photoId
	 */
	public void deletePhotos(int photoId) {
		PhotoMeta photo = photoDAO.getPhotoById(photoId);
		photo.setStatus(Constants.DISABLED);
		photoDAO.update(photo);
	}
}
