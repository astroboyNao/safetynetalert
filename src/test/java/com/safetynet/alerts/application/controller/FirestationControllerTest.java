package com.safetynet.alerts.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.safetynet.alerts.application.dto.FirestationAffectationDTO;
import com.safetynet.alerts.application.dto.FirestationDTO;
import com.safetynet.alerts.application.dto.GrptAddresseDTO;
import com.safetynet.alerts.application.dto.GrptStationDTO;
import com.safetynet.alerts.application.service.FireStationService;
import static org.mockito.AdditionalMatchers.*;

@ExtendWith(MockitoExtension.class)
public class FirestationControllerTest {
	@Mock
	private FireStationService firestationService;

	@InjectMocks
	private FirestationController firestationController;

	@Test
	public void shouldReturnList() {
		List<FirestationDTO> firestationDTOs = new ArrayList<FirestationDTO>();
		firestationDTOs.add(mock(FirestationDTO.class));

		when(firestationService.list()).thenReturn(firestationDTOs);

		List<FirestationDTO> response = firestationController.list();
		assertEquals(response, firestationDTOs);
		verify(firestationService, Mockito.times(1)).list();

	}

	@Test
	public void shouldSave() {
		FirestationDTO firestationDTO = (mock(FirestationDTO.class));

		firestationController.save(firestationDTO);

		verify(firestationService, Mockito.times(1)).save(firestationDTO);
	}

	@Test
	public void shouldDelete() {
		String address = "address";

		firestationController.delete(address);

		verify(firestationService, Mockito.times(1)).delete(address);
	}
	
	@Test
	public void getListPersonForStations(){
		long[] stations = new long[] {1L,2L};
		List<GrptAddresseDTO> grpAddressDTOs = new ArrayList();
		grpAddressDTOs.add(mock(GrptAddresseDTO.class));
		
		when(firestationService.getListPersonForStations(aryEq(stations))).thenReturn(grpAddressDTOs);
		
		List<GrptAddresseDTO> grpAddress = firestationController.getListPersonForStations(stations);

		verify(firestationService, Mockito.times(1)).getListPersonForStations(stations);
		assertEquals(grpAddressDTOs, grpAddress);
	}
	

	@Test
	public void getListPersonForAddress() {
		String address = "address";
		List<GrptStationDTO> grpStationDTOs = new ArrayList();
		grpStationDTOs.add(mock(GrptStationDTO.class));
		
		when(firestationService.getListPersonForAdress(address)).thenReturn(grpStationDTOs);
		List<GrptStationDTO> grpStations = firestationController.getListPersonForAddress(address);
		
		verify(firestationService, Mockito.times(1)).getListPersonForAdress(address);
		assertEquals(grpStationDTOs, grpStations);
	}
	

	@Test
	public void getFirestationAffectation() {
		int stationNumber = 1;
		FirestationAffectationDTO firestationAffectationDTO = mock(FirestationAffectationDTO.class);
		when(firestationService.getFirestationAffectation(stationNumber)).thenReturn(firestationAffectationDTO);
		
		FirestationAffectationDTO firestationAffectation = firestationController.getFirestationAffectation(stationNumber);
		verify(firestationService, Mockito.times(1)).getFirestationAffectation(stationNumber);
		assertEquals(firestationAffectationDTO, firestationAffectation);
	}
}
