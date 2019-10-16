package com.github.rthomazine.partnerpdv.persistence;

import lombok.Builder;
import lombok.Data;
import mil.nga.sf.geojson.MultiPolygon;
import mil.nga.sf.geojson.Point;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "pdvs")
public class PartnerPdv {

    @Id
    private String id;
    @Indexed(unique = true)
    private String document;
    private String owner;
    private String trading;
    private MultiPolygon coverageArea;
    private Point address;

}
