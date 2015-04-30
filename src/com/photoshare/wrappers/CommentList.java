/**
 * 
 */
package com.photoshare.wrappers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.photoshare.model.Comment;

/**
 * @author Amol
 *
 */

@XmlRootElement
public class CommentList {

	private List<Comment> comments = new ArrayList<Comment>();

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	
	
}
