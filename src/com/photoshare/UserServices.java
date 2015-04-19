package com.photoshare;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/users")
public class UserServices {


	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String addUser() {
		return "addUser Test";
	}
	
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{userId}")
	public String updateUserInfo(@PathParam("userId") int userId) {
		return "update user + " + userId;
	}
	
	@DELETE
	@Path("/{userId}")
	public void deletePhoto(@PathParam("userId") int userId){
		
	}
	
	@GET
	@Path("/{userId}")
	public String getPhoto(@PathParam("userId") int userId){
		String url ="";
		return url;
	}
	

	
}
