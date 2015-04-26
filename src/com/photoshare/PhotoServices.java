package com.photoshare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
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
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONException;

import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;
import com.sun.jersey.multipart.FormDataParam;
import com.photoshare.dao.PhotoDAO;
import com.photoshare.dto.ResponseDTO;
import com.photoshare.model.Comment;
import com.photoshare.model.PhotoMeta;

/**
 * @author Amol
 * 
 *         Photoservice provides all services related to photos
 * 
 */
@Path("/photos")
public class PhotoServices {

	private final String uploadFileHomeLocation = "C:/photoshareImages/";

	private PhotoDAO photoDAO = PhotoDAO.getInstance();

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addPhoto(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String uniqueFileName = UUID.randomUUID() + "."
				+ fileDetail.getFileName().split("\\.")[1];
		String uploadedFileLocation = uploadFileHomeLocation + uniqueFileName;

		PhotoDAO photoDAO = PhotoDAO.getInstance();

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

		ResponseBuilder res = new ResponseBuilderImpl();

		try {
			responseObj.put("success", true);
			responseObj.put("response-code", "201");
			responseObj.put("fileURI", uploadedFileLocation);
			responseObj.put("filename", uniqueFileName);
			responseObj.put("message", "File uploaded successfully..");

			/*
			 * Amol changes This might save creating json Little change let me
			 * know if this will serve the purpose
			 */
			ResponseDTO resDTO = new ResponseDTO(true,
					"File uploaded successfully");

			res.status(201);
			res.contentLocation(new URI(uploadedFileLocation));
			res.entity(resDTO);
			res.type(MediaType.APPLICATION_JSON);
			// =============================================================

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res.build();
	}

	@PUT
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/{photoId}")
	public Response updatePhoto(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@PathParam("photoId") String uniqueFileName) {

		String uploadedFileLocation = uploadFileHomeLocation + uniqueFileName;

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

		PhotoMeta rPhoto = photoDAO.getPhotoById(1);

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

		File f = new File(uploadFileHomeLocation + image);

		org.json.JSONObject responseObj = new org.json.JSONObject();

		if (!f.exists()) {
			try {
				responseObj.put("success", false);
				responseObj.put("response-code", "404");
				responseObj.put("message", "File not found..");
				return Response.status(404).entity(responseObj.toString())
						.build();
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

	
	@POST
	@Path("/{photoId}/comments/")
	public Response addComment(@PathParam("photoId") int photoId, Comment comment){
		
		comment.setPhoto(photoDAO.getPhotoById(photoId));
	
			
		ResponseBuilder res = new ResponseBuilderImpl();
		res.status(201);
		return res.build();
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
