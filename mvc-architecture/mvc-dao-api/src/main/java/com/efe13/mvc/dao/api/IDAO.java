package com.efe13.mvc.dao.api;

import com.efe13.mvc.commons.api.interfaces.CrudOperations;

public interface IDAO<T> extends CrudOperations<T> {

	public long getTableCount();
}