package com.safetynet.alerts.common;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * The Class LocalDateDeserializer.
 */
public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

	/**
	 * Instantiates a new local date deserializer.
	 */
	protected LocalDateDeserializer() {
		super(LocalDate.class);
	}

	/**
	 * Deserialize.
	 *
	 * @param parser the parser
	 * @param context the context
	 * @return the local date
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		return LocalDate.parse(parser.readValueAs(String.class));
	}
}