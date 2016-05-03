package com.ss.apoorv.manager;

import com.ss.apoorv.bo.transport.BirdRequest;
import com.ss.apoorv.bo.transport.BirdResponse;
import com.ss.apoorv.exceptions.WebApplicationException;
import com.ss.apoorv.mongo.model.BirdModel;
import com.ss.apoorv.mongo.repositories.BirdRepository;
import com.ss.apoorv.util.SSErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apoorv on 03/05/16.
 */
public class BirdManager {

    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");

    @Autowired
    private BirdRepository birdRepository;

    public List<BirdResponse> getAllBirdsData() {
        // Get data from mongo without pagination
        List<BirdModel> birdModelList = birdRepository.findAll();
        // Check if bird list is not null or empty
        if (birdModelList == null || birdModelList.isEmpty()) {
            throw new WebApplicationException(SSErrorEnum.BIRD_NOT_FOUND);
        }

        // Initialize bird response list as birds exist
        List<BirdResponse> birdResponseList = new ArrayList<>();
        for (BirdModel birdModel: birdModelList) {
            birdResponseList.add(fillBirdDataFromBirdModel(birdModel));
        }
        return birdResponseList;
    }

    public BirdResponse getBirdData(String birdId) {
        // Get data from mongo for bird id
        BirdModel birdModel = birdRepository.findOne(birdId);

        // if bird data not found return 404
        if (birdModel == null) {
            throw new WebApplicationException(SSErrorEnum.BIRD_NOT_FOUND);
        }

        return fillBirdDataFromBirdModel(birdModel);
    }

    public BirdResponse saveAndGetBirdData(BirdRequest birdRequest) {
        // Save bird data in database
        BirdModel birdModel = birdRepository.save(fillBirdModelFromBirdRequest(birdRequest));
        // Return saved data response
        return fillBirdDataFromBirdModel(birdModel);
    }

    public Response deleteBird(String birdId) {
        // Delete bird from database
        Long result = birdRepository.deleteById(birdId);
        // Return 404 http status if bird doesn't exist in database prior deletion
        if (result == null || result == 0) {
            throw new WebApplicationException(SSErrorEnum.BIRD_NOT_FOUND);
        }
        // Return successful response
        return Response.ok().build();
    }

    private BirdResponse fillBirdDataFromBirdModel(BirdModel birdModel) {
        // Initialize bird response as bird exist
        BirdResponse birdResponse = new BirdResponse();

        birdResponse.setId(birdModel.getId());
        birdResponse.setName(birdModel.getName());
        birdResponse.setFamily(birdModel.getFamily());
        birdResponse.setContinents(birdModel.getContinents());
        // Date can never be null as defaulted to new Date()
        birdResponse.setAdded(sdf.format(birdModel.getAdded()));
        birdResponse.setVisible(birdModel.isVisible());

        return birdResponse;
    }

    private BirdModel fillBirdModelFromBirdRequest(BirdRequest birdRequest) {
        BirdModel birdModel = new BirdModel();

        birdModel.setName(birdRequest.getName());
        birdModel.setFamily(birdRequest.getFamily());
        birdModel.setContinents(birdRequest.getContinents());
        if (birdRequest.getAdded() != null) {
            try {
                birdModel.setAdded(sdf.parse(birdRequest.getAdded()));
            } catch (ParseException e) {
                throw new WebApplicationException(SSErrorEnum.BAD_REQUEST);
            }
        }
        if (birdRequest.getVisible() != null) {
            birdModel.setVisible(birdRequest.getVisible());
        }

        return birdModel;
    }
}
