package com.safetynet.alerts.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.safetynet.alerts.application.dto.ChildDTO;
import com.safetynet.alerts.application.dto.MailDTO;
import com.safetynet.alerts.application.dto.PersonDTO;
import com.safetynet.alerts.application.dto.PersonInfoDTO;
import com.safetynet.alerts.application.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {
	@Mock
	private PersonService personService;

	@InjectMocks
	private PersonController personController;

	@Captor
	ArgumentCaptor<PersonDTO> personTOCaptor;

	@Test
	public void shouldReturnList() {
		List<PersonDTO> personDTOs = new ArrayList<PersonDTO>();
		personDTOs.add(mock(PersonDTO.class));

		when(personService.list()).thenReturn(personDTOs);

		List<PersonDTO> response = personController.list();
		assertEquals(response, personDTOs);
		verify(personService, Mockito.times(1)).list();

	}

	@Test
	public void shouldSave() {
		PersonDTO personDTO = (mock(PersonDTO.class));

		personController.save(personDTO);

		verify(personService, Mockito.times(1)).save(personDTO);
	}

	@Test
	public void shouldDelete() {
		PersonDTO personDTO = (mock(PersonDTO.class));

		personController.delete(personDTO.getFirstName(), personDTO.getLastName());

		verify(personService, Mockito.times(1)).delete(personTOCaptor.capture());
		PersonDTO checkPersonDTO = personTOCaptor.getValue();

		assertEquals(checkPersonDTO.getFirstName(), personDTO.getFirstName());
		assertEquals(checkPersonDTO.getLastName(), personDTO.getLastName());
	}
	

	@Test 
	public void shouldListEmail() {
		String city = "city";
		List<MailDTO> mailDTOs = new ArrayList<MailDTO>();
		mailDTOs.add(mock(MailDTO.class));

		when(personService.listEmail(city)).thenReturn(mailDTOs);

		List<MailDTO> mails = personController.listEmail(city);

		verify(personService, Mockito.times(1)).listEmail(city);
		assertEquals(mailDTOs, mails);

	}
	

	@Test 
	public void listPersonInfo() {
		String firstName = "firstName";
		String lastName = "lastName";
		
		List<PersonInfoDTO> personInfoDTOs = new ArrayList();
		personInfoDTOs.add(mock(PersonInfoDTO.class));
		
		when(personService.listPersonInfo(firstName, lastName)).thenReturn(personInfoDTOs);


		List<PersonInfoDTO> personInfos = personController.listPersonInfo(firstName, lastName);
		
		verify(personService, Mockito.times(1)).listPersonInfo(firstName, lastName);
		assertEquals(personInfoDTOs, personInfos);
	}
	

	@Test
	public void listChild() {
		String address = "address";
		List<ChildDTO> childDTOs = new ArrayList();
		childDTOs.add(mock(ChildDTO.class));
		when(personService.listChild(address)).thenReturn(childDTOs);
		
		List<ChildDTO> childs = this.personController.listChild(address);
		verify(personService, Mockito.times(1)).listChild(address);
		assertEquals(childDTOs, childs);
	}
}
