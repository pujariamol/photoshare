package com.photoshare.bizlogic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.photoshare.dao.UserDAO;
import com.photoshare.model.Album;
import com.photoshare.model.AlbumList;
import com.photoshare.model.User;

public class UserBizLogic {

	private static UserDAO userDAO = UserDAO.getInstance();

	/**
	 * @param userId
	 * @return
	 */
	public User getUserById(int userId) {
		User user = userDAO.getUserById(userId);
		return user;
	}

	/**
	 * @param emailId
	 * @return
	 */
	public User getUserByEmailId(String emailId) {

		Map<String, String> nameValueBean = new HashMap<String, String>();
		nameValueBean.put("emailId", emailId);

		List<User> users = userDAO.getObjectByQuery(
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

	public AlbumList getAlbumsByUserId(int userId) {
		AlbumList albumList = new AlbumList();
		List<Album> list = new AlbumBizLogic().getAlbumsByUserId(userId);
		albumList.setAlbums(list);
		return albumList;
	}

	public void createUser(User user) {
		userDAO.insert(user);
	}

	public void updateUser(User user) {
		userDAO.update(user);
	}

	public void deleteUser(User user) {
		userDAO.delete(user);
	}

}
