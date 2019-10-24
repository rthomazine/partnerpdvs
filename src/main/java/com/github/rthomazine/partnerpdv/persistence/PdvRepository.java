package com.github.rthomazine.partnerpdv.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PdvRepository extends MongoRepository<Pdv, String> {

    Optional<Pdv> findByDocument(String document);

}
