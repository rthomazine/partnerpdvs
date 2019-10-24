package com.github.rthomazine.partnerpdv.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rthomazine.partnerpdv.api.exception.DocumentException;
import com.github.rthomazine.partnerpdv.api.model.PartnersPdvs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Component
public class PdvsJsonLoaderService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PartnersPdvsService partnersPdvsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PdvsJsonLoaderService.class);

    @PostConstruct
    public void loadPdvs() throws IOException {
        TypeReference<PartnersPdvs> typeReference = new TypeReference<PartnersPdvs>() {};

        InputStream inputStream = TypeReference.class.getResourceAsStream("/pdvs.json");
        PartnersPdvs partnersPdvs = objectMapper.readValue(inputStream, typeReference);

        partnersPdvs.getPdvs().forEach(p -> {
            try {
                partnersPdvsService.createPartnerPdv(p);
            } catch (DocumentException | DuplicateKeyException e) {
                LOGGER.warn("Could not create partner pdv: " + e.getMessage());
            }
        });
    }

}
