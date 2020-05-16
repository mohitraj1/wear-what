package com.mohit.wearwhat.dressservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mohit.wearwhat.dressservice.model.Garment;

public interface GarmentRepository extends MongoRepository<Garment, String> {

}
