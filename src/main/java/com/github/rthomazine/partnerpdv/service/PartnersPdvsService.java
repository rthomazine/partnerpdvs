package com.github.rthomazine.partnerpdv.service;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.github.rthomazine.partnerpdv.api.exception.DocumentException;
import com.github.rthomazine.partnerpdv.api.exception.NotFoundException;
import com.github.rthomazine.partnerpdv.api.model.PartnerPdv;
import com.github.rthomazine.partnerpdv.api.model.PartnersPdvs;
import com.github.rthomazine.partnerpdv.persistence.Pdv;
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

    private static CNPJValidator cnpjValidator = new CNPJValidator();

    public PartnerPdv createPartnerPdv(PartnerPdv partnerPdv) throws DocumentException, DuplicateKeyException {

        try {
            cnpjValidator.assertValid(partnerPdv.getDocument());
        } catch (InvalidStateException e) {
            throw new DocumentException(e.getMessage());
        }
        Pdv pdv = pdvRepository.save(modelMapper.map(partnerPdv, Pdv.class));
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

}
