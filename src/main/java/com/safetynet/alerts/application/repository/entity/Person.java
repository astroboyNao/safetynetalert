package com.safetynet.alerts.application.repository.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** The first name. */
	@Column(name = "FIRSTNAME")
	private String firstName;
	
	/** The last name. */
	@Column(name = "LASTNAME")
	private String lastName;
	
	/** The birthdate. */
	private LocalDate birthdate;
	
	/** The address. */
	private String address;
	
	/** The city. */
	private String city;
	
	/** The zip. */
	private String zip;
	
	/** The phone. */
	private String phone;
	
	/** The email. */
	private String email;

	/** The medical record. */
	@OneToOne(fetch = FetchType.LAZY)
	private MedicalRecord medicalRecord;


}
