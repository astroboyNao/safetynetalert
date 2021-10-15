package com.safetynet.alerts.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.safetynet.alerts.application.dto.FirestationDTO;
import com.safetynet.alerts.application.repository.entity.Firestation;

/**
 * The Interface FirestationMapper.
 */
@Mapper
public interface FirestationMapper {
	
	/**
	 * Firestation to firestation DTO.
	 *
	 * @param firestation the firestation
	 * @return the firestation DTO
	 */
	FirestationDTO firestationToFirestationDTO(Firestation firestation);

	/**
	 * Firestation DTO to firestation.
	 *
	 * @param firestationDTO the firestation DTO
	 * @return the firestation
	 */
	Firestation firestationDTOToFirestation(FirestationDTO firestationDTO);

	/**
	 * Firestations to firestation DT os.
	 *
	 * @param firestations the firestations
	 * @return the list
	 */
	List<FirestationDTO> firestationsToFirestationDTOs(List<Firestation> firestations);

	/**
	 * Firestation DT os to firestations.
	 *
	 * @param firestationDTOs the firestation DT os
	 * @return the list
	 */
	List<Firestation> firestationDTOsToFirestations(List<FirestationDTO> firestationDTOs);
	
	/**
	 * Update.
	 *
	 * @param firestatation the firestatation
	 * @param firestationDTO the firestation DTO
	 */
	void update(@MappingTarget Firestation firestatation, FirestationDTO firestationDTO);
}
