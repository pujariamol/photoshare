package com.photoshare.utility;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.photoshare.dto.ResponseDTO;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;

public class Utility {

	public static Response getResponse(ResponseDTO responseDTO) {
		ResponseBuilder res = new  ResponseBuilderImpl();
		res.status(responseDTO.isSuccess()?200:500);
		res.entity(responseDTO);
		return res.build();
	}

	
}
