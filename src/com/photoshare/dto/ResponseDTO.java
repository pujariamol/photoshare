package com.photoshare.dto;

import org.json.JSONObject;

/**
 * @author Amol
 *
 */


public class ResponseDTO {

	private boolean success = false;
	private String message = "";

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

	@Override
	public String toString() {
		return new JSONObject(this).toString();
	}

}
