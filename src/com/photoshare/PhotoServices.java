package com.photoshare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
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
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addPhoto(@FormDataParam("file") InputStream uploadedInputStream) {
 
		String uploadedFileLocation = "/Users/maheshbingi/Documents/MS/Spring15/277/Project/abcd.jpg";
		
		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);
		String output = "File uploaded to : " + uploadedFileLocation;
		 
		return Response.status(200).entity(output).build(); 
	}
	
	/*
	public Response addPhoto(@FormDataParam("param1") String param) {
	 
		String output = "File uploaded to : " + param;
		 
		return Response.status(200).entity(output).build();
		
		
		
		
		
		
		PhotoDAO photoDAO = new PhotoDAO();
	 	
		PhotoMeta photo = new PhotoMeta();
		photo.setName("Test pic");
		photo.setDate(new Date());
		photo.setLocation("San Jose");
		photo.setDescription("Test description");
		// SET image type (jpeg,png)
		// SET imagePath
		photoDAO.insert(photo);
		
		// Read Bytes data image and form image and store on disk
	
	
		
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
	*/
	
	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
		String uploadedFileLocation) {
 
		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
 
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
 
			e.printStackTrace();
		}
 
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
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response getPhoto() {

		String output = "Called GET with ID: ";
		
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/photoId")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response getPostPhoto(@FormDataParam("photoId") int photoId) {

		String output = "Called GET with ID: "+ photoId;
		
		return Response.status(200).entity(output).build();
	}

}
