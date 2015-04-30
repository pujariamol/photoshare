package com.photoshare.dao;

import org.hibernate.Criteria;

import com.photoshare.model.AlbumSharedUsers;

public class AlbumSharedUsersDAO extends CommonDAO<AlbumSharedUsers, Integer> {

	private static AlbumSharedUsersDAO instance = null;

	private AlbumSharedUsersDAO() {
		super();
	}

	public static AlbumSharedUsersDAO getInstance() {
		if (instance == null) {
			instance = new AlbumSharedUsersDAO();
		}
		return instance;
	}
	
	public Criteria getCriteriaInstance() {
		return getCriteriaInstance(AlbumSharedUsers.class);
	}
	
	
}
