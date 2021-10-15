package com.safetynet.alerts.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The Class LocalDateSerializer.
 */
public class LocalDateSerializer extends StdSerializer<LocalDate> {

	/**
	 * Instantiates a new local date serializer.
	 */
	public LocalDateSerializer() {
		super(LocalDate.class);
	}

	/**
	 * Serialize.
	 *
	 * @param value the value
	 * @param generator the generator
	 * @param provider the provider
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
	}
}
