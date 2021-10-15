package com.safetynet.alerts.application.controller;

import java.util.List;
import java.util.Map;

import com.safetynet.alerts.application.dto.ChildDTO;
import com.safetynet.alerts.application.repository.entity.Person;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.application.dto.MailDTO;
import com.safetynet.alerts.application.dto.PersonDTO;
import com.safetynet.alerts.application.dto.PersonInfoDTO;
import com.safetynet.alerts.application.service.PersonService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO: Auto-generated Javadoc
/**
 * The Class PersonnController.
 */
@RestController

/**
 * Instantiates a new person controller.
 *
 * @param personService the person service
 */

/**
 * Instantiates a new person controller.
 *
 * @param personService the person service
 */
@AllArgsConstructor

/** The Constant log. */

/** The Constant log. */
@Slf4j
public class PersonController {

	/** The person service. */
	private PersonService personService;

	/**
	 * List.
	 *
	 * @return the list
	 */
	@GetMapping("/persons")
	public List<PersonDTO> list() {
		log.debug("call person controller - list");
		return this.personService.list();
	}

	/**
	 * create.
	 *
	 * @param personDTO the person DTO
	 * @return the person DTO
	 */
	@PostMapping("/person")
	public PersonDTO create(@RequestBody PersonDTO personDTO) {
		log.debug("call person controller - create");
		return personService.create(personDTO);
	}

	/**
	 * Save.
	 *
	 * @param personDTO the person DTO
	 * @return the person DTO
	 */
	@PutMapping("/person")
	public PersonDTO save(@RequestBody PersonDTO personDTO) {
		log.debug("call person controller - save");
		return personService.save(personDTO);
	}
	
	/**
	 * Delete.
	 *
	 * @param firstName the first name
	 * @param lastName  the last name
	 */
	@DeleteMapping("/person")
	public void delete(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
		log.debug("call person controller - delete");
		PersonDTO personDTO = new PersonDTO();
		personDTO.setFirstName(firstName);
		personDTO.setLastName(lastName);
		this.personService.delete(personDTO);
	}

	@GetMapping("/childAlert")
	public List<ChildDTO> listChild(@RequestParam(value="address") String address) {
		log.debug("call safetyNet controller - listChild");
		return this.personService.listChild(address);
	}

	@GetMapping("/personInfo")
	public List<PersonInfoDTO> listPersonInfo(@RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName) {
		log.debug("call safetyNet controller - personInfo");
		return this.personService.listPersonInfo(firstName, lastName);
	}
	
	@GetMapping("/communityEmail")
	public List<MailDTO> listEmail(@RequestParam(value="city") String city) {
		log.debug("call safetyNet controller - communityEmail");
		return this.personService.listEmail(city);
	}
}
