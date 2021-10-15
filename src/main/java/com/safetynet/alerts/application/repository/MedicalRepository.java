package com.safetynet.alerts.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.application.repository.entity.MedicalRecord;
import com.safetynet.alerts.application.repository.entity.Person;
/**
 * The Interface PersonRepository.
 */
@Repository
public interface MedicalRepository extends CrudRepository<MedicalRecord, Long> {
	
	/**
	 * Find by person.
	 *
	 * @param person the person
	 * @return the person
	 */
	public MedicalRecord findByPerson(Person person);


	
}
