package com.safetynet.alerts.application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.safetynet.alerts.application.dto.FirestationDTO;
import com.safetynet.alerts.application.dto.MedicalrecordDTO;
import com.safetynet.alerts.application.dto.PersonDTO;
import com.safetynet.alerts.application.service.FireStationService;
import com.safetynet.alerts.application.service.MedicalRecordService;
import com.safetynet.alerts.application.service.PersonService;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class Application.
 */
@SpringBootApplication
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
@Slf4j
public class Application {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Initialize data only on production mode
	 *
	 * @param objectMapper         the object mapper
	 * @param resourceLoader       the resource loader
	 * @param fireStationService   the fire station service
	 * @param medicalRecordService the medical record service
	 * @param personService        the person service
	 * @return the command line runner
	 */
	@Bean
	@Profile({ "!test" })
	public CommandLineRunner initializeData(ObjectMapper objectMapper, ResourceLoader resourceLoader,
			FireStationService fireStationService, MedicalRecordService medicalRecordService,
			PersonService personService) {
		return (args) -> {
			log.debug("INITIALIZE DATA");
			JsonNode treeFile = objectMapper.readTree(new ClassPathResource("data.json").getFile());
			JsonNode firestationNodes = treeFile.get("firestations");
			JsonNode personNodes = treeFile.get("persons");
			JsonNode medicalRecordNodes = treeFile.get("medicalrecords");

			List<FirestationDTO> firestationDTOs = objectMapper.readerFor(new TypeReference<List<FirestationDTO>>() {
			}).readValue(firestationNodes);
			List<PersonDTO> personDTOs = objectMapper.readerFor(new TypeReference<List<PersonDTO>>() {
			}).readValue(personNodes);
			List<MedicalrecordDTO> medicalRecordDTOs = objectMapper
					.readerFor(new TypeReference<List<MedicalrecordDTO>>() {
					}).readValue(medicalRecordNodes);

			firestationDTOs.stream().forEach(fireStationService::create);
			personDTOs.stream().forEach(personService::create);
			medicalRecordDTOs.stream().forEach(medicalRecordService::create);
			log.info("DATA INITIALIZED");

		};

	}

	/**
	 * Gets the object mapper.
	 *
	 * @return the object mapper
	 */
	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(LocalDate.class,
				new LocalDateSerializer(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		javaTimeModule.addDeserializer(LocalDate.class,
				new LocalDateDeserializer(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		objectMapper.registerModule(javaTimeModule);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return objectMapper;
	}

	/**
	 * Htttp trace repository for spring actuator
	 *
	 * @return the http trace repository
	 */
	@Bean
	public HttpTraceRepository htttpTraceRepository() {
		return new InMemoryHttpTraceRepository();
	}
}
