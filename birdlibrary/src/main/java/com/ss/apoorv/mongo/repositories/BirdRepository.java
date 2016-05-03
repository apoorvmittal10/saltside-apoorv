package com.ss.apoorv.mongo.repositories;

import com.ss.apoorv.mongo.model.BirdModel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by apoorv on 03/05/16.
 */
public interface BirdRepository extends MongoRepository<BirdModel, String> {

    Long deleteById(String id);
}
