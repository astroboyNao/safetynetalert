package com.safetynet.alerts.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ChildDTO {
	private String firstName;
	private String lastName;
	private long age;
	private List<PersonDTO> personAtSameAddress;
}
