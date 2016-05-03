package com.ss.apoorv.exceptions;

import com.ss.apoorv.util.SSErrorEnum;

import javax.ws.rs.core.Response.Status;

/**
 * Created by apoorv on 03/05/16.
 */
public class WebApplicationException extends RuntimeException {

	private final Status httpStatus;
	
	public WebApplicationException(SSErrorEnum error){
		this(error.getHttpStatus());
	}

	public WebApplicationException(Status httpStatus){
		this.httpStatus = httpStatus;
	}

	public WebApplicationException(SSErrorEnum error, Throwable throwable){
		this(error.getHttpStatus(), throwable);
	}
	
	public WebApplicationException(Status httpStatus, Throwable throwable){
		super(throwable);
		this.httpStatus = httpStatus;
	}

	public Status getHttpStatus() {
		return httpStatus;
	}

}
