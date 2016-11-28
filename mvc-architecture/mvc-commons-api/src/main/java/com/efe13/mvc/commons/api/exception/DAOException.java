package com.efe13.mvc.commons.api.exception;

public class DAOException extends RuntimeException {
	
	private static final long serialVersionUID = System.currentTimeMillis();

	public DAOException( String message ) {
		super( message );
	}
}