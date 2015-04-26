package com.photoshare;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.photoshare.dao.AlbumDAO;
import com.photoshare.dao.AlbumSharedUsersDAO;
import com.photoshare.dao.UserDAO;
import com.photoshare.model.Album;
import com.photoshare.model.AlbumSharedUsers;
import com.photoshare.model.User;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

/**
 * @author Amol
 * 
 *         provides services related to the photosharing application
 */
@Path("/photoshare")
public class PhotoshareServices {

	private static AlbumDAO albumDAO = AlbumDAO.getInstance();
	private static UserDAO userDAO = UserDAO.getInstance();
	private static AlbumSharedUsersDAO albumSharedUsersDAO = AlbumSharedUsersDAO.getInstance();

	@PUT
	@Path("/albums/{albumId}/users/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response shareAlbum(@PathParam("albumId") int albumId,
			@PathParam("userId") int userId, AlbumSharedUsers albumSharedUsers) {
		User user = userDAO.getUserById(userId);
		Album album = albumDAO.getAlbumById(albumId);
		
		if(null == albumSharedUsers )
			albumSharedUsers = new AlbumSharedUsers();
		
		albumSharedUsers.setUser(user);
		albumSharedUsers.setAlbum(album);
		
		albumSharedUsersDAO.insert(albumSharedUsers);
		
		ResponseBuilder res = new ResponseBuilderImpl();
		res.status(200);
		return res.build();
	}

	@PUT
	@Path("/revoke/albums/{albumId}/users/{userIds}")
	public void revokeAlbum(@PathParam("albumId") int albumId,
			@PathParam("userIds") String userIds) {

	}

}
