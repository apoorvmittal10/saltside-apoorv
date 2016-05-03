package com.ss.apoorv.util;

import javax.ws.rs.core.Response;

/**
 * Created by apoorv on 03/05/16.
 */
public enum SSErrorEnum {

    BIRD_NOT_FOUND(Response.Status.NOT_FOUND),
    BAD_REQUEST(Response.Status.BAD_REQUEST);

    private Response.Status httpStatus;

    SSErrorEnum(Response.Status httpStatus){
        this.httpStatus = httpStatus;
    }

    public Response.Status getHttpStatus() {
        return httpStatus;
    }
}
