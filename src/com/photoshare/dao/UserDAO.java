package com.photoshare.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javassist.tools.rmi.ObjectNotFoundException;

import com.photoshare.model.PhotoMeta;
import com.photoshare.model.User;
import com.photoshare.utility.Constants;

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
	
	
	/**
	 * Returns the User with the given Id
	 * included deleted users
	 * 
	 * @param id
	 * @return user
	 */
	public User getUserById(int id) {
		return getObject(User.class, id);
	}
	
	
	public Criteria getCriteriaForActiveRecords() {
		Criteria criteria = getCriteriaInstance();
		criteria.add(Restrictions.ne(Constants.STATUS, Constants.DISABLED));
		return criteria;
	}
	
	
	public Criteria getCriteriaInstance() {
		return getCriteriaInstance(User.class);
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
