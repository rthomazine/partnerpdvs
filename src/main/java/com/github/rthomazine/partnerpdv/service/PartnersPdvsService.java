package com.github.rthomazine.partnerpdv.service;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.github.rthomazine.partnerpdv.api.exception.DocumentException;
import com.github.rthomazine.partnerpdv.api.exception.NotFoundException;
import com.github.rthomazine.partnerpdv.api.model.PartnerPdv;
import com.github.rthomazine.partnerpdv.api.model.PartnersPdvs;
import com.github.rthomazine.partnerpdv.persistence.Pdv;
import com.github.rthomazine.partnerpdv.persistence.PdvCustomQueriesRepository;
import com.github.rthomazine.partnerpdv.persistence.PdvRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnersPdvsService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PdvRepository pdvRepository;
    @Autowired
    private PdvCustomQueriesRepository pdvCustomQueriesRepository;

    private static CNPJValidator cnpjValidator = new CNPJValidator();

    public PartnerPdv createPartnerPdv(PartnerPdv partnerPdv) throws DocumentException, DuplicateKeyException {

        try {
            cnpjValidator.assertValid(partnerPdv.getDocument());
        } catch (InvalidStateException e) {
            throw new DocumentException(e.getMessage());
        }

        Pdv pdv = modelMapper.map(partnerPdv, Pdv.class);
        pdv = pdvRepository.save(pdv);

        partnerPdv.setId(pdv.getId());
        return partnerPdv;
    }

    public PartnersPdvs getAllPdvs() {
        PartnersPdvs partnersPdvs = new PartnersPdvs();
        List<Pdv> pdvs = pdvRepository.findAll();
        pdvs.forEach(p -> partnersPdvs.addPdv(modelMapper.map(p, PartnerPdv.class)));
        return partnersPdvs;
    }

    public PartnerPdv getPdvById(String id) throws NotFoundException {
        Optional<Pdv> pdv = pdvRepository.findById(id);
        if (pdv.isEmpty())
            throw new NotFoundException(String.format("No partner pdv found with id %s", id));
        return modelMapper.map(pdv.get(), PartnerPdv.class);
    }

    public PartnerPdv searchNearestPartner(double longitude, double latitude) throws NotFoundException {
        Optional<Pdv> pdv = pdvCustomQueriesRepository.queryNearestPartnerWithinCoverageArea(longitude, latitude);
        return modelMapper.map(
                pdv.orElseThrow(() -> new NotFoundException(String.format("There is no partner that has coverage area for the given location: lng=%d lat=%d", longitude, latitude))),
                PartnerPdv.class);
    }

}
