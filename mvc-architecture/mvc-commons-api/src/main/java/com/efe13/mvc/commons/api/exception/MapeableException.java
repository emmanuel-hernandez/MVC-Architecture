package com.efe13.mvc.commons.api.exception;

public class MapeableException extends RuntimeException {
	
	private static final long serialVersionUID = System.currentTimeMillis();

	public MapeableException( String message ) {
		super( message );
	}
}