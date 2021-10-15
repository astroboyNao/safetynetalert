package com.safetynet.alerts.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.application.dto.MailDTO;
import com.safetynet.alerts.application.dto.PersonInfoDTO;
import com.safetynet.alerts.application.service.MedicalRecordService;
import com.safetynet.alerts.application.service.PersonService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class SafetyNetController {

	/** The person service. */
	private PersonService personService;
	
	private MedicalRecordService medicalRecordService;
	
}
