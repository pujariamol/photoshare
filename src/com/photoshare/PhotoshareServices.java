package com.photoshare;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/photoshare")
public class PhotoshareServices {

	@PUT
	@Path("/albums/{albumId}/photos/{photoIds}")
	public void addPhotoToAlbum(@PathParam("albumId") int albumId,
			@PathParam("photoIds") String photoIds) {
		
	}
	
	@PUT
	@Path("/albums/{albumId}/users/{userIds}")
	public void shareAlbum(@PathParam("albumId") int albumId,
			@PathParam("userIds") String userIds) {
		
	}
	
	@PUT
	@Path("/revoke/albums/{albumId}/users/{userIds}")
	public void revokeAlbum(@PathParam("albumId") int albumId,
			@PathParam("userIds") String userIds) {
		
	}

}
