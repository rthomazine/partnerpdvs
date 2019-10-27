package com.github.rthomazine.partnerpdv;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rthomazine.partnerpdv.persistence.Pdv;
import com.github.rthomazine.partnerpdv.persistence.PdvRepository;
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
	private PdvRepository partnerRepository;

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
		Pdv partnerPdv = mapper.readValue(partnerPdvJson, Pdv.class);
		assertNotNull(partnerPdv);
		assertNotNull(partnerPdv.getAddress());
		assertNotNull(partnerPdv.getCoverageArea());
	}

	@Test
	public void testAddNewPartnerFromJsonToRepository() throws IOException {
		Pdv partnerPdv = mapper.readValue(partnerPdvJson, Pdv.class);
		partnerPdv = partnerRepository.save(partnerPdv);
		assertNotNull(partnerPdv.getId());
	}

	@Test(expected = DuplicateKeyException.class)
	public void testAddExistingPartnerToRepository() {
		partnerRepository.save(new Pdv()
				.setDocument("1432132123891/0001")
				.setOwner("João Manuel")
				.setTrading("Butequinho da Esquina"));
	}

	@Test
	public void testQueryExistingPartnerByDocument() {
		Optional<Pdv> partner = partnerRepository.findByDocument("22512343000178");
		assertTrue(partner.isPresent());
		assertTrue(partner.get().getOwner().equals("Ze da Ambev"));
		assertTrue(partner.get().getTrading().equals("Emporio legal"));
	}

	@Test
	public void testQueryNonExistingPartnerByDocument() {
		Optional<Pdv> partner = partnerRepository.findByDocument("1432132123891/1111");
		assertFalse(partner.isPresent());
	}

}
