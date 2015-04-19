package com.photoshare;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.photoshare.db.HibernateUtil;

@Path("/photos")
public class PhotoServices {

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String addPhoto() {
		
		return "test";
	}

	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{photoId}")
	public String updatePhoto(@PathParam("photoId") int photoId) {
		return "test + " + photoId;
	}

	@DELETE
	@Path("/{photoId}")
	public void deletePhoto(@PathParam("photoId") int photoId) {

	}

	@GET
	@Path("/{photoId}")
	public String getPhoto(@PathParam("photoId") int photoId) {
		String url = "";
		return url;
	}

}
