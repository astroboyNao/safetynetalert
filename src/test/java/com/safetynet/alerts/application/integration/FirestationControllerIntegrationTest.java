package com.safetynet.alerts.application.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.safetynet.alerts.application.dto.FirestationDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql({ "/sql/import-firestation.sql" })
public class FirestationControllerIntegrationTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testSaveFirestationController() {
		FirestationDTO firestationDTO = FirestationDTO.builder().address("ADRESSE_10").station(1).build();

		ResponseEntity<FirestationDTO> response = restTemplate
				.postForEntity("http://localhost:" + port + "/firestation", firestationDTO, FirestationDTO.class);

		FirestationDTO firestationResponse = response.getBody();

		assertTrue(response.getStatusCode().is2xxSuccessful());

	}

	@Test
	public void testDeleteFirestationController() {

		restTemplate.delete("http://localhost:" + port + "/firestation/" + "ADRESSE_10");

		FirestationDTO[] firestationDTOs = restTemplate
				.getForEntity("http://localhost:" + port + "/firestations", FirestationDTO[].class).getBody();

		assertTrue(Stream.of(firestationDTOs).filter(firestation -> firestation.getAddress().equals("ADRESSE_10"))
				.findFirst().isEmpty());

	}

	@Test
	public void testAllFirestationController() {
		ResponseEntity<FirestationDTO[]> response = restTemplate
				.getForEntity("http://localhost:" + port + "/firestations", FirestationDTO[].class);
		FirestationDTO[] firestations = response.getBody();

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertNotNull(firestations);
		assertEquals(5, firestations.length);

	}
}
