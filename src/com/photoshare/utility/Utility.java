package com.photoshare.utility;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.photoshare.dto.ResponseDTO;
import com.photoshare.model.Album;
import com.photoshare.model.User;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

/**
 * @author Amol
 *
 */
public class Utility {

	/**
	 * @param responseDTO
	 * @return
	 */
	public static Response getResponse(ResponseDTO responseDTO) {
		ResponseBuilder res = new ResponseBuilderImpl();
		res.status(responseDTO.isSuccess() ? 200 : 500);
		res.entity(responseDTO);
		return res.build();
	}

	/**
	 * @param responseDTO
	 * @param responseCode
	 * @return
	 */
	public static Response getResponse(ResponseDTO responseDTO, int responseCode) {
		ResponseBuilder res = new ResponseBuilderImpl();
		res.status(responseDTO.isSuccess() ? responseCode : 500);
		res.entity(responseDTO);
		return res.build();
	}

	/**
	 * @param albumOwner
	 * @return
	 */
	public static String createWelcomeMsgBody(User albumOwner, String albumName) {
		StringBuilder body = new StringBuilder();
		body.append("You have been invited by ").append(
				albumOwner.getFirstname());
		body.append(" ");
		body.append(albumOwner.getLastname());
		body.append(" to view an album ").append(albumName);
		body.append(" on Mirage!!");
		body.append(" Please click below link to download the app and start sharing your memories with your dear ones!!");
		body.append("\n\n").append(Constants.APP_DOWNLOAD_URL).append("\n\n");
		body.append("\n\n").append("Thank you.\n");
		body.append("Team Mirage");
		return body.toString();
	}

	/**
	 * @param album
	 * @param user
	 * @return
	 */
	public static String createShareAlbumMsg(Album album, User friendUser) {
		StringBuilder body = new StringBuilder();
		String firstName = friendUser.getFirstname();
		String lastName = friendUser.getLastname();
		boolean isUserRegistered = false;

		if (firstName != null && lastName != null) {
			body.append("Dear ").append(firstName).append(" ").append(lastName)
					.append(",\n\n");
			isUserRegistered = true;
		}
		body.append("You have been invited by ").append(
				album.getOwner().getFirstname());
		body.append(" ");
		body.append(album.getOwner().getLastname());
		body.append(" to view an album '").append(album.getName());
		body.append("' on Mirage!!");

		if (isUserRegistered == false) {
			body.append(" Please click below link to download the app and start sharing your memories with your dear ones!!");
			body.append("\n\n").append(Constants.APP_DOWNLOAD_URL)
					.append("\n\n");
		}

		body.append("\n\n").append("Thank you.\n");
		body.append("Team Mirage");
		return body.toString();
	}

	/**
	 * @param friendEmailId
	 * @param user
	 */
	public static String sendInvite(String friendEmailId, User user) {
		StringBuilder body = new StringBuilder();
		body.append("Your friend ").append(
				user.getFirstname());
		body.append(" ");
		body.append(user.getLastname());
		body.append(" has invited you to use this awesome photosharing mobile app -- MIRAGE!!!");
		body.append(" You can download the app by clicking on the below URL and start sharing your memories with your dear ones!!");
		body.append("\n\n").append(Constants.APP_DOWNLOAD_URL).append("\n\n");
		body.append("\n\n").append("Thank you.\n");
		body.append("Team Mirage");
		return body.toString();
	}
}
