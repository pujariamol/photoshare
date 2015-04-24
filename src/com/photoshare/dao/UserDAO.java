package com.photoshare.dao;

import com.photoshare.model.User;

/**
 * @author Amol
 * 
 *         class for performing database operation related to User The class
 *         don't have to implement common operation method since they are
 *         included in CommonDAO. Only operations specific to User should be
 *         included here.
 * 
 */
public class UserDAO extends CommonDAO<User, Integer> {
	private static UserDAO instance = null;

	private UserDAO() {
		super();
	}

	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
}
