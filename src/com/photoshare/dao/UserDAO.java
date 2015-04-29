package com.photoshare.dao;

import javassist.tools.rmi.ObjectNotFoundException;

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
	
	
	public User getUserById(int id) {
		return getObject(User.class, id);
	}
	

	public void deleteUser(User user) throws ObjectNotFoundException {
		int id = user.getId();
		user = getUserById(id);
		if (user == null) {
			throw new ObjectNotFoundException("The user with " + id
					+ " id does not exists!!");
		}
		delete(user);
	}
	
}
