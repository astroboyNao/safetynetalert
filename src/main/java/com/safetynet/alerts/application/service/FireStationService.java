package com.safetynet.alerts.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.application.dto.FirestationAffectationDTO;
import com.safetynet.alerts.application.dto.FirestationDTO;
import com.safetynet.alerts.application.dto.GrptAddresseDTO;
import com.safetynet.alerts.application.dto.GrptStationDTO;
import com.safetynet.alerts.application.dto.PersonDTO;
import com.safetynet.alerts.application.dto.PersonInfoDTO;
import com.safetynet.alerts.application.exception.ExistException;
import com.safetynet.alerts.application.mapper.FirestationMapper;
import com.safetynet.alerts.application.repository.FireStationRepository;
import com.safetynet.alerts.application.repository.entity.Firestation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class FireStationService.
 */
@Service

/**
 * Instantiates a new fire station service.
 *
 * @param fireStationRepository the fire station repository
 * @param firestationMapper the firestation mapper
 */
@AllArgsConstructor

/** The Constant log. */
@Slf4j
public class FireStationService {
	
	/** The fire station repository. */
	private FireStationRepository fireStationRepository;
	
	private PersonService personService;
	
	/** The firestation mapper. */
	private FirestationMapper firestationMapper;

	/**
	 * List.
	 *
	 * @return the list
	 */
	public List<FirestationDTO> list() {
		log.debug("call firestation service - list");
		List<Firestation> firestations = StreamSupport.stream(this.fireStationRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());

		return firestationMapper.firestationsToFirestationDTOs(firestations);
	}


	/**
	 * Create.
	 *
	 * @param firestationDTO the firestation DTO
	 * @return the firestation DTO
	 */
	public FirestationDTO create(FirestationDTO firestationDTO) {
		log.debug("call firestation service - save");
		
		Firestation exist = fireStationRepository.findByAddress(firestationDTO.getAddress());
		
		if(exist != null) {
			log.error("firestation exist !!!", firestationDTO);
			System.out.println(firestationDTO);
			throw new ExistException("la station de pompier existe déjà !");
		}
		
		Firestation firestation = firestationMapper.firestationDTOToFirestation(firestationDTO);
		
		return firestationMapper.firestationToFirestationDTO(fireStationRepository.save(firestation));
	}

	/**
	 * Save.
	 *
	 * @param firestationDTO the firestation DTO
	 * @return the firestation DTO
	 */
	public FirestationDTO save(FirestationDTO firestationDTO) {
		log.debug("call firestation service - save");

		Firestation firestation = findFirestation(firestationDTO);
		
		firestationMapper.update(firestation, firestationDTO);
		
		return firestationMapper.firestationToFirestationDTO(fireStationRepository.save(firestation));
	}


	private Firestation findFirestation(FirestationDTO firestationDTO) {
		Firestation firestation = fireStationRepository.findByAddress(firestationDTO.getAddress());
		
		if(firestation == null) {
			log.error("firestation not found !!!");
			throw new ExistException("la station de pompier n'existe pas !");
		}
		return firestation;
	}

	/**
	 * Delete.
	 *
	 * @param address the address
	 */
	public void delete(String address) {
		log.debug("call firestation service - delete");
		
		FirestationDTO firestationDTO = new FirestationDTO();
		firestationDTO.setAddress(address);
		Firestation firestation = findFirestation(firestationDTO);
		
		this.fireStationRepository.delete(firestation);
	}


	public FirestationAffectationDTO getFirestationAffectation(long stationNumber) {
		Firestation firestation = this.fireStationRepository.findByStation(stationNumber);
		
		return personService.searchFirestationAffectationFromAddress(firestation.getAddress());
	}

    public List<String> getListPhoneForAStation(int stationNumber) {
		Firestation firestation = this.fireStationRepository.findByStation(stationNumber);
		return personService.getByAddress(firestation.getAddress()).stream().map(
				PersonDTO::getPhone).distinct().collect(Collectors.toList());
    }

	public List<GrptStationDTO> getListPersonForAdress(String address) {
		List<GrptStationDTO> grptStationDTOs = new ArrayList<GrptStationDTO>();
		Firestation firestation = this.fireStationRepository.findByAddress(address);
		Map<Long, List<PersonInfoDTO>> map = new HashMap<>();
		map.put(firestation.getStation(), personService.getPersonInfoByAddress(address));
		map.entrySet().forEach(elt-> {
			grptStationDTOs.add(GrptStationDTO.builder().station(elt.getKey()).persons(elt.getValue()).build());
		 });
		return grptStationDTOs;
	}

	public List<GrptAddresseDTO> getListPersonForStations(long[] stations) {
		List<GrptAddresseDTO> grptAddressDTOs = new ArrayList<GrptAddresseDTO>();
		List<String> addresses = this.fireStationRepository.findByStationIn(stations).stream().map(Firestation::getAddress).collect(Collectors.toList());

		 Map<String, List<PersonInfoDTO>> map = this.personService.getByAddressIn(addresses).stream().collect(
				Collectors.groupingBy(PersonInfoDTO::getAddress));
		 map.entrySet().forEach(elt-> {
			 grptAddressDTOs.add(GrptAddresseDTO.builder().address(elt.getKey()).persons(elt.getValue()).build());
		 });
		 return grptAddressDTOs;
	}
}
