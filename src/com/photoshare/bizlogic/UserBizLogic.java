package com.photoshare.bizlogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.tools.rmi.ObjectNotFoundException;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.photoshare.dao.CommentDAO;
import com.photoshare.dao.UserDAO;
import com.photoshare.model.Album;
import com.photoshare.model.Comment;
import com.photoshare.model.PhotoMeta;
import com.photoshare.model.User;
import com.photoshare.utility.Constants;
import com.photoshare.utility.EmailModule;
import com.photoshare.utility.Utility;

/**
 * @author Amol
 *
 */
public class UserBizLogic {

	private static UserDAO userDAO = UserDAO.getInstance();

	/**
	 * @param userId
	 * @return User
	 */
	public User getUserById(int userId) {
		Criteria criteria = userDAO.getCriteriaForActiveRecords();
		criteria.add(Restrictions.eq(Constants.ID, userId));
		return (User) criteria.list().get(0);
	}

	/**
	 * @param emailId
	 * @return
	 * @throws ObjectNotFoundException
	 */
	public User getUserByEmailId(String emailId) {

		Map<String, String> nameValueBean = new HashMap<String, String>();
		nameValueBean.put("emailId", emailId);

		List<User> users = (List<User>) userDAO.getObjectByQuery(
				"from User as u where u.emailId = :emailId", nameValueBean);

		return users.get(0);
	}

	public User getUserByCredentials(String emailId, String password) {
		Criteria userCriteria = userDAO.getCriteriaInstance(User.class);
		userCriteria.add(Restrictions.eq("emailId", emailId));
		userCriteria.add(Restrictions.eq("password", password));
		List<User> users = userCriteria.list();

		return users.get(0);
	}

	public List<Album> getAlbumsByUserId(int userId) {
		List<Album> list = new AlbumBizLogic().getAlbumsByUserId(userId);
		return list;
	}

	public void createUser(User user) {
		userDAO.insert(user);
	}

	public void updateUser(User user) {
		userDAO.update(user);
	}

	public List<String> getFriendsByUserId(int userId) {
		AlbumSharedUsersBizLogic albumSharedUsersBizLogic = new AlbumSharedUsersBizLogic();
		return albumSharedUsersBizLogic.getFriends(userId);
	}

	/**
	 * @param userId
	 * @param photoId
	 * @param commentStr
	 * @return
	 */
	public Comment addCommentToPhoto(int userId, int photoId, String commentStr) {
		PhotoBizLogic photoBizLogic = new PhotoBizLogic();
		PhotoMeta photo = photoBizLogic.getPhotoById(photoId);

		Comment comment = new Comment();
		comment.setPhoto(photo);
		comment.setUser(getUserById(userId));
		comment.setComment(commentStr);

		CommentDAO.getInstance().insert(comment);
		return comment;
	}

	/**
	 * @param userId
	 * @return
	 */
	public List<Album> getAllAlbumsByUserId(int userId) {
		List<Album> albums = new ArrayList<Album>();
		List<Album> sharedAlbums = new ArrayList<Album>();

		albums = new AlbumBizLogic().getAlbumsByUserId(userId);
		sharedAlbums = new AlbumBizLogic().getSharedAlbumsByUserId(userId);

		albums.addAll(sharedAlbums);

		return albums;
	}

	/**
	 * @param user
	 */
	public void deleteUser(int userId) {
		User user = userDAO.getUserById(userId);
		user.setStatus(Constants.DISABLED);
		userDAO.update(user);
	}

	/**
	 * @param userId
	 * @param friendEmailId
	 */
	public void sendInvite(int userId, String friendEmailId) {
		User user = getUserById(userId);
		String body = Utility.sendInvite(friendEmailId, user);
		EmailModule.sendEmail(friendEmailId, body,
				Constants.INVITATION_SENT_SUBJECT);
	}

}
