package com.safetynet.alerts.application.mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.safetynet.alerts.application.dto.MedicalrecordDTO;
import com.safetynet.alerts.application.dto.PersonDTO;
import com.safetynet.alerts.application.dto.PersonInfoDTO;
import com.safetynet.alerts.application.repository.entity.Allergie;
import com.safetynet.alerts.application.repository.entity.Medication;
import com.safetynet.alerts.application.repository.entity.Person;

/**
 * The Interface PersonMapper.
 */
@Mapper
public interface PersonMapper {

	/**
	 * Person to person DTO.
	 *
	 * @param person the person
	 * @return the person DTO
	 */
	PersonDTO personToPersonDTO(Person person);

	/**
	 * Person DTO to person.
	 *
	 * @param personDTO the person DTO
	 * @return the person
	 */
	Person personDTOToPerson(PersonDTO personDTO);

	/**
	 * Persons to person DT os.
	 *
	 * @param persons the persons
	 * @return the list
	 */
	List<PersonDTO> personsToPersonDTOs(List<Person> persons);

	/**
	 * Person DT os to persons.
	 *
	 * @param personDTOs the person DT os
	 * @return the list
	 */
	List<Person> personDTOsToPersons(List<PersonDTO> personDTOs);

	/**
	 * Medicalrecord DT os to personn.
	 *
	 * @param medicalRecordDTO the medical record DTO
	 * @return the person
	 */
	Person medicalrecordDTOsToPersonn(MedicalrecordDTO medicalRecordDTO);


	/**
	 * Person to person info.
	 *
	 * @param person the person
	 * @return the person info DTO
	 */
	@Mappings({
		@Mapping(source = "medicalRecord.allergies", target = "allergies"),
		@Mapping(source = "medicalRecord.medications", target = "medications"),
		@Mapping(source = "lastName", target = "lastName"),
		@Mapping(source = "address", target = "address"),
		@Mapping(source = "city", target = "city"),
		@Mapping(source = "zip", target = "zip"),
		@Mapping(source = "email", target = "email"),
		@Mapping(source = "birthdate", target = "age")
	})
	PersonInfoDTO personToPersonInfo(Person person);

	/**
	 * Persons to person infos.
	 *
	 * @param persons the persons
	 * @return the list
	 */
	List<PersonInfoDTO> personsToPersonInfos(List<Person> persons);

	/**
	 * From string to allergie.
	 *
	 * @param allergie the allergie
	 * @return the allergie
	 */
	default Allergie fromStringToAllergie(String allergie) {
		return Allergie.builder().name(allergie).build();
	}


	/**
	 * From string to medication.
	 *
	 * @param medication the medication
	 * @return the medication
	 */
	default Medication fromStringToMedication(String medication) {
		return Medication.builder().name(medication).build();
	}

	/**
	 * From medication to string.
	 *
	 * @param medication the medication
	 * @return the string
	 */
	default String fromMedicationToString(Medication medication) {
		return medication.getName();
	}

	/**
	 * From allergie to string.
	 *
	 * @param allergie the allergie
	 * @return the string
	 */
	default String fromAllergieToString(Allergie allergie) {
		return allergie.getName();
	}

	/**
	 * From birthdate toint.
	 *
	 * @param birthdate the birthdate
	 * @return the int
	 */
	default int fromBirthdateToint(LocalDate birthdate) {
		return Period.between(birthdate, java.time.LocalDate.now()).getYears();
	}
}
