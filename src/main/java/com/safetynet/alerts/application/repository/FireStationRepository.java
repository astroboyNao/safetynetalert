package com.safetynet.alerts.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.application.repository.entity.Firestation;

import java.util.List;

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
	
	public Firestation findByStation(long station);

	public List<Firestation> findByStationIn(long[] stations);
	
}
