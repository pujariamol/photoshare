package com.photoshare;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import javassist.tools.rmi.ObjectNotFoundException;

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

import com.photoshare.dao.AlbumDAO;
import com.photoshare.dao.PhotoDAO;
import com.photoshare.dto.ResponseDTO;
import com.photoshare.model.Album;
import com.photoshare.model.PhotoMeta;
import com.photoshare.utility.Constants;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

/**
 * @author Amol
 * 
 *         Provides services related to album
 */
@Path("/albums")
public class AlbumServices {

	private static AlbumDAO albumDAO = AlbumDAO.getInstance();

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
	public Response createAlbum(Album album) {
		album.setDateOfCreation(new Date());
		albumDAO.insert(album);

		ResponseBuilder res = new ResponseBuilderImpl();

		res.status(201);
		res.entity(
				new ResponseDTO(true, Constants.ALBUM_CREATED_SUCCESS)
						.toString()).type(MediaType.APPLICATION_JSON);
		try {
			URI createdURI = new URI("/albums/" + album.getId());
			res.contentLocation(createdURI);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return res.build();
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
		ResponseBuilder res = new ResponseBuilderImpl();

		Album album = new Album();
		album.setId(albumId);
		try {
			albumDAO.deleteAlbum(album);
			res.status(204);
		} catch (ObjectNotFoundException e) {
			res.status(400);
			res.entity(new ResponseDTO(false, Constants.ALBUM_NOT_FOUND));
			e.printStackTrace();
		}
		res.type(MediaType.APPLICATION_JSON);
		return res.build();
	}

	@GET
	@Path("/{albumId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Album getAlbumInfo(@PathParam("albumId") int albumId) {
		Album album = albumDAO.getAlbumById(albumId);
		return album;
	}
	
	
	/**
	 * [{
	 * id : "1",
	 * "name" : "photo1"
	 * },{
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
		
		System.out.println("--- " + photo.getName());
		System.out.println("---" + photoIds);
		
		photo.setAlbum(albumDAO.getAlbumById(albumId));

		PhotoDAO photoDao = PhotoDAO.getInstance();
		photoDao.update(photo);
		
		ResponseBuilder res = new ResponseBuilderImpl();
		res.status(200);
		return res.build();
		
	}
}
