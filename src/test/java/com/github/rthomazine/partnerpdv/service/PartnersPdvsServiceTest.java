package com.github.rthomazine.partnerpdv.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rthomazine.partnerpdv.api.exception.DocumentException;
import com.github.rthomazine.partnerpdv.api.exception.NotFoundException;
import com.github.rthomazine.partnerpdv.api.model.PartnerPdv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartnersPdvsServiceTest {

    private static String invalidPartnerPdvJson = "{" +
            "  \"tradingName\": \"Adega da Cerveja - Pinheiros\"," +
            "  \"ownerName\": \"Zé da Silva\"," +
            "  \"document\": \"1432132123891/0001\"," +
            "  \"coverageArea\": { " +
            "    \"type\": \"MultiPolygon\", " +
            "    \"coordinates\": [" +
            "      [[[30, 20], [45, 40], [10, 40], [30, 20]]], " +
            "      [[[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]]" +
            "    ]" +
            "  }," +
            "  \"address\": { " +
            "    \"type\": \"Point\"," +
            "    \"coordinates\": [-46.57421, -21.785741]" +
            "  }" +
            "}";

    private static String validPartnerPdvJson = "{" +
            "      \"tradingName\": \"Bar Legal\"," +
            "      \"ownerName\": \"Fernando Silva\"," +
            "      \"document\": \"05202839000126\"," +
            "      \"coverageArea\": {" +
            "        \"type\": \"MultiPolygon\"," +
            "        \"coordinates\": [" +
            "          [" +
            "            [" +
            "              [" +
            "                -43.50404," +
            "                -22.768366" +
            "              ]," +
            "              [" +
            "                -43.45254," +
            "                -22.775646" +
            "              ]," +
            "              [" +
            "                -43.429195," +
            "                -22.804451" +
            "              ]," +
            "              [" +
            "                -43.38422," +
            "                -22.788942" +
            "              ]," +
            "              [" +
            "                -43.390743," +
            "                -22.764568" +
            "              ]," +
            "              [" +
            "                -43.355724," +
            "                -22.739239" +
            "              ]," +
            "              [" +
            "                -43.403446," +
            "                -22.705671" +
            "              ]," +
            "              [" +
            "                -43.440525," +
            "                -22.707571" +
            "              ]," +
            "              [" +
            "                -43.4752," +
            "                -22.698704" +
            "              ]," +
            "              [" +
            "                -43.514683," +
            "                -22.742722" +
            "              ]," +
            "              [" +
            "                -43.50404," +
            "                -22.768366" +
            "              ]" +
            "            ]" +
            "          ]" +
            "        ]" +
            "      }," +
            "      \"address\": {" +
            "        \"type\": \"Point\"," +
            "        \"coordinates\": [" +
            "          -43.432034," +
            "          -22.747707" +
            "        ]" +
            "      }" +
            "    }";

    @Autowired
    private PartnersPdvsService partnersPdvsService;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testCreateNewPartnerWithValidDocument() throws JsonProcessingException, DocumentException {
        PartnerPdv partnerPdv = mapper.readValue(validPartnerPdvJson, PartnerPdv.class);
        partnersPdvsService.createPartnerPdv(partnerPdv);
    }

    @Test(expected = DocumentException.class)
    public void testCreateNewPartnerWithInvalidDocument() throws JsonProcessingException, DocumentException {
        PartnerPdv partnerPdv = mapper.readValue(invalidPartnerPdvJson, PartnerPdv.class);
        partnersPdvsService.createPartnerPdv(partnerPdv);
    }

    @Test
    public void testExistingNearestPartner() throws NotFoundException {
        PartnerPdv pdv = partnersPdvsService.searchNearestPartner(-46.57421, -21.785741);
        assertEquals(pdv.getDocument(), "06004905000116");
    }

    @Test(expected = NotFoundException.class)
    public void testNonExistingNearestPartner() throws NotFoundException {
        partnersPdvsService.searchNearestPartner(46.57421, 21.785741);
    }

}