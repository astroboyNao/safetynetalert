package com.safetynet.alerts.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.application.dto.MedicalrecordDTO;
import com.safetynet.alerts.application.service.MedicalRecordService;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordControllerTest {
	@Mock
	private MedicalRecordService medicalRecordService;

	@InjectMocks
	private MedicalRecordController medicalRecordController;

	@Captor
	ArgumentCaptor<MedicalrecordDTO> medicalRecordTOCaptor;

	@Test
	public void shouldReturnList() {
		List<MedicalrecordDTO> medicalRecordDTOs = new ArrayList<MedicalrecordDTO>();
		medicalRecordDTOs.add(mock(MedicalrecordDTO.class));

		when(medicalRecordService.list()).thenReturn(medicalRecordDTOs);

		List<MedicalrecordDTO> response = medicalRecordController.list();
		assertEquals(response, medicalRecordDTOs);
		verify(medicalRecordService, Mockito.times(1)).list();

	}

	@Test
	public void shouldSave() {
		MedicalrecordDTO medicalRecordDTO = (mock(MedicalrecordDTO.class));

		medicalRecordController.save(medicalRecordDTO);

		verify(medicalRecordService, Mockito.times(1)).save(medicalRecordDTO);
	}


	@Test
	public void shouldCreate() {
		MedicalrecordDTO medicalRecordDTO = (mock(MedicalrecordDTO.class));
		when(medicalRecordService.create(medicalRecordDTO)).thenReturn(medicalRecordDTO);
		
		MedicalrecordDTO medicalRecord = medicalRecordController.create(medicalRecordDTO);

		verify(medicalRecordService, Mockito.times(1)).create(medicalRecordDTO);
		assertEquals(medicalRecordDTO, medicalRecord);
	}
	
	@Test
	public void shouldDelete() {
		MedicalrecordDTO medicalRecordDTO = (mock(MedicalrecordDTO.class));

		medicalRecordController.delete(medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());

		verify(medicalRecordService, Mockito.times(1)).delete(medicalRecordTOCaptor.capture());
		MedicalrecordDTO checkMedicalRecordDTO = medicalRecordTOCaptor.getValue();

		assertEquals(checkMedicalRecordDTO.getFirstName(), medicalRecordDTO.getFirstName());
		assertEquals(checkMedicalRecordDTO.getLastName(), medicalRecordDTO.getLastName());
	}
}
