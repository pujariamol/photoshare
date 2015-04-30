package com.photoshare;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.photoshare.bizlogic.UserBizLogic;
import com.photoshare.dto.ResponseDTO;
import com.photoshare.model.Comment;
import com.photoshare.model.User;
import com.photoshare.utility.Constants;
import com.photoshare.utility.Utility;
import com.photoshare.wrappers.AlbumList;
import com.photoshare.wrappers.FriendList;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

/**
 * @author Amol
 * 
 *         provides services related to users
 * 
 */
@Path("/users")
public class UserServices {

	private static UserBizLogic userBizLogic = new UserBizLogic();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(User user) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			userBizLogic.createUser(user);
			responseDTO.setPayload(user);
			responseDTO.setSuccess(true);
			responseDTO.setMessage(Constants.USER_CREATED_SUCCESSFULLY);
		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return Utility.getResponse(responseDTO, 201);
	}

	@PUT
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUserInfo(User user, @PathParam("userId") int userId) {

		user.setId(userId);
		userBizLogic.updateUser(user);

		ResponseBuilder res = new ResponseBuilderImpl();
		res.status(200);
		return res.build();
	}

	@DELETE
	@Path("/{userId}")
	public Response deletePhoto(@PathParam("userId") int userId) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			userBizLogic.deleteUser(userId);
			responseDTO.setSuccess(true);
			responseDTO.setMessage(Constants.USER_DELETE_SUCCESSFULLY);
		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(Constants.USER_DOES_NOT_FOUND);
			e.printStackTrace();
		}
		return Utility.getResponse(responseDTO, 204);
	}

	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("userId") int userId) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			userBizLogic.getUserById(userId);
			responseDTO.setMessage(Constants.USER_FETCHED_SUCCESSFULLY);
			responseDTO.setSuccess(true);
		} catch (Exception e) {
			responseDTO.setMessage(e.getMessage());
			responseDTO.setSuccess(false);
		}

		return Utility.getResponse(responseDTO);

	}

	@GET
	@Path("/{userId}/albums")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAlbums(@PathParam("userId") int userId) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			AlbumList albums = userBizLogic.getAllAlbumsByUserId(userId);
			responseDTO.setPayload(albums);
			responseDTO.setSuccess(true);
			responseDTO.setMessage(Constants.USER_ALBUMS_FETCHED_SUCCESSFULLY);
		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Utility.getResponse(responseDTO);
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLogin(User user) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			user = userBizLogic.getUserByCredentials(user.getEmailId(),
					user.getPassword());
			responseDTO.setSuccess(true);
			responseDTO.setMessage("Welcome " + user.getFirstname());
			responseDTO.setPayload(user);
		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(Constants.USER_DOES_NOT_EXISTS);
			System.out.println("-----" +responseDTO.getMessage() + "-----");
			e.printStackTrace();
		}

		return Utility.getResponse(responseDTO);
	}

	@GET
	@Path("/{userId}/friends")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFriendsList(@PathParam("userId") int userId) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			FriendList friendList = new FriendList();
			List<String> frndsList = userBizLogic.getFriendsByUserId(userId);
			String[] frndArr = new String[frndsList.size()];

			frndArr = frndsList.toArray(frndArr);
			friendList.setFriendEmailIds(frndArr);

			responseDTO.setPayload(friendList);
			responseDTO.setSuccess(true);
			responseDTO.setMessage(Constants.FRIENDS_LIST_FETCHED_SUCCESSFULLY);
		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return Utility.getResponse(responseDTO);
	}

	@POST
	@Path("/{userId}/photos/{photoId}/comments/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComment(@PathParam("photoId") int photoId,
			@PathParam("userId") int userId, String commentStr) {

		ResponseDTO responseDTO = new ResponseDTO();
		try {

			Comment comment = userBizLogic.addCommentToPhoto(userId, photoId,
					commentStr);

			responseDTO.setPayload(comment);
			responseDTO.setSuccess(true);
			responseDTO.setMessage(Constants.COMMENT_SAVED_SUCCESSFULLY);
			
		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Utility.getResponse(responseDTO, 201);
	}

}
