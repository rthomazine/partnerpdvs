package com.github.rthomazine.partnerpdv.api;

import com.github.rthomazine.partnerpdv.api.exception.DocumentException;
import com.github.rthomazine.partnerpdv.api.exception.NotFoundException;
import com.github.rthomazine.partnerpdv.api.model.PartnerPdv;
import com.github.rthomazine.partnerpdv.api.model.PartnersPdvs;
import com.github.rthomazine.partnerpdv.service.PartnersPdvsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Retrieve all partners from database")
    @PostMapping(path = "/partners", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public PartnerPdv createPartnerPdv(@RequestBody PartnerPdv partnerPdv) throws DocumentException, DuplicateKeyException {
        return partnersPdvsService.createPartnerPdv(partnerPdv);
    }

    @ApiOperation(value = "Create and save a new partner on database")
    @GetMapping(path = "/partners", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    public PartnersPdvs getAllPartnersPdvs() {
        return partnersPdvsService.getAllPdvs();
    }

    @ApiOperation(value = "Retrieve a partner from database given its id")
    @GetMapping(path = "/partners/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    public PartnerPdv getPartnerPdvById(@PathVariable String id) throws NotFoundException {
        return partnersPdvsService.getPdvById(id);
    }

    @ApiOperation(value = "Search the nearest partner within coverage area given a location")
    @GetMapping(path = "/partners/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    public PartnerPdv searchPartnerPdvByLocation(@RequestParam double longitude, @RequestParam double latitude) throws NotFoundException {
        return partnersPdvsService.searchNearestPartner(longitude, latitude);
    }

}
