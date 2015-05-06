/**
 * 
 */
package com.photoshare.bizlogic;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.photoshare.dao.CommentDAO;
import com.photoshare.model.Comment;

/**
 * @author Amol
 *
 */
public class CommentBizLogic {

	private static CommentDAO commentDAO = CommentDAO.getInstance();
	
	/**
	 * @param photoId
	 * @return
	 */
	public List<Comment> getCommentsByPhotoId(int photoId){
		Criteria commentCriteria = commentDAO.getCriteriaInstance();
		commentCriteria.add(Restrictions.eq("photo.id", photoId));
		return commentCriteria.list();
	}
	
}
