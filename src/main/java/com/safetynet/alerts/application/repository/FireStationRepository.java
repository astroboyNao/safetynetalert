package com.safetynet.alerts.application.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.application.repository.entity.Firestation;

/**
 * The Interface FireStationRepository.
 */
@Repository
public interface FireStationRepository extends CrudRepository<Firestation, String> {

	/**
	 * Find by address.
	 *
	 * @param address the address
	 * @return the firestation
	 */
	public Firestation findByAddress(String address);

	/**
	 * Find by station.
	 *
	 * @param station the station
	 * @return the firestation
	 */
	public Firestation findByStation(long station);

	/**
	 * Find by station in.
	 *
	 * @param stations the stations
	 * @return the list
	 */
	public List<Firestation> findByStationIn(long[] stations);

}
