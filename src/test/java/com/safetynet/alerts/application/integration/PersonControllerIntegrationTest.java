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
import com.safetynet.alerts.application.dto.PersonDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql({ "/sql/import-person.sql" })
public class PersonControllerIntegrationTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testSavePersonController() {
		PersonDTO personDTO = PersonDTO.builder().firstName("firstName").lastName("lastName").address("address")
				.city("city").email("email").zip("zip").build();

		ResponseEntity<PersonDTO> response = restTemplate.postForEntity("http://localhost:" + port + "/person",
				personDTO, PersonDTO.class);

		PersonDTO personResponse = response.getBody();

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertNotNull(personResponse);
		assertTrue(personDTO.equals(personResponse));

	}

	@Test
	public void testDeletePersonController() {
		PersonDTO personDTO = PersonDTO.builder().firstName("firstNameToDelete").lastName("lastNameToDelete").build();

		ResponseEntity<PersonDTO> response = restTemplate.postForEntity("http://localhost:" + port + "/person",
				personDTO, PersonDTO.class);

		PersonDTO personResponse = response.getBody();

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertEquals(personResponse.getFirstName(), "firstNameToDelete");
		assertEquals(personResponse.getLastName(), "lastNameToDelete");

		restTemplate.delete("http://localhost:" + port + "/person?" + "firstName=" + personDTO.getFirstName()
				+ "&lastName=" + personDTO.getLastName());

		PersonDTO[] personDTOs = restTemplate.getForEntity("http://localhost:" + port + "/persons", PersonDTO[].class)
				.getBody();

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertTrue(Stream.of(personDTOs).filter(person -> person.getFirstName().equals(personDTO.getFirstName())
				&& person.getLastName().equals(personDTO.getLastName())).findFirst().isEmpty());

	}

	@Test
	public void testAllPersonController() {
		ResponseEntity<PersonDTO[]> response = restTemplate.getForEntity("http://localhost:" + port + "/persons",
				PersonDTO[].class);
		PersonDTO[] persons = response.getBody();

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertNotNull(persons);
		assertEquals(5, persons.length);

	}
}
