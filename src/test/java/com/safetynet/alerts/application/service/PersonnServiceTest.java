package com.safetynet.alerts.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.safetynet.alerts.application.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.application.mapper.PersonMapper;
import com.safetynet.alerts.application.repository.PersonRepository;
import com.safetynet.alerts.application.repository.entity.Person;

@ExtendWith(MockitoExtension.class)
public class PersonnServiceTest {
	private PersonService personService;
	@Mock
	private PersonRepository personRepository;
	@Mock
	private PersonMapper personMapper;

	@BeforeEach
	public void setup() {
		this.personService = new PersonService(personRepository, personMapper);
	}

	@Test
	public void shouldGetList() {
		List<Person> persons = new ArrayList<Person>();
		persons.add(mock(Person.class));

		List<PersonDTO> personDTOs = new ArrayList<PersonDTO>();
		personDTOs.add(mock(PersonDTO.class));

		when(personRepository.findAll()).thenReturn(persons);
		when(personMapper.personsToPersonDTOs(anyList())).thenReturn(personDTOs);

		List<PersonDTO> checkPersonDTOs = this.personService.list();

		verify(personRepository, Mockito.times(1)).findAll();
		verify(personMapper, Mockito.times(1)).personsToPersonDTOs(persons);
		assertEquals(checkPersonDTOs, personDTOs);
	}

	@Test
	public void shouldSave() {

		PersonDTO personDTO = mock(PersonDTO.class);
		Person person = mock(Person.class);

		when(personMapper.personDTOToPerson(any(PersonDTO.class))).thenReturn(person);
		when(personRepository.findByFirstNameAndLastName(personDTO.getFirstName(), personDTO.getLastName())).thenReturn(person);
		this.personService.save(personDTO);

		verify(personRepository, Mockito.times(1)).save(person);
		verify(personMapper, Mockito.times(1)).personDTOToPerson(personDTO);

	}

	@Test
	public void shouldDelete() {

		PersonDTO personDTO = mock(PersonDTO.class);
		Person person = mock(Person.class);
		when(personRepository.findByFirstNameAndLastName(personDTO.getFirstName(), personDTO.getLastName()))
				.thenReturn(person);

		this.personService.delete(personDTO);

		verify(personRepository, Mockito.times(1)).delete(person);

	}

	@Test
	public void getByAddressIn() {
		when(personRepository.findByAddressIn(List.of("addresses"))).thenReturn(List.of(mock(Person.class)));
		when(personMapper.personsToPersonInfos(any(List.class))).thenReturn(List.of(mock(PersonInfoDTO.class)));

		this.personService.getByAddressIn(List.of("addresses"));

		verify(personRepository, Mockito.times(1)).findByAddressIn(anyList());
		verify(personMapper, Mockito.times(1)).personsToPersonInfos(anyList());
	}


	@Test
	public void listPersonInfo() {
		when(personRepository.searchByFirstNameAndLastName(anyString(), anyString())).thenReturn(List.of(mock(Person.class)));
		when(personMapper.personToPersonInfo(any(Person.class))).thenReturn(mock(PersonInfoDTO.class));

		List<PersonInfoDTO> listExpected = this.personService.listPersonInfo("firstName", "lastName");

		verify(personRepository, Mockito.times(1)).searchByFirstNameAndLastName(anyString(),anyString());
		verify(personMapper, Mockito.times(1)).personToPersonInfo(any(Person.class));

		assertNotNull(listExpected);
		assertEquals(1, listExpected.size());

	}

	@Test
	public void  listEmail() {
		Person person = new Person();
		person.setEmail("email");
		when(personRepository.findByCity(anyString())).thenReturn(List.of(person));
		List<MailDTO> emailsExpected = this.personService.listEmail("city");

		verify(personRepository, Mockito.times(1)).findByCity("city");

		assertNotNull(emailsExpected);
		assertEquals(1, emailsExpected.size());
		assertEquals("email", emailsExpected.get(0).getEmail());

	}

	@Test
	public void  searchFirestationAffectationFromAddress() {
		Person adult = new Person();
		adult.setBirthdate(LocalDate.now().minusYears(18));
		Person child1 = new Person();
		child1.setBirthdate(LocalDate.now().minusYears(10));
		Person child2 = new Person();
		child2.setBirthdate(LocalDate.now().minusYears(15));

		when(personRepository.findByAddress(anyString())).thenReturn(List.of(adult, child1, child2));
		when(personMapper.personsToPersonDTOs(anyList())).thenReturn(List.of(mock(PersonDTO.class)));

		FirestationAffectationDTO fireAffectation = this.personService.searchFirestationAffectationFromAddress("address");

		verify(personRepository).findByAddress(anyString());
		assertEquals(1, fireAffectation.getNumberAdult());
		assertEquals(2, fireAffectation.getNumberChild());
		assertNotNull(fireAffectation.getPersons());
		assertEquals(1, fireAffectation.getPersons().size());

	}

	@Test
	public void getByAddress() {
		when(personRepository.findByAddress(anyString())).thenReturn(List.of(mock(Person.class)));
		when(personMapper.personsToPersonDTOs(anyList())).thenReturn(List.of(mock(PersonDTO.class)));

		this.personService.getByAddress("address");

		verify(personRepository, Mockito.times(1)).findByAddress(anyString());
	}


	@Test
	public void getPersonInfoByAddress() {
		when(personRepository.findByAddress(anyString())).thenReturn(List.of(mock(Person.class)));
		when(personMapper.personsToPersonInfos(anyList())).thenReturn(List.of(mock(PersonInfoDTO.class)));

		this.personService.getPersonInfoByAddress("address");

		verify(personRepository, Mockito.times(1)).findByAddress(anyString());
	}

	@Test
	public void listChild() {
		Person adult = new Person();
		adult.setBirthdate(LocalDate.now().minusYears(18));
		adult.setFirstName("adultFirstName");
		Person child1 = new Person();
		child1.setFirstName("child1FirstName");
		child1.setBirthdate(LocalDate.now().minusYears(10));
		Person child2 = new Person();
		child2.setFirstName("child2FirstName");
		child2.setBirthdate(LocalDate.now().minusYears(15));
		PersonInfoDTO childDTO1 = PersonInfoDTO.builder().firstName("child1").build();
		PersonInfoDTO childDTO2 = PersonInfoDTO.builder().firstName("child2").build();
		PersonDTO personDTO = PersonDTO.builder().firstName("firstName").lastName("lastName").build();


		when(personRepository.findByAddress(anyString())).thenReturn(List.of(adult, child1, child2));
		when(personMapper.personToPersonInfo(child1)).thenReturn(childDTO1);
		when(personMapper.personToPersonInfo(child2)).thenReturn(childDTO2);
		when(personMapper.personsToPersonDTOs(anyList())).thenReturn(List.of(personDTO));

		List<ChildDTO> childDTOs = this.personService.listChild("address");

		verify(personRepository, Mockito.times(1)).findByAddress(anyString());
		assertEquals(2, childDTOs.size());
		assertTrue(childDTOs.stream().anyMatch(e -> e.getFirstName().equals(childDTO1.getFirstName())));
		assertNotNull(childDTOs.stream().filter(e -> e.getFirstName().equals(childDTO2.getFirstName())).findFirst().get().getPersonAtSameAddress());
		assertTrue(childDTOs.stream().anyMatch(e -> e.getFirstName().equals(childDTO1.getFirstName())));
		assertNotNull(childDTOs.stream().filter(e -> e.getFirstName().equals(childDTO2.getFirstName())).findFirst().get().getPersonAtSameAddress());


	}
}
