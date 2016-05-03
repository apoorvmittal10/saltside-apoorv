package com.ss.apoorv.services;

import com.ss.apoorv.bo.transport.BirdRequest;
import com.ss.apoorv.bo.transport.BirdResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * Created by apoorv on 03/05/16.
 */
@Path("birds")
public interface BirdService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<BirdResponse> getBirds(@Context HttpHeaders httpHeaders);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    BirdResponse getBird(@Context HttpHeaders httpHeaders, @PathParam("id") String birdId);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response saveBird(@Context HttpHeaders httpHeaders, BirdRequest birdRequest);

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteBird(@Context HttpHeaders httpHeaders, @PathParam("id") String birdId);
}
