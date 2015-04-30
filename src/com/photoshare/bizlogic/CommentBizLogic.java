/**
 * 
 */
package com.photoshare.bizlogic;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.photoshare.dao.CommentDAO;
import com.photoshare.model.Comment;
import com.photoshare.wrappers.CommentList;

/**
 * @author Amol
 *
 */
public class CommentBizLogic {

	private static CommentDAO commentDAO = CommentDAO.getInstance();
	
	public List<Comment> getCommentsByPhotoId(int photoId){
		Criteria commentCriteria = commentDAO.getCriteriaInstance();
		commentCriteria.add(Restrictions.eq("photo.id", photoId));
		return commentCriteria.list();
	}
	
}
