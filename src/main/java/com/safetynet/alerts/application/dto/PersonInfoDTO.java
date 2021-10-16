package com.safetynet.alerts.application.dto;

import java.util.List;

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
public class PersonInfoDTO {
	private String lastName;
	private String firstName;
	private String address;
	private String city;
	private String zip;
	private String email;
	private int age;
	private List<String> medications;
	private List<String> allergies;
}
