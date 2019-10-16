package com.github.rthomazine.partnerpdv.api;

import com.github.rthomazine.partnerpdv.api.exception.NotFoundException;
import com.github.rthomazine.partnerpdv.persistence.PartnerPdv;
import com.github.rthomazine.partnerpdv.persistence.PartnerPdvRepository;
import io.swagger.annotations.Api;
import mil.nga.sf.geojson.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
@Api(tags = "PartnerPDV")
public class PartnerPdvApi {

    @Autowired
    private PartnerPdvRepository partnerPdvRepository;

    @PostMapping(path = "/partner/pdv", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public PartnerPdv createPartnerPdv(@RequestBody PartnerPdv partnerPdv) throws DuplicateKeyException {
        return partnerPdvRepository.save(partnerPdv);
    }

    @GetMapping(path = "/partner/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(code = HttpStatus.OK)
    public PartnerPdv getPartnerPdvById(@PathVariable String id) throws NotFoundException {
        Optional<PartnerPdv> partnerPdv = partnerPdvRepository.findById(id);
        return partnerPdv.orElseThrow(() -> new NotFoundException(String.format("No partner pdv found with id %s", id)));
    }

    @GetMapping(path = "/partner/search", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(code = HttpStatus.OK)
    public PartnerPdv searchPartnerPdvByLocation(@RequestBody Position location) throws NotFoundException {
        //TODO
        return null;
    }

}
