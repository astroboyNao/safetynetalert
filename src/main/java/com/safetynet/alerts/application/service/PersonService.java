package com.safetynet.alerts.application.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.application.dto.ChildDTO;
import com.safetynet.alerts.application.dto.FirestationAffectationDTO;
import com.safetynet.alerts.application.dto.MailDTO;
import com.safetynet.alerts.application.dto.PersonDTO;
import com.safetynet.alerts.application.dto.PersonInfoDTO;
import com.safetynet.alerts.application.exception.ExistException;
import com.safetynet.alerts.application.exception.NotFoundException;
import com.safetynet.alerts.application.mapper.PersonMapper;
import com.safetynet.alerts.application.repository.PersonRepository;
import com.safetynet.alerts.application.repository.entity.Person;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class PersonService.
 */
@Service

/**
 * Instantiates a new person service.
 *
 * @param personRepository the person repository
 * @param personMapper the person mapper
 */
@AllArgsConstructor

/** The Constant log. */
@Slf4j
public class PersonService {

	/** The person repository. */
	private PersonRepository personRepository;

	/** The person mapper. */
	private PersonMapper personMapper;

	/**
	 * List.
	 *
	 * @return the list
	 */
	public List<PersonDTO> list() {
		log.debug("call person service - list");
		List<Person> persons = StreamSupport.stream(this.personRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());

		return personMapper.personsToPersonDTOs(persons);
	}

	/**
	 * Create.
	 *
	 * @param personDTO the person DTO
	 * @return the person DTO
	 * @throws ExistException
	 */
	public PersonDTO create(PersonDTO personDTO) {
		log.debug("call person service - create");

		Person exist = personRepository.findByFirstNameAndLastName(personDTO.getFirstName(), personDTO.getLastName());

		if(exist != null) {
			log.error("person exist !!!");
			throw new ExistException("la personne existe déjà dans notre référentiel");
		}

		Person person = personMapper.personDTOToPerson(personDTO);
		return personMapper.personToPersonDTO(this.personRepository.save(person));
	}

	/**
	 * Save.
	 *
	 * @param personDTO the person DTO
	 * @return the person DTO
	 */
	public PersonDTO save(PersonDTO personDTO) {
		log.debug("call person service - save");

		Person exist = personRepository.findByFirstNameAndLastName(personDTO.getFirstName(), personDTO.getLastName());

		if(exist == null) {
			log.error("person not found !!!");
			throw new NotFoundException("la personne n'existe pas dans notre référentiel");
		}

		Person person = personMapper.personDTOToPerson(personDTO);
		return personMapper.personToPersonDTO(this.personRepository.save(person));
	}

	/**
	 * Delete.
	 *
	 * @param personDTO the person DTO
	 */
	public void delete(PersonDTO personDTO) {
		log.debug("call person service - delete");
		Person person = this.personRepository.findByFirstNameAndLastName(personDTO.getFirstName(),
				personDTO.getLastName());
		this.personRepository.delete(person);
	}

	public List<MailDTO> listEmail(String city) {
		return this.personRepository.findByCity(city).stream().map((Person person) -> MailDTO.builder().email(person.getEmail()).build()).distinct().collect(Collectors.toList());
	}



	public List<PersonInfoDTO> listPersonInfo(String firstName, String lastName) {
		log.debug("call medicalrecord service - list pesronInfo");
		List<Person> persons = personRepository.searchByFirstNameAndLastName(firstName, lastName);
		return persons.stream().map(
				(Person person) ->
				personMapper.personToPersonInfo(person)).collect(Collectors.toList());
	}

	public FirestationAffectationDTO searchFirestationAffectationFromAddress(String address) {

		List<Person> persons =  personRepository.findByAddress(address);
		long nbAdult = persons.stream().filter(this::isAdult).count();
		long nbChild = persons.size() - nbAdult;

		return FirestationAffectationDTO.builder().numberAdult(nbAdult).numberChild(nbChild).persons(personMapper.personsToPersonDTOs(persons)).build();

	}

	public List<PersonDTO> getByAddress(String address) {
		return personMapper.personsToPersonDTOs(personRepository.findByAddress(address));
	}
	public List<PersonInfoDTO> getPersonInfoByAddress(String address) {
		return personMapper.personsToPersonInfos(personRepository.findByAddress(address));
	}

	private boolean isAdult(Person person) {
		return Period.between(person.getBirthdate(), LocalDate.now()).getYears() > 17;
	}

	private Boolean isChild(Person person) {
		return !isAdult(person);
	}



    public List<ChildDTO> listChild(String address) {
		Map<PersonDTO, List<PersonDTO>> family = new HashMap<>();
		List<Person> persons = personRepository.findByAddress(address);
		List<Person> childs = persons.stream().filter(this::isChild).collect(Collectors.toList());
		List<ChildDTO> chilDTOS = new ArrayList<>();
		childs.forEach( child -> {
			PersonInfoDTO personInfoDTO = personMapper.personToPersonInfo(child);

			List<PersonDTO> personAtSameAddress = personMapper.personsToPersonDTOs(
					persons.stream().filter((Person person) -> !person.getFirstName().equals(child.getFirstName()))
							.collect(Collectors.toList())
			);

			chilDTOS.add(ChildDTO.builder()
					.personAtSameAddress(
							personAtSameAddress
					)
					.age(personInfoDTO.getAge())
					.firstName(personInfoDTO.getFirstName())
					.lastName(personInfoDTO.getLastName()).build());

		});
		return chilDTOS;
    }


	public List<PersonInfoDTO> getByAddressIn(List<String> addresses) {
		return personMapper.personsToPersonInfos(personRepository.findByAddressIn(addresses));
	}
}
