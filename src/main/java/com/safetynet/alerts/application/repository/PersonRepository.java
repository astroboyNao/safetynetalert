package com.safetynet.alerts.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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

	/**
	 * Find by city.
	 *
	 * @param city the city
	 * @return the list
	 */
	public List<Person> findByCity(String city);

	/**
	 * Search by first name and last name.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @return the list
	 */
	@Query("SELECT P FROM Person P WHERE LOWER(P.firstName) LIKE LOWER(concat('%', concat(?1, '%'))) OR LOWER(P.lastName) LIKE LOWER(concat('%', concat(?2, '%'))) ")
	List<Person> searchByFirstNameAndLastName(String firstName, String lastName);

	/**
	 * Find by address.
	 *
	 * @param address the address
	 * @return the list
	 */
	public List<Person> findByAddress(String address);

    /**
     * Find by address in.
     *
     * @param addresses the addresses
     * @return the list
     */
    List<Person> findByAddressIn(List<String> addresses);
}
