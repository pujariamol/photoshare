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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONException;

import com.photoshare.bizlogic.AlbumBizLogic;
import com.photoshare.bizlogic.CommentBizLogic;
import com.photoshare.bizlogic.PhotoBizLogic;
import com.photoshare.dao.PhotoDAO;
import com.photoshare.dto.ResponseDTO;
import com.photoshare.model.Album;
import com.photoshare.model.PhotoMeta;
import com.photoshare.utility.Constants;
import com.photoshare.utility.Utility;
import com.photoshare.wrappers.CommentList;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;
import com.sun.jersey.multipart.FormDataParam;

/**
 * @author Amol
 * 
 *         Photoservice provides all services related to photos
 * 
 */
@Path("/photos")
public class PhotoServices {

	/**
	 * 
	 */

	private final String uploadFileHomeLocation = "C:/photoshareImages/";

	private PhotoDAO photoDAO = PhotoDAO.getInstance();
	private static PhotoBizLogic photoBizLogic = new PhotoBizLogic();

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addPhoto(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@QueryParam("albumId") int albumId) {

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

		Album album = new AlbumBizLogic().getAlbumById(albumId);

		photo.setAlbum(album);

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

	/**
	 * @param photoId
	 * @return
	 */
	@DELETE
	@Path("/{photoId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePhoto(@PathParam("photoId") int photoId) {

		ResponseDTO responseDTO = new ResponseDTO();

		try {
			photoBizLogic.deletePhotos(photoId);
			responseDTO.setSuccess(true);
			responseDTO.setMessage(Constants.PHOTO_DELETE_SUCCESSFULLY);
		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(Constants.PHOTO_DOES_NOT_FOUND);
			e.printStackTrace();
		}
		return Utility.getResponse(responseDTO, 204);
	}

	@GET
	@Path("/{photoId}")
	@Produces("image/*")
	public Response getPhoto(@PathParam("photoId") int photoId) {

		PhotoMeta photoMetaData = photoBizLogic.getPhotoById(photoId);
		File f = new File(photoMetaData.getURL());

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

	@GET
	@Path("/{photoId}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCommentsByPhotoId(@PathParam("photoId") int photoId) {
		ResponseDTO responseDTO = new ResponseDTO();
		CommentBizLogic commentBizLogic = new CommentBizLogic();
		CommentList commentList = new CommentList();

		try {
			commentList.setComments(commentBizLogic
					.getCommentsByPhotoId(photoId));
			responseDTO.setPayload(commentList);
			responseDTO.setSuccess(true);
			responseDTO.setMessage(Constants.COMMENTS_FETCHED_SUCCESSFULLY);

		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return Utility.getResponse(responseDTO);
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
