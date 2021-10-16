package com.safetynet.alerts.application.dto;

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
public class FirestationDTO {
	
	/** The address. */
	private String address;
	
	/** The station. */
	private long station;
}
