package com.github.rthomazine.partnerpdv.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PartnerPdvRepository extends MongoRepository<PartnerPdv, String> {

    Optional<PartnerPdv> findByDocument(String document);

}
