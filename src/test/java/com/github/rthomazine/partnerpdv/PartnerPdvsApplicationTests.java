package com.github.rthomazine.partnerpdv;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rthomazine.partnerpdv.persistence.PartnerPdv;
import com.github.rthomazine.partnerpdv.persistence.PartnerPdvRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartnerPdvsApplicationTests {

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private PartnerPdvRepository partnerRepository;

	private static String partnerPdvJson = "{" +
			"  \"id\": 1, " +
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

	@Test
	public void testPartnerPdvJsonMapper() throws IOException {
		PartnerPdv partnerPdv = mapper.readValue(partnerPdvJson, PartnerPdv.class);
		assertNotNull(partnerPdv);
		assertNotNull(partnerPdv.getAddress());
		assertNotNull(partnerPdv.getCoverageArea());
	}

	@Test
	public void testAddNewPartnerFromJsonToRepository() throws IOException {
		PartnerPdv partnerPdv = mapper.readValue(partnerPdvJson, PartnerPdv.class);
		partnerPdv = partnerRepository.save(partnerPdv);
		assertTrue(partnerPdv.getId() != null);
	}

	@Test(expected = DuplicateKeyException.class)
	public void testAddExistingPartnerToRepository() {
		PartnerPdv partner = partnerRepository.save(PartnerPdv.builder()
				.document("1432132123891/0001")
				.owner("João Manuel")
				.trading("Butequinho da Esquina")
				.build());
	}

	@Test
	public void testQueryExistingPartnerByDocument() {
		Optional<PartnerPdv> partner = partnerRepository.findByDocument("1432132123891/0001");
		assertTrue(partner.isPresent());
		assertTrue(partner.get().getOwner().equals("Zé da Silva"));
		assertTrue(partner.get().getTrading().equals("Adega da Cerveja - Pinheiros"));
	}

	@Test
	public void testQueryNonExistingPartnerByDocument() {
		Optional<PartnerPdv> partner = partnerRepository.findByDocument("1432132123891/1111");
		assertFalse(partner.isPresent());
	}

}
