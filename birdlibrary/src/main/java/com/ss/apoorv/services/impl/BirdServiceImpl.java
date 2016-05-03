package com.ss.apoorv.services.impl;

import com.ss.apoorv.bo.transport.BirdRequest;
import com.ss.apoorv.bo.transport.BirdResponse;
import com.ss.apoorv.exceptions.WebApplicationException;
import com.ss.apoorv.manager.BirdManager;
import com.ss.apoorv.services.BirdService;
import com.ss.apoorv.util.SSErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by apoorv on 03/05/16.
 */
public class BirdServiceImpl implements BirdService {

    @Autowired
    private BirdManager birdManager;

    @Override
    public List<BirdResponse> getBirds(HttpHeaders httpHeaders) {
        // No pagination as per the requirement
        return birdManager.getAllBirdsData();
    }

    @Override
    public BirdResponse getBird(HttpHeaders httpHeaders, String birdId) {
        // Check if bird is null
        if (birdId == null) {
            throw new WebApplicationException(SSErrorEnum.BIRD_NOT_FOUND);
        }
        return birdManager.getBirdData(birdId);
    }

    @Override
    public Response saveBird(HttpHeaders httpHeaders, BirdRequest birdRequest) {
        // Check if request meets prerequisites
        if (birdRequest == null || birdRequest.getName() == null || birdRequest.getFamily() == null
                || birdRequest.getContinents() == null || birdRequest.getContinents().size() == 0) {
            throw new WebApplicationException(SSErrorEnum.BAD_REQUEST);
        }
        return Response.status(Response.Status.CREATED).entity(birdManager.saveAndGetBirdData(birdRequest)).build();
    }

    @Override
    public Response deleteBird(HttpHeaders httpHeaders, String birdId) {
        // Check if bird id is not null
        if (birdId == null) {
            throw new WebApplicationException(SSErrorEnum.BIRD_NOT_FOUND);
        }
        return birdManager.deleteBird(birdId);
    }
}
