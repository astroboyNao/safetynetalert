package com.safetynet.alerts.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.application.dto.MedicalrecordDTO;
import com.safetynet.alerts.application.service.MedicalRecordService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class MedicalRecordController.
 */
@RestController

/**
 * Instantiates a new medical record controller.
 *
 * @param medicalRecordService the medical record service
 */
@AllArgsConstructor

/** The Constant log. */
@Slf4j
public class MedicalRecordController {

	/** The medical record service. */
	private MedicalRecordService medicalRecordService;

	/**
	 * List.
	 *
	 * @return the list
	 */
	@GetMapping("/medicalrecords")
	public List<MedicalrecordDTO> list() {
		log.debug("call medicalrecord controller - list");
		return this.medicalRecordService.list();
	}

	/**
	 * Create.
	 *
	 * @param medicalRecordDTO the medical record DTO
	 * @return the medicalrecord DTO
	 */
	@PostMapping("/medicalrecord")
	public MedicalrecordDTO create(@RequestBody MedicalrecordDTO medicalRecordDTO) {
		log.debug("call medicalrecord controller - create");
		return this.medicalRecordService.create(medicalRecordDTO);
	}


	/**
	 * Save.
	 *
	 * @param medicalRecordDTO the medical record DTO
	 * @return the medicalrecord DTO
	 */
	@PutMapping("/medicalrecord")
	public MedicalrecordDTO save(@RequestBody MedicalrecordDTO medicalRecordDTO) {
		log.debug("call medicalrecord controller - save");
		return this.medicalRecordService.save(medicalRecordDTO);
	}

	/**
	 * Delete.
	 *
	 * @param firstName the first name
	 * @param lastName  the last name
	 */
	@DeleteMapping("/medicalrecord")
	public void delete(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
		log.debug("call medicalrecord controller - delete");
		MedicalrecordDTO medicalrecordDTO = new MedicalrecordDTO();
		medicalrecordDTO.setFirstName(firstName);
		medicalrecordDTO.setLastName(lastName);
		this.medicalRecordService.delete(medicalrecordDTO);
	}
}
