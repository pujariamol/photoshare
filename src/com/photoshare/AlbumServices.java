package com.photoshare;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * @author Amol
 *
 * Provides services related to album
 */
@Path("/albums")
public class AlbumServices {

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{name}")
	public String addAlbum(@PathParam("name") String name) {
		return "test";
	}
	
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{name}")
	public String updateAlbumInfo(@PathParam("name") int name) {
		return "test + " + name;
	}
	
	@DELETE
	@Path("/{albumId}")
	public void deleteAlbum(@PathParam("albumId") int albumId){
		
	}
	
	@GET
	@Path("/{albumId}")
	public String getAlbumInfo(@PathParam("albumId") int albumId){
		String url ="";
		return url;
	}
}
