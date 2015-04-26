package com.photoshare;

import java.util.Date;

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

import com.photoshare.dao.UserDAO;
import com.photoshare.model.Album;
import com.photoshare.model.User;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

/**
 * @author Amol
 * 
 *         provides services related to users
 * 
 */
@Path("/users")
public class UserServices {

	private static UserDAO userDAO = UserDAO.getInstance();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User user) {
		userDAO.insert(user);

		ResponseBuilder res = new ResponseBuilderImpl();
		res.status(201);
		return res.build();
	}

	@PUT
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUserInfo(User user, @PathParam("userId") int userId) {

		user.setId(userId);
		userDAO.update(user);
		ResponseBuilder res = new ResponseBuilderImpl();
		res.status(200);
		return res.build();
	}

	@DELETE
	@Path("/{userId}")
	public Response deletePhoto(@PathParam("userId") int userId) {
		User user = new User();
		user.setId(userId);
		userDAO.delete(user);

		ResponseBuilder res = new ResponseBuilderImpl();
		res.status(200);
		return res.build();
	}

	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("userId") int userId) {
		User user = userDAO.getUserById(userId);
		return user;
	}

}
