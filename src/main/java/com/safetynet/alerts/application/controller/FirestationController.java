package com.safetynet.alerts.application.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.safetynet.alerts.application.dto.FirestationAffectationDTO;
import com.safetynet.alerts.application.dto.FirestationDTO;
import com.safetynet.alerts.application.dto.GrptAddresseDTO;
import com.safetynet.alerts.application.dto.GrptStationDTO;
import com.safetynet.alerts.application.service.FireStationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class FirestationController.
 */
@RestController

/**
 * Instantiates a new firestation controller.
 *
 * @param fireStationService the fire station service
 */

/**
 * Instantiates a new firestation controller.
 *
 * @param fireStationService the fire station service
 */
@AllArgsConstructor

/** The Constant log. */
@Slf4j
public class FirestationController {

	/** The fire station service. */
	private FireStationService fireStationService;

	/**
	 * List.
	 *
	 * @return the list
	 */
	@GetMapping("/firestations")
	public List<FirestationDTO> list() {
		log.debug("call firestation controller - list");
		return this.fireStationService.list();
	}

	/**
	 * Create.
	 *
	 * @param firestationDTO the firestation DTO
	 * @return the firestation DTO
	 */
	@PostMapping("/firestation")
	public ResponseEntity<Object> create(@RequestBody FirestationDTO firestationDTO) {
		log.debug("call firestation controller - create");
		FirestationDTO firestationDTOCreated = this.fireStationService.create(firestationDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{address}")
				.buildAndExpand(firestationDTOCreated.getAddress()).toUri();

		return ResponseEntity.created(location).build();
	}

	/**
	 * Save.
	 *
	 * @param firestationDTO the firestation DTO
	 * @return the firestation DTO
	 */
	@PutMapping("/firestation")
	public FirestationDTO save(@RequestBody FirestationDTO firestationDTO) {
		log.debug("call firestation controller - save");
		return this.fireStationService.save(firestationDTO);
	}

	/**
	 * Delete.
	 *
	 * @param address the address
	 */
	@DeleteMapping("/firestation/{address}")
	public void delete(@PathVariable String address) {
		log.debug("call firestation controller - delete");
		this.fireStationService.delete(address);
	}

	/**
	 * Gets the phone alert.
	 *
	 * @param stationNumber the station number
	 * @return the phone alert
	 */
	@GetMapping("/phoneAlert")
	public List<String> getPhoneAlert(@RequestParam(value="stationNumber") int stationNumber) {
		log.debug("call firestation controller - getFirestationAffectation");
		return this.fireStationService.getListPhoneForAStation(stationNumber);
	}


	/**
	 * Gets the firestation affectation.
	 *
	 * @param stationNumber the station number
	 * @return the firestation affectation
	 */
	@GetMapping("/firestation")
	public FirestationAffectationDTO getFirestationAffectation(@RequestParam(value="stationNumber") int stationNumber) {
		log.debug("call firestation controller - getFirestationAffectation");
		return this.fireStationService.getFirestationAffectation(stationNumber);
	}

	/**
	 * Gets the list person for address.
	 *
	 * @param address the address
	 * @return the list person for address
	 */
	@GetMapping("/fire")
	public List<GrptStationDTO> getListPersonForAddress(@RequestParam(value="address") String address) {
		log.debug("call firestation controller - getFirestationAffectation");
		return this.fireStationService.getListPersonForAdress(address);
	}

	/**
	 * Gets the list person for stations.
	 *
	 * @param stations the stations
	 * @return the list person for stations
	 */
	@GetMapping("/flood")
	public List<GrptAddresseDTO> getListPersonForStations(@RequestParam(value="stations[]") long[] stations ){
		log.debug("call firestation controller - getFirestationAffectation");
		return this.fireStationService.getListPersonForStations(stations);
	}

}
