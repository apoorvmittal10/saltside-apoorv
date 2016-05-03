package com.ss.apoorv.handlers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by apoorv on 03/05/16.
 */
public class ThrowableMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
