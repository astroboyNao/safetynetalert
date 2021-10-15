package com.safetynet.alerts.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.safetynet.alerts.application.dto.FirestationAffectationDTO;
import com.safetynet.alerts.application.dto.PersonInfoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.application.dto.FirestationDTO;
import com.safetynet.alerts.application.dto.GrptStationDTO;
import com.safetynet.alerts.application.mapper.FirestationMapper;
import com.safetynet.alerts.application.repository.FireStationRepository;
import com.safetynet.alerts.application.repository.entity.Firestation;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {
	private FireStationService firestationService;
	@Mock
	private FireStationRepository fireStationRepository;
	@Mock
	private FirestationMapper firestationMapper;
	@Mock
	private PersonService personService;

	@BeforeEach
	public void setup() {
		this.firestationService = new FireStationService(fireStationRepository, personService, firestationMapper);
	}

	@Test
	public void shouldGetList() {
		List<Firestation> firestations = new ArrayList<Firestation>();
		firestations.add(mock(Firestation.class));

		List<FirestationDTO> firestationDTOs = new ArrayList<FirestationDTO>();
		firestationDTOs.add(mock(FirestationDTO.class));

		when(fireStationRepository.findAll()).thenReturn(firestations);
		when(firestationMapper.firestationsToFirestationDTOs(anyList())).thenReturn(firestationDTOs);

		List<FirestationDTO> checkFirestationDTOs = this.firestationService.list();

		verify(fireStationRepository, Mockito.times(1)).findAll();
		verify(firestationMapper, Mockito.times(1)).firestationsToFirestationDTOs(firestations);
		assertEquals(checkFirestationDTOs, firestationDTOs);
	}

	@Test
	public void shouldSave() {

		FirestationDTO firestationDTO = FirestationDTO.builder().address("address").station(1).build();
		Firestation firestation = mock(Firestation.class);

		when(fireStationRepository.findByAddress(any(String.class))).thenReturn(firestation);

		this.firestationService.save(firestationDTO);

		verify(fireStationRepository, Mockito.times(1)).save(firestation);

	}

	@Test
	public void shouldDelete() {

		String address = "address";
		Firestation firestation = mock(Firestation.class);
		when(fireStationRepository.findByAddress(address)).thenReturn(firestation);

		this.firestationService.delete(address);

		verify(fireStationRepository, Mockito.times(1)).findByAddress(address);
		verify(fireStationRepository, Mockito.times(1)).delete(firestation);

	}

	@Test
	public void getListPersonForStations() {
		long[] stations = new long[] {1,2};
		Firestation firestation = mock(Firestation.class);

		when(fireStationRepository.findByStationIn(stations)).thenReturn(List.of(firestation));
		this.firestationService.getListPersonForStations(stations);

		verify(personService, Mockito.times(1)).getByAddressIn(any());

	}
	@Test
	public void getListPersonForAdress() {
		String address = "address";
		Firestation firestation = mock(Firestation.class);
		PersonInfoDTO personInfoDTO = mock(PersonInfoDTO.class);

		when(fireStationRepository.findByAddress(address)).thenReturn(firestation);
		when(personService.getPersonInfoByAddress(address)).thenReturn(List.of(personInfoDTO));

		 List<GrptStationDTO> listPersonForAdress = this.firestationService.getListPersonForAdress(address);


	}

	/*
	public FirestationAffectationDTO getFirestationAffectation(long stationNumber) {
		Firestation firestation = this.fireStationRepository.findByStation(stationNumber);

		return personService.searchFirestationAffectationFromAddress(firestation.getAddress());
	}

	public List<String> getListPhoneForAStation(int stationNumber) {*/

}
