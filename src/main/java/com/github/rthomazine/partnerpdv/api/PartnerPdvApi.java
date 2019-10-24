package com.github.rthomazine.partnerpdv.api;

import com.github.rthomazine.partnerpdv.api.exception.DocumentException;
import com.github.rthomazine.partnerpdv.api.exception.NotFoundException;
import com.github.rthomazine.partnerpdv.api.model.PartnerPdv;
import com.github.rthomazine.partnerpdv.api.model.PartnersPdvs;
import com.github.rthomazine.partnerpdv.service.PartnersPdvsService;
import io.swagger.annotations.Api;
import mil.nga.sf.geojson.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
@Api(tags = "PartnerPDV")
public class PartnerPdvApi {

    @Autowired
    private PartnersPdvsService partnersPdvsService;

    @PostMapping(path = "/partners", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public PartnerPdv createPartnerPdv(@RequestBody PartnerPdv partnerPdv) throws DocumentException, DuplicateKeyException {
        return partnersPdvsService.createPartnerPdv(partnerPdv);
    }

    @GetMapping(path = "/partners", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(code = HttpStatus.OK)
    public PartnersPdvs getAllPartnersPdvs() {
        return partnersPdvsService.getAllPdvs();
    }

    @GetMapping(path = "/partners/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(code = HttpStatus.OK)
    public PartnerPdv getPartnerPdvById(@PathVariable String id) throws NotFoundException {
        return partnersPdvsService.getPdvById(id);
    }

    @GetMapping(path = "/partners/search", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(code = HttpStatus.OK)
    public PartnerPdv searchPartnerPdvByLocation(@RequestBody Position location) throws NotFoundException {
        //TODO

        return null;
    }

}
