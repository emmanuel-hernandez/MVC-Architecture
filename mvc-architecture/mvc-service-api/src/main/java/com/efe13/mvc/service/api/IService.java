package com.efe13.mvc.service.api;

import com.efe13.mvc.commons.api.interfaces.CrudOperations;
import com.efe13.mvc.commons.api.interfaces.Mappeable;
import com.efe13.mvc.model.api.dto.IDTO;

public interface IService<T extends IDTO> extends CrudOperations<T> {

	public Mappeable map( Mappeable source, Mappeable destination );
}