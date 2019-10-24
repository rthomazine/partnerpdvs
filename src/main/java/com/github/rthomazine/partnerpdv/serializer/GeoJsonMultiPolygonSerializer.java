package com.github.rthomazine.partnerpdv.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.io.IOException;

public class GeoJsonMultiPolygonSerializer extends JsonSerializer<GeoJsonMultiPolygon> {

    @Override
    public void serialize(GeoJsonMultiPolygon geoJsonMultiPolygon, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", geoJsonMultiPolygon.getType());
        jsonGenerator.writeArrayFieldStart("coordinates");
        for (GeoJsonPolygon geoJsonPolygon : geoJsonMultiPolygon.getCoordinates()) {
            jsonGenerator.writeStartArray();
            for (GeoJsonLineString geoJsonLineString : geoJsonPolygon.getCoordinates()) {
                jsonGenerator.writeStartArray();
                for (Point point : geoJsonLineString.getCoordinates()) {
                    jsonGenerator.writeObject(new double[]{point.getX(), point.getY()});
                }
                jsonGenerator.writeEndArray();
            }
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
