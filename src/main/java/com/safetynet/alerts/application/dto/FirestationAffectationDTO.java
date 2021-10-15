package com.safetynet.alerts.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FirestationAffectationDTO {
	private List<PersonDTO> persons;
	private long numberAdult;
	private long numberChild;
}
