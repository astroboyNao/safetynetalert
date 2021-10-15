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
	@JsonInclude(Include.NON_EMPTY)
	private String firstName;
	@JsonInclude(Include.NON_EMPTY)
	private String lastName;
	private String address;
	private String city;
	private String zip;
	private String phone;
	private String email;
}