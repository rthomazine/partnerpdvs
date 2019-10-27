package com.github.rthomazine.partnerpdv.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class PdvCustomQueriesRepository {

    @Autowired
    private MongoOperations mongoOperations;

    public Optional<Pdv> queryNearestPartnerWithinCoverageArea(double longitude,
                                                               double latitude) {
        Optional<Pdv> pdv = Optional.empty();
        AggregationResults<Pdv> results = mongoOperations.aggregate(
                newAggregation(Pdv.class,
                        match(where("coverageArea").intersects(new GeoJsonPoint(longitude, latitude))),
                        lookup("partners", "partner_id", "_id", "partner"),
                        limit(1)
                ), Pdv.class);
        if (results.iterator().hasNext())
            pdv = Optional.of(results.getUniqueMappedResult());
        return pdv;
    }

}
