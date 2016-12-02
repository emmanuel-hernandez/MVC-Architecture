package com.efe13.mvc.commons.api.util;

import com.efe13.mvc.commons.api.exception.ValidationException;

public abstract class Utilities<T> {

	public abstract void validateDTO( T dto ) throws ValidationException;

}