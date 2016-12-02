package com.efe13.mvc.commons.api.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = System.currentTimeMillis();

	public ValidationException( String message ) {
		super( message );
	}
}
