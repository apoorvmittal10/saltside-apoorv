package com.ss.apoorv.handlers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.text.ParseException;

/**
 * Created by apoorv on 03/05/16.
 */
public class ParseExceptionMapper implements ExceptionMapper<ParseException> {

    @Override
    public Response toResponse(ParseException e) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
