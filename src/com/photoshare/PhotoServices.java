package com.photoshare;

import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.photoshare.dao.PhotoDAO;
import com.photoshare.model.PhotoMeta;

/**
 * @author Amol
 * 
 * Photoservice provides all services related to photos
 *
 */
@Path("/photos")
public class PhotoServices {

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String addPhoto() {
		PhotoDAO photoDAO = new PhotoDAO();

		PhotoMeta photo = new PhotoMeta();
		photo.setName("Test pic");
		photo.setDate(new Date());
		photo.setLocation("San Jose");
		photo.setDescription("Test description");
		
		photoDAO.insert(photo);
		System.out.println("---------------------------successfully inserted");
		
		photo.setDescription("---------------------------Description changed");
		photoDAO.update(photo);
		System.out.println("---------------------------successfully updated");
		
		PhotoMeta rPhoto = photoDAO.getObject(PhotoMeta.class, 1);
		
		System.out.println("---------------------------Name = " + rPhoto.getName() + "\n Description = " + rPhoto.getDescription() );
		
		photoDAO.delete(rPhoto);
		System.out.println("---------------------------Record deleted successfully");
		
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
