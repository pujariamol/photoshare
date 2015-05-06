package com.photoshare.bizlogic;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.photoshare.dao.PhotoDAO;
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
		Criteria criteria = photoDAO.getCriteriaForActiveRecords();
		criteria.add(Restrictions.eq("id", photoId));
		return (PhotoMeta) criteria.list().get(0);
	}

	/**
	 * @param photoId
	 */
	public void deletePhotos(int photoId) {
		PhotoMeta photo = photoDAO.getPhotoById(photoId);
		photo.setStatus(Constants.DISABLED);
		photoDAO.update(photo);
	}

	/**
	 * @param albumIds
	 * @param txt
	 * @return
	 */
	public List<PhotoMeta> getPhotosInAllAlbumById(List<Integer> albumIds,
			String txt) {
		Criteria photoCriteria = getCriteriaInstance();
		Criterion nameCondition = Restrictions.ilike("name", txt,
				MatchMode.ANYWHERE);
		Criterion descriptionCond = Restrictions.ilike("description", txt,
				MatchMode.ANYWHERE);

		photoCriteria.add(Restrictions.or(nameCondition, descriptionCond));
		photoCriteria.add(Restrictions.in("album.id", albumIds));
		List<PhotoMeta> photos = photoCriteria.list();
		return photos;
	}
}
