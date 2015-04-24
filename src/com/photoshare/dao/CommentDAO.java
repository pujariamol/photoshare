package com.photoshare.dao;

import com.photoshare.model.Comment;

/**
 * @author Amol
 * 
 *         class for performing database operation related to Comment The class
 *         don't have to implement common operation method since they are
 *         included in CommonDAO. Only operations specific to Comment should be
 *         included here.
 * 
 */
public class CommentDAO extends CommonDAO<Comment, Integer> {
	private static CommentDAO instance = null;

	private CommentDAO() {
		super();
	}

	public static CommentDAO getInstance() {
		if (instance == null) {
			instance = new CommentDAO();
		}
		return instance;
	}

}
