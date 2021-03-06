package com.photoshare;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.photoshare.bizlogic.PhotoshareServiceBizLogic;
import com.photoshare.dto.ResponseDTO;
import com.photoshare.model.PhotoMeta;
import com.photoshare.utility.Constants;
import com.photoshare.utility.Utility;
import com.photoshare.wrappers.PhotoList;

/**
 * @author Amol
 * 
 *         provides services related to the photosharing application
 */
@Path("/photoshare")
public class PhotoshareServices {

	PhotoshareServiceBizLogic photoserviceBizLogic = new PhotoshareServiceBizLogic();

	/**
	 * 
	 * friendEmailIds : amol@gmail.com,mahesh@gmail.com
	 * 
	 * @param albumId
	 * @param friendEmailIds
	 * @return
	 */
	@PUT
	@Path("/albums/{albumId}/friends/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response shareAlbum(@PathParam("albumId") int albumId,
			@QueryParam("friendEmailIds") String friendEmailIds) {
		ResponseDTO responseDTO = new ResponseDTO();
		
		try {
			photoserviceBizLogic.shareAlbumToUsers(albumId,
					friendEmailIds.split(","));
			responseDTO.setMessage(Constants.USERS_ADDED_TO_ALBUM);
			responseDTO.setSuccess(true);
		} catch (Exception e) {
			responseDTO.setMessage(e.getMessage());
			responseDTO.setSuccess(false);
			e.printStackTrace();
		}

		return Utility.getResponse(responseDTO);
	}

	@PUT
	@Path("/revoke/albums/{albumId}/friends")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response revokeAlbum(@PathParam("albumId") int albumId,
			@QueryParam("friendEmailIds") String friendEmailIds) {
		ResponseDTO responseDTO = new ResponseDTO();

		try {
			photoserviceBizLogic.revokeUsersFromAlbum(albumId,
					friendEmailIds.split(","));
			responseDTO.setMessage(Constants.USERS_REMOVED_FROM_ALBUM);
			responseDTO.setSuccess(true);
		} catch (Exception e) {
			responseDTO.setMessage(e.getMessage());
			responseDTO.setSuccess(false);
			e.printStackTrace();
		}

		return Utility.getResponse(responseDTO);

	}

	@PUT
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPhotosBySearchText(
			@QueryParam("searchTxt") String searchTxt,
			@QueryParam("albumIds") String albumIds) {

		// TODO use userid for checkin the user rights on the album

		ResponseDTO responseDTO = new ResponseDTO();

		try {
			List<PhotoMeta> photos = photoserviceBizLogic
					.getPhotosBySearchText(searchTxt, albumIds);
			PhotoList photoList = new PhotoList();
			photoList.setPhotos(photos);
			responseDTO.setPayload(photoList);
			responseDTO.setMessage(Constants.SEARCH_COMPLETE);
			responseDTO.setSuccess(true);
		} catch (Exception e) {
			responseDTO.setMessage(e.getMessage());
			responseDTO.setSuccess(false);
			e.printStackTrace();
		}

		return Utility.getResponse(responseDTO);
	}

}
