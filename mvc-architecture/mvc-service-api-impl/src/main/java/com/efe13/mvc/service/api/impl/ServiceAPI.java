package com.efe13.mvc.service.api.impl;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.efe13.mvc.commons.api.exception.ServiceException;
import com.efe13.mvc.commons.api.interfaces.Mappeable;
import com.efe13.mvc.commons.api.util.ServiceUtilities;
import com.efe13.mvc.model.api.impl.dto.DTOAPI;
import com.efe13.mvc.service.api.IService;

public abstract class ServiceAPI extends ServiceUtilities<DTOAPI> implements IService<DTOAPI> {

	@Override
	public long getTableCount() {
		throw new ServiceException( "This method has not implementation. It needs to be implemented by the concrete class" );
	}
	
	@Override
	public DTOAPI getById(DTOAPI object) {
		throw new ServiceException( "This method has not implementation. It needs to be implemented by the concrete class" );
	}

	@Override
	public <E> List<DTOAPI> getAll(E queryHelper) {
		throw new ServiceException( "This method has not implementation. It needs to be implemented by the concrete class" );
	}
	
	@Override
	public Number save(DTOAPI object) {
		throw new ServiceException( "This method has not implementation. It needs to be implemented by the concrete class" );
	}

	@Override
	public Boolean update(DTOAPI object) {
		throw new ServiceException( "This method has not implementation. It needs to be implemented by the concrete class" );
	}

	@Override
	public Boolean delete(DTOAPI object) {
		throw new ServiceException( "This method has not implementation. It needs to be implemented by the concrete class" );
	}

	@Override
	public Mappeable map(Mappeable source, Mappeable destination) {
		destination = new ModelMapper().map( source, destination.getClass() );
		return destination;
	}
}