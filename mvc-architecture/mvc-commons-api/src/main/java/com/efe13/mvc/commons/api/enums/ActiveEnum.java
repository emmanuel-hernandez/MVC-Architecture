package com.efe13.mvc.commons.api.enums;

public enum ActiveEnum {

	ACTIVE( true ),
	INACTIVE( false );
	
	private boolean value;
	
	private ActiveEnum( boolean value ) {
		this.value = value;
	}
	
	public boolean getValue() {
		return this.value;
	}
}
