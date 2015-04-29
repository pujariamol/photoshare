package com.photoshare;

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
import com.photoshare.model.AlbumList;
import com.photoshare.model.User;
import com.photoshare.utility.Constants;
import com.photoshare.utility.Utility;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

/**
 * @author Amol
 * 
 *         provides services related to users
 * 
 */
@Path("/users")
public class UserServices {

	// private static UserDAO userDAO = UserDAO.getInstance();
	private static UserBizLogic userBizLogic = new UserBizLogic();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User user) {

		userBizLogic.createUser(user);

		ResponseBuilder res = new ResponseBuilderImpl();
		res.status(201);
		return res.build();
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
		User user = new User();
		user.setId(userId);

		userBizLogic.deleteUser(user);

		ResponseBuilder res = new ResponseBuilderImpl();
		res.status(200);
		return res.build();
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
			AlbumList albums = userBizLogic.getAlbumsByUserId(userId);
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

		user = userBizLogic.getUserByCredentials(user.getEmailId(),
				user.getPassword());

		if (null == user) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(Constants.USER_DOES_NOT_EXISTS);
		} else {
			responseDTO.setSuccess(true);
			responseDTO.setMessage("Welcome " + user.getFirstname());
			responseDTO.setPayload(user);
		}
		return Utility.getResponse(responseDTO);
	}

}
