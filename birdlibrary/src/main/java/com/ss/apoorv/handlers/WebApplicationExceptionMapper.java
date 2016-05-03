package com.ss.apoorv.handlers;

import com.ss.apoorv.exceptions.WebApplicationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by apoorv on 03/05/16.
 */
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

	@Override
	public Response toResponse(WebApplicationException exception) {
		return Response.status(exception.getHttpStatus()).build();
	}

}
