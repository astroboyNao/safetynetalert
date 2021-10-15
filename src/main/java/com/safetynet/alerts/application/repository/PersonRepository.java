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
public interface PersonRepository extends CrudRepository<Person, Long> {
	
	/**
	 * Find by first name and last name.
	 *
	 * @param FirstName the first name
	 * @param LastName the last name
	 * @return the person
	 */
	public Person findByFirstNameAndLastName(String FirstName, String LastName);

	public List<Person> findByCity(String city);

	@Query("SELECT P FROM Person P WHERE LOWER(P.firstName) LIKE LOWER(concat('%', concat(?1, '%'))) OR LOWER(P.lastName) LIKE LOWER(concat('%', concat(?2, '%'))) ")
	List<Person> searchByFirstNameAndLastName(String firstName, String lastName);
	
	public List<Person> findByAddress(String address);

    List<Person> findByAddressIn(List<String> addresses);
}
