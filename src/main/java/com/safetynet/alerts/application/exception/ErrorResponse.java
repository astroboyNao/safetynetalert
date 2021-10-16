package com.safetynet.alerts.application.exception;

import lombok.Builder;
import lombok.Getter;

/**
 * Gets the msg.
 *
 * @return the msg
 */
@Getter

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class ErrorResponse {
	
	/** The status. */
	private String status;
	
	/** The msg. */
	private String msg;
}
