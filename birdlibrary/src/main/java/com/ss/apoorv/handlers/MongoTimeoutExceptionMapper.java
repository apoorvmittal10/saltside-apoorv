package com.ss.apoorv.handlers;

import com.mongodb.MongoTimeoutException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by apoorv on 03/05/16.
 */
public class MongoTimeoutExceptionMapper implements ExceptionMapper<MongoTimeoutException> {

	@Override
	public Response toResponse(MongoTimeoutException exception) {
		return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
	}

}
