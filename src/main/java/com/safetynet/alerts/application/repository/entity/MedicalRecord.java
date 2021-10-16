package com.safetynet.alerts.application.repository.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class MedicalRecord {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The person. */
	@OneToOne(fetch = FetchType.EAGER)
	private Person person;

	/** The medications. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Medication> medications;

	/** The allergies. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Allergie> allergies;
}
