package com.photoshare.bizlogic;

import java.util.List;

import javassist.tools.rmi.ObjectNotFoundException;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.photoshare.dao.AlbumSharedUsersDAO;
import com.photoshare.model.Album;
import com.photoshare.model.AlbumSharedUsers;
import com.photoshare.model.User;
import com.photoshare.utility.Constants;

/**
 * @author Amol
 *
 */
public class PhotoshareServiceBizLogic {

	
	private static AlbumSharedUsersDAO albumSharedUsersDAO = AlbumSharedUsersDAO
			.getInstance();
	private static AlbumSharedUsersBizLogic albumSharedUsersBizLogic = new AlbumSharedUsersBizLogic();
	/**
	 * @param albumId
	 * @param friendEmailIds
	 * @throws ObjectNotFoundException
	 */
	public void shareAlbumToUsers(int albumId, String[] friendEmailIds) {

		AlbumBizLogic albumBizLogic = new AlbumBizLogic();
		Album album = albumBizLogic.getAlbumById(albumId);

		UserBizLogic userBizLogic = new UserBizLogic();

		for (String emailId : friendEmailIds) {
			User user = null;
			try {
				user = userBizLogic.getUserByEmailId(emailId);
			} catch (IndexOutOfBoundsException e) {
				user = new User();
				user.setEmailId(emailId);
				// TODO send email to this email id for invitation
				userBizLogic.createUser(user);
			}

			if (!isSharedAlbum(user.getId(), album.getId())) {
				AlbumSharedUsers albumSharedUsers = new AlbumSharedUsers();
				albumSharedUsers.setAccessLevel(Constants.CAN_VIEW);
				albumSharedUsers.setUser(user);
				albumSharedUsers.setAlbum(album);
				albumSharedUsers.setFriendsEmailId(emailId);
				albumSharedUsers.setStatus(Constants.ACTIVE);
				albumSharedUsersDAO.insert(albumSharedUsers);
			}

		}
	}

	private boolean isSharedAlbum(int userId, int albumId) {
		boolean flag = true;
		Criteria asuCriteria = albumSharedUsersDAO
				.getCriteriaInstance(AlbumSharedUsers.class);
		asuCriteria.add(Restrictions.eq("album.id", albumId));
		asuCriteria.add(Restrictions.eq("user.id", userId));
		asuCriteria.add(Restrictions.eqOrIsNull("status", Constants.DISABLED));
		if (asuCriteria.list().isEmpty()) {
			flag = false;
		}
		return flag;
	}

	public void revokeUsersFromAlbum(int albumId, String[] friendEmailIds) {

		

		for (String emailId : friendEmailIds) {
			Criteria asuCriteria = albumSharedUsersDAO
					.getCriteriaInstance(AlbumSharedUsers.class);
			// Object user = userBizLogic.getAlbumsByUserId(emailId);
			asuCriteria.add(Restrictions.eq("album.id", albumId));
			asuCriteria.add(Restrictions.eq("friendsEmailId", emailId));
			List<AlbumSharedUsers> asuList = asuCriteria.list();
			
			if (!asuList.isEmpty()) {
				albumSharedUsersBizLogic.revokeAccess(asuList.get(0));
			}

		}

	}

	/**
	 * @param searchTxt
	 */
	public void getPhotosBySearchText(String searchTxt) {
		
		
	}

}
