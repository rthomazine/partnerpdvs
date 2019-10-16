package com.github.rthomazine.partnerpdv.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.rthomazine.partnerpdv.persistence.PartnerPdv;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PdvsResponse {

    private List<PartnerPdv> pdvs;

    public PdvsResponse addPdv(PartnerPdv pdv) {
        if (pdvs == null)
            pdvs = new ArrayList<>();
        pdvs.add(pdv);

        return this;
    }

}
