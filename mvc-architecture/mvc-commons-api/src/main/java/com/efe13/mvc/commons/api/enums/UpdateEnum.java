package com.efe13.mvc.commons.api.enums;

public enum UpdateEnum {

	IS_UPDATE( true ),
	IS_NOT_UPDATE( false );
	
	private boolean value;
	
	private UpdateEnum( boolean value ) {
		this.value = value;
	}
	
	public boolean getValue() {
		return this.value;
	}
}
