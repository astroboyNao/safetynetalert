package com.safetynet.alerts.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.safetynet.alerts.application.exception.ExistException;
import com.safetynet.alerts.application.repository.MedicalRepository;
import com.safetynet.alerts.application.repository.entity.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.application.dto.MedicalrecordDTO;
import com.safetynet.alerts.application.mapper.MedicalrecordMapper;
import com.safetynet.alerts.application.repository.PersonRepository;
import com.safetynet.alerts.application.repository.entity.Person;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {
	private MedicalRecordService medicalRecordService;
	@Mock
	private PersonRepository personRepository;
	@Mock
	private MedicalRepository medicalRepository;
	@Mock
	private MedicalrecordMapper medicalrecordMapper;

	@BeforeEach
	public void setup() {
		this.medicalRecordService = new MedicalRecordService(personRepository, medicalRepository, medicalrecordMapper);
	}

	@Test
	public void shouldGetList() {

		when(medicalRepository.findAll()).thenReturn(List.of(mock(MedicalRecord.class)));
		when(medicalrecordMapper.medicalRecordsToMedicalrecordDTOs(anyList())).thenReturn(List.of(mock(MedicalrecordDTO.class)));

		this.medicalRecordService.list();

		verify(medicalRepository, Mockito.times(1)).findAll();
		verify(medicalrecordMapper, Mockito.times(1)).medicalRecordsToMedicalrecordDTOs(anyList());
	}

	@Test
	public void shouldCreate() {
		MedicalrecordDTO medicalrecordDTO = new MedicalrecordDTO();
		medicalrecordDTO.setFirstName("FIRSTNAME");
		medicalrecordDTO.setLastName("LASTNAME");

		MedicalRecord medicalRecord = MedicalRecord.builder()
				.id(1L)
				.person(Person.builder().firstName("FIRSTNAME").lastName("LASTNAME").build())
				.build();
		when(personRepository.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(mock(Person.class));
		when(medicalrecordMapper.medicalrecordDTOToMedicalRecord(any(MedicalrecordDTO.class))).thenReturn(mock(MedicalRecord.class));
		when(medicalRepository.save(any(MedicalRecord.class))).thenReturn(medicalRecord);

		MedicalrecordDTO medicalrecordDTOCreated = this.medicalRecordService.create(medicalrecordDTO);

		verify(medicalRepository,  Mockito.times(1)).save(any(MedicalRecord.class));
		verify(personRepository,  Mockito.times(1)).save(any(Person.class));
	}


	@Test
	public void shouldSave() {
		MedicalrecordDTO medicalrecordDTO = new MedicalrecordDTO();
		medicalrecordDTO.setFirstName("FIRSTNAME");
		medicalrecordDTO.setLastName("LASTNAME");

		MedicalRecord medicalRecord = MedicalRecord.builder()
				.id(1L)
				.person(Person.builder().firstName("FIRSTNAME").lastName("LASTNAME").build())
				.build();
		when(personRepository.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(mock(Person.class));
		when(medicalRepository.findByPerson(any(Person.class))).thenReturn(mock(MedicalRecord.class));
		when(medicalRepository.save(any(MedicalRecord.class))).thenReturn(medicalRecord);

		MedicalrecordDTO medicalrecordDTOCreated = this.medicalRecordService.save(medicalrecordDTO);

		verify(medicalRepository,  Mockito.times(1)).save(any(MedicalRecord.class));
		verify(personRepository,  Mockito.times(1)).save(any(Person.class));

	}

/*
	  @Test
	  public void shouldDelete() {
		MedicalrecordDTO medicalrecordDTO = new MedicalrecordDTO();
		medicalrecordDTO.setFirstName("FIRSTNAME");
		medicalrecordDTO.setLastName("LASTNAME");

		when(medicalRepository.findByPerson(any(Person.class))).thenReturn(mock(MedicalRecord.class));
		when(personRepository.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(mock(Person.class));

		this.medicalRecordService.delete(medicalrecordDTO);

	    verify(medicalRepository, Mockito.times(1)).deleteById(anyLong());

	  }
*/
}
