package com.safetynet.alerts.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PersonDTO {
	
	/** The first name. */
	@JsonInclude(Include.NON_EMPTY)
	private String firstName;
	
	/** The last name. */
	@JsonInclude(Include.NON_EMPTY)
	private String lastName;
	
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
}