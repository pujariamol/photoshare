package com.photoshare.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.json.JSONObject;

import com.photoshare.model.Album;
import com.photoshare.model.Comment;
import com.photoshare.model.PhotoMeta;
import com.photoshare.model.User;
import com.photoshare.wrappers.AlbumList;
import com.photoshare.wrappers.CommentList;
import com.photoshare.wrappers.FriendList;
import com.photoshare.wrappers.PhotoList;

/**
 * @author Amol
 * 
 */
@XmlRootElement
@XmlSeeAlso({ User.class, Album.class, AlbumList.class, PhotoList.class,
		FriendList.class, Comment.class,CommentList.class,PhotoMeta.class })
public class ResponseDTO {

	private boolean success = true;
	private String message = "";
	// private int status = 500;
	private Object payload = null;

	public ResponseDTO() {

	}

	/**
	 * @param success
	 *            boolean value depending on the result of the response
	 * @param message
	 *            string parameter for setting message related to the operation
	 *            like success message or error details
	 * 
	 */
	public ResponseDTO(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return new JSONObject(this).toString();
	}

}
