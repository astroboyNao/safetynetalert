package com.safetynet.alerts.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.application.dto.MedicalrecordDTO;
import com.safetynet.alerts.application.exception.ExistException;
import com.safetynet.alerts.application.exception.NotFoundException;
import com.safetynet.alerts.application.mapper.MedicalrecordMapper;
import com.safetynet.alerts.application.repository.MedicalRepository;
import com.safetynet.alerts.application.repository.PersonRepository;
import com.safetynet.alerts.application.repository.entity.MedicalRecord;
import com.safetynet.alerts.application.repository.entity.Person;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class MedicalRecordService.
 */
@Service

/**
 * Instantiates a new medical record service.
 *
 * @param personRepository the person repository
 * @param medicalRepository the medical repository
 * @param medicalrecordMapper the medicalrecord mapper
 */
@AllArgsConstructor
/** The Constant log. */
@Slf4j
public class MedicalRecordService {

	/** The person repository. */
	private PersonRepository personRepository;

	/** The person repository. */
	private MedicalRepository medicalRepository;

	/** The medicalrecord mapper. */
	private MedicalrecordMapper medicalrecordMapper;

	/**
	 * List.
	 *
	 * @return the list
	 */
	public List<MedicalrecordDTO> list() {
		log.debug("call medicalrecord service - list");
		List<MedicalRecord> medicalRecords = StreamSupport
				.stream(medicalRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());

		return medicalrecordMapper.medicalRecordsToMedicalrecordDTOs(medicalRecords);
	}

	/**
	 * Create.
	 *
	 * @param medicalRecordDTO the medical record DTO
	 * @return the medicalrecord DTO
	 */
	public MedicalrecordDTO create(MedicalrecordDTO medicalRecordDTO) {
		log.debug("call medicalrecord service - save");

		Person person = findPerson(medicalRecordDTO);

		MedicalRecord exist = medicalRepository.findByPerson(person);

		if (exist != null) {
			log.error("medicalRecord exist !!!");
			throw new ExistException("Un dossier medical existe déjà !");
		}

		MedicalRecord medicalRecord = medicalrecordMapper.medicalrecordDTOToMedicalRecord(medicalRecordDTO);
		medicalRecord.setPerson(person);
		
		medicalRecord = medicalRepository.save(medicalRecord);

		saveBirthdateForPerson(person, medicalRecord, medicalRecordDTO.getBirthdate());

		return medicalrecordMapper.medicalRecordToMedicalrecordDTO(medicalRecord);
	}

	/**
	 * Save.
	 *
	 * @param medicalRecordDTO the medical record DTO
	 * @return the medicalrecord DTO
	 */
	public MedicalrecordDTO save(MedicalrecordDTO medicalRecordDTO) {
		log.debug("call medicalrecord service - save");

		Person person = findPerson(medicalRecordDTO);

		MedicalRecord medicalRecord = findMedicalRecord(person);

		medicalrecordMapper.update(medicalRecord, medicalRecordDTO);

		medicalRecord = medicalRepository.save(medicalRecord);

		saveBirthdateForPerson(person, medicalRecord, medicalRecordDTO.getBirthdate());

		return medicalrecordMapper.medicalRecordToMedicalrecordDTO(medicalRecord);
	}

	private void saveBirthdateForPerson(Person person, MedicalRecord medicalRecord, LocalDate birthDate) {
		person.setBirthdate(birthDate);
		person.setMedicalRecord(medicalRecord);
		personRepository.save(person);
	}
	/**
	 * Find medical record.
	 *
	 * @param person the person
	 * @return the medical record
	 */
	private MedicalRecord findMedicalRecord(Person person) {
		MedicalRecord medicalRecord = medicalRepository.findByPerson(person);

		if (medicalRecord == null) {
			log.error("medicalRecord not found !!!");
			throw new NotFoundException("dossier médical non trouvé !");
		}
		return medicalRecord;
	}


	/**
	 * Find person.
	 *
	 * @param medicalRecordDTO the medical record DTO
	 * @return the person
	 */
	private Person findPerson(MedicalrecordDTO medicalRecordDTO) {
		Person person = personRepository.findByFirstNameAndLastName(medicalRecordDTO.getFirstName(),
				medicalRecordDTO.getLastName());

		if (person == null) {
			log.error("person not exist for this medicalrecord !");
			throw new NotFoundException("La personne n'existe pas, veuillez la créer dans le référentiel avant de créer ou mettre à jours son dossier médicale !");
		}

		return person;
	}

	/**
	 * Delete.
	 *
	 * @param medicalRecordDTO the medical record DTO
	 */
	public void delete(MedicalrecordDTO medicalRecordDTO) {
		log.debug("call medicalrecord service - delete");

		Person person = findPerson(medicalRecordDTO);

		MedicalRecord medicalRecord = findMedicalRecord(person);

		medicalRepository.delete(medicalRecord);
	}
}
