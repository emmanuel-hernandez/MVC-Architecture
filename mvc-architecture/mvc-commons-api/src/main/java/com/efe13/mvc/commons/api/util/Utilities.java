package com.efe13.mvc.commons.api.util;

import com.efe13.mvc.commons.api.enums.UpdateEnum;
import com.efe13.mvc.commons.api.exception.ValidationException;

public abstract class Utilities<T> {

	public abstract void validateDTO(T dto, UpdateEnum isUpdate) throws ValidationException;

	public abstract T sanitizeDTO(T dto) throws ValidationException;
}