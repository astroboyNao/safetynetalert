package com.safetynet.alerts.common;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

@ExtendWith(MockitoExtension.class)
public class LocalDateSerializerTest {
	private LocalDateSerializer localDateSerializer;

	@BeforeEach()
	public void setup() {
		this.localDateSerializer = new LocalDateSerializer();
	}

	@Test
	public void shouldSerialize() throws IOException {
		JsonGenerator jsonGenerator = mock(JsonGenerator.class);
		SerializerProvider serializerProvider = mock(SerializerProvider.class);
		LocalDate expectedLocalDate = LocalDate.of(2021, 12, 31);

		this.localDateSerializer.serialize(expectedLocalDate, jsonGenerator, serializerProvider);
		verify(jsonGenerator, Mockito.times(1)).writeString("2021-12-31");
	}

}
