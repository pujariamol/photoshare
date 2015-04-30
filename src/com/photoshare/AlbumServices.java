package com.photoshare;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

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

import com.photoshare.bizlogic.AlbumBizLogic;
import com.photoshare.dao.AlbumDAO;
import com.photoshare.dao.PhotoDAO;
import com.photoshare.dto.ResponseDTO;
import com.photoshare.model.Album;
import com.photoshare.model.PhotoMeta;
import com.photoshare.utility.Constants;
import com.photoshare.utility.Utility;
import com.photoshare.wrappers.PhotoList;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

/**
 * @author Amol
 * 
 *         Provides services related to album
 */
@Path("/albums")
public class AlbumServices {

	/**
	 * 
	 */
	
	private static AlbumDAO albumDAO = AlbumDAO.getInstance();
	private static AlbumBizLogic albumBizLogic = new AlbumBizLogic();

	/**
	 * {name:'test',
	 * 
	 * photos:{name:'photos1'} }
	 * 
	 * @param album
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAlbum(Album album) {
		System.out.println("Inside Create album");
		ResponseDTO responseDTO = new ResponseDTO();
		ResponseBuilder res = new ResponseBuilderImpl();

		try {
			albumBizLogic.createAlbum(album);
			responseDTO.setMessage(Constants.ALBUM_CREATED_SUCCESS);
			responseDTO.setSuccess(true);
			responseDTO.setPayload(album);
			URI createdURI = new URI("/albums/" + album.getId());
			res.contentLocation(createdURI);
		} catch (URISyntaxException e) {
			responseDTO.setMessage(e.getMessage());
			responseDTO.setSuccess(false);
			e.printStackTrace();
		} catch (Exception e) {
			responseDTO.setMessage(e.getMessage());
			responseDTO.setSuccess(false);
			e.printStackTrace();
		}

		return Utility.getResponse(responseDTO,201);
	}

	@PUT
	@Path("/{albumId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAlbumInfo(Album album,
			@PathParam("albumId") int albumId) {

		album.setId(albumId);
		album.setDateOfModification(new Date());
		albumDAO.update(album);

		ResponseBuilder res = new ResponseBuilderImpl();
		res.status(200);
		res.entity(
				new ResponseDTO(true, Constants.ALBUM_UPDATED_SUCCESS)
						.toString()).type(MediaType.APPLICATION_JSON);

		return res.build();
	}

	@DELETE
	@Path("/{albumId}")
	public Response deleteAlbum(@PathParam("albumId") int albumId) {
		ResponseDTO responseDTO = new ResponseDTO();
		
		try {
			albumBizLogic.deleteAlbum(albumId);
			responseDTO.setSuccess(true);
			responseDTO.setMessage(Constants.ALBUM_DELETE_SUCCESSFULLY);
		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(Constants.ALBUM_NOT_FOUND);
			e.printStackTrace();
		}
		return Utility.getResponse(responseDTO,204);
	}

	@GET
	@Path("/{albumId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Album getAlbumInfo(@PathParam("albumId") int albumId) {
		Album album = albumDAO.getAlbumById(albumId);
		return album;
	}

	/**
	 * [{ id : "1", "name" : "photo1" },{
	 * 
	 * }]
	 * 
	 * 
	 * @param albumId
	 * @param photoIds
	 * @param photosList
	 */

	@PUT
	@Path("/{albumId}/photos/{photoId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPhotoToAlbum(@PathParam("albumId") int albumId,
			@PathParam("photoIds") String photoIds, PhotoMeta photo) {

		photo.setAlbum(albumDAO.getAlbumById(albumId));

		PhotoDAO photoDao = PhotoDAO.getInstance();
		photoDao.update(photo);

		ResponseBuilder res = new ResponseBuilderImpl();
		res.status(200);
		return res.build();

	}
	
	@GET
	@Path("/{albumId}/photos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPhotos(@PathParam("albumId") int albumId) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			PhotoList photos = albumBizLogic.getPhotosByAlbumId(albumId);
			responseDTO.setPayload(photos);
			responseDTO.setSuccess(true);
			responseDTO.setMessage(Constants.PHOTOS_ALBUM_FETCHED_SUCCESSFULLY);
		} catch (Exception e) {
			responseDTO.setSuccess(false);
			responseDTO.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return Utility.getResponse(responseDTO);
	}
}
