package com.safetynet.alerts.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.application.repository.entity.MedicalRecord;
import com.safetynet.alerts.application.repository.entity.Person;

/**
 * The Interface MedicalRepository.
 */
@Repository
public interface MedicalRepository extends CrudRepository<MedicalRecord, Long> {

	/**
	 * Find by person.
	 *
	 * @param person the person
	 * @return the medical record
	 */
	public MedicalRecord findByPerson(Person person);



}
