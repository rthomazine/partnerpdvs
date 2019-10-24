package com.github.rthomazine.partnerpdv.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnersPdvs {

    private List<PartnerPdv> pdvs;

    public PartnersPdvs addPdv(PartnerPdv pdv) {
        if (pdvs == null)
            pdvs = new ArrayList<>();
        pdvs.add(pdv);

        return this;
    }

}
