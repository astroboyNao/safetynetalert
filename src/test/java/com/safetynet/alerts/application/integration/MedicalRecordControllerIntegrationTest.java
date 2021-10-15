package com.safetynet.alerts.application.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.safetynet.alerts.application.dto.MedicalrecordDTO;
import com.safetynet.alerts.application.dto.PersonDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql({ "/sql/import-person.sql", "/sql/import-medicalrecord.sql" })
public class MedicalRecordControllerIntegrationTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;


	public void testSaveMedicalRecordController() {
		MedicalrecordDTO medicalRecordDTO = MedicalrecordDTO.builder().firstName("FIRSTNAME_6").lastName("LASTNAME_6")
				.birthdate(LocalDate.now()).allergies(List.of("allergie")).medications(List.of("medication")).build();

		ResponseEntity<MedicalrecordDTO> response = restTemplate
				.postForEntity("http://localhost:" + port + "/medicalrecord", medicalRecordDTO, MedicalrecordDTO.class);


		assertTrue(response.getStatusCode().is2xxSuccessful());

	}


	public void testDeleteMedicalRecordController() {
		MedicalrecordDTO medicalRecordDTO = MedicalrecordDTO.builder().firstName("firstNameToDelete")
				.lastName("lastNameToDelete").build();

		ResponseEntity<MedicalrecordDTO> response = restTemplate.postForEntity("http://localhost:" + port + "/person",
				medicalRecordDTO, MedicalrecordDTO.class);

		MedicalrecordDTO medicalRecordResponse = response.getBody();

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertEquals(medicalRecordResponse.getFirstName(), "firstNameToDelete");
		assertEquals(medicalRecordResponse.getLastName(), "lastNameToDelete");

		restTemplate.delete("http://localhost:" + port + "/medicalrecord?" + "firstName="
				+ medicalRecordDTO.getFirstName() + "&lastName=" + medicalRecordDTO.getLastName());

		MedicalrecordDTO[] medicalRecordDTOs = restTemplate
				.getForEntity("http://localhost:" + port + "/medicalrecords", MedicalrecordDTO[].class).getBody();

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertTrue(
				Stream.of(medicalRecordDTOs)
						.filter(medicalRecord -> medicalRecord.getFirstName().equals(medicalRecordDTO.getFirstName())
								&& medicalRecord.getLastName().equals(medicalRecordDTO.getLastName()))
						.findFirst().isEmpty());

	}

	@Test
	public void testAllMedicalRecordController() {
		ResponseEntity<MedicalrecordDTO[]> response = restTemplate
				.getForEntity("http://localhost:" + port + "/medicalrecords", MedicalrecordDTO[].class);
		MedicalrecordDTO[] medicalrecords = response.getBody();

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertNotNull(medicalrecords);
		assertEquals(1, medicalrecords.length);

	}
}
