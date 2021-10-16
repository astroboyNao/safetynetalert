package com.safetynet.alerts.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GrptStationDTO {
	
	/** The station. */
	private Long station;
	
	/** The persons. */
	private List<PersonInfoDTO> persons;
}
