package com.photoshare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

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

import org.json.JSONException;

import com.sun.jersey.api.client.ClientResponse.Status;
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

	private final String uploadFileHomeLocation = "/Users/maheshbingi/Documents/MS/Spring15/277/Project/images/";
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addPhoto(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
 
		String uniqueFileName = UUID.randomUUID() + "." + fileDetail.getFileName().split("\\.")[1];
		String uploadedFileLocation = uploadFileHomeLocation + uniqueFileName;
		
		PhotoDAO photoDAO = new PhotoDAO();
	 	
		PhotoMeta photo = new PhotoMeta();
		photo.setName(uniqueFileName);
		photo.setDate(new Date());
		photo.setLocation(uploadedFileLocation);
		photo.setURL(uploadedFileLocation);
		photo.setDescription(fileDetail.getFileName().split("\\.")[0]);
		photoDAO.insert(photo);
		
		// save file to disk
		writeToFile(uploadedInputStream, uploadedFileLocation);
		
		// form JSON response message
		org.json.JSONObject responseObj = new org.json.JSONObject();
		try {
			responseObj.put("success", true);
			responseObj.put("response-code", "201");
			responseObj.put("fileURI", uploadedFileLocation);
			responseObj.put("filename", uniqueFileName);
			responseObj.put("message", "File uploaded successfully..");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.status(201).entity(responseObj.toString()).build(); 
	}
	
	@PUT
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/{photoId}")
	public Response updatePhoto(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@PathParam("photoId") String uniqueFileName) {
		
		String uploadedFileLocation = uploadFileHomeLocation + uniqueFileName;
		
		PhotoDAO photoDAO = new PhotoDAO();
	 	
		PhotoMeta photo = new PhotoMeta();
		photo.setName(uniqueFileName);
		photo.setDate(new Date());
		photo.setLocation(uploadedFileLocation);
		photo.setURL(uploadedFileLocation);
		photo.setDescription(fileDetail.getFileName().split("\\.")[0]);
		photoDAO.update(photo);
		
		// save file to disk
		writeToFile(uploadedInputStream, uploadedFileLocation);
		
		// form JSON response message
		org.json.JSONObject responseObj = new org.json.JSONObject();
		try {
			responseObj.put("success", true);
			responseObj.put("response-code", "200");
			responseObj.put("fileURI", uploadedFileLocation);
			responseObj.put("filename", uniqueFileName);
			responseObj.put("message", "File updated successfully..");
			
		} catch (JSONException e) {
			return Response.status(200).build();
		}
		
		return Response.status(200).entity(responseObj.toString()).build();
	}

	@DELETE
	@Path("/{photoId}")
	public Response deletePhoto(@PathParam("photoId") String uniqueFileName) {
		
		PhotoDAO photoDAO = new PhotoDAO();
		PhotoMeta rPhoto = photoDAO.getObject(PhotoMeta.class, 1);
		
		photoDAO.delete(rPhoto);
		
		// form JSON response message
		org.json.JSONObject responseObj = new org.json.JSONObject();
		try {
			responseObj.put("success", true);
			responseObj.put("response-code", "200");
			responseObj.put("message", "File deleted successfully..");
			
		} catch (JSONException e) {
			return Response.status(200).build();
		}
		
		return Response.status(200).entity(responseObj.toString()).build();
	}

	@GET
	@Path("/{photoId}")
	@Produces("image/*")
	public Response getPhoto(@PathParam("photoId") String image) {

		File f = new File(uploadFileHomeLocation+image);
		
		org.json.JSONObject responseObj = new org.json.JSONObject();
		
		if(!f.exists()) {
			try {
				responseObj.put("success", false);
				responseObj.put("response-code", "404");
				responseObj.put("message", "File not found..");
				return Response.status(404).entity(responseObj.toString()).build();
			} catch (JSONException e) {
				return Response.status(Status.NOT_FOUND).build();
			}
			
		}

		try {
	        return Response.ok(new FileInputStream(f)).build();
	    } catch (FileNotFoundException e) {
	        return Response.status(Status.NOT_FOUND).build();
	    }
	}
	
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
}
