package com.photoshare.bizlogic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.photoshare.dao.AlbumSharedUsersDAO;
import com.photoshare.model.AlbumSharedUsers;
import com.photoshare.model.User;
import com.photoshare.utility.Constants;

public class AlbumSharedUsersBizLogic {

	private static AlbumSharedUsersDAO albumSharedUsersDAO = AlbumSharedUsersDAO
			.getInstance();

	public void revokeAccess(AlbumSharedUsers albumSharedUsers) {
		albumSharedUsers.setStatus(Constants.DISABLED);
		albumSharedUsersDAO.update(albumSharedUsers);

	}

	public List<String> getFriends(int userId) {
//		Criteria criteria = albumSharedUsersDAO.getCriteriaInstance();
//		criteria.add(Restrictions.eq("album.id",));
		String query = "select distinct au.friendsEmailId from AlbumSharedUsers au, User u, Album a "
				+ "where u.id = :id  and u.id = a.owner.id and a.id = au.album.id";
	
		Map<String, Integer> nameValueBean = new HashMap<String, Integer>();
		nameValueBean.put("id", userId);

		List<String> users = (List<String>) albumSharedUsersDAO.getObjectByQuery(query, nameValueBean);
		
		return users;
	}
}
