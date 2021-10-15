package com.safetynet.alerts.application.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
	private String status;
	private String msg;
}
