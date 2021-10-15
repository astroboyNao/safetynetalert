package com.safetynet.alerts.common;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

@ExtendWith(MockitoExtension.class)
public class LocalDateDeserializerTest {
	private LocalDateDeserializer localDateDeserialiser;

	@BeforeEach()
	public void setup() {
		this.localDateDeserialiser = new LocalDateDeserializer();
	}

	@Test
	public void shouldDeserialize() throws IOException {
		JsonParser jsonParser = mock(JsonParser.class);
		DeserializationContext deserializationContext = mock(DeserializationContext.class);

		LocalDate expectedLocalDate = LocalDate.of(2021, 12, 31);
		when(jsonParser.readValueAs(String.class)).thenReturn("2021-12-31");

		LocalDate localDateReturned = this.localDateDeserialiser.deserialize(jsonParser, deserializationContext);
		assertTrue(expectedLocalDate.equals(localDateReturned));
	}
}