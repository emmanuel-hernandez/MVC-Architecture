package com.efe13.mvc.commons.api.exception;

public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = System.currentTimeMillis();

	public ServiceException( String message ) {
		super( message );
	}
}