package com.github.rthomazine.partnerpdv.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.rthomazine.partnerpdv.serializer.GeoJsonMultiPolygonSerializer;
import com.github.rthomazine.partnerpdv.serializer.GeoJsonPointSerializer;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartnerPdv {

    private String id;
    private String document;
    @JsonAlias("ownerName")
    private String owner;
    @JsonAlias("tradingName")
    private String trading;
    @JsonSerialize(using = GeoJsonMultiPolygonSerializer.class)
    private GeoJsonMultiPolygon coverageArea;
    @JsonSerialize(using = GeoJsonPointSerializer.class)
    private GeoJsonPoint address;

}
