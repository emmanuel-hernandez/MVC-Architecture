package com.efe13.mvc.service.api.impl;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.efe13.mvc.commons.api.Mappeable;
import com.efe13.mvc.commons.api.exception.ServiceException;
import com.efe13.mvc.model.api.impl.dto.DTOAPI;
import com.efe13.mvc.service.api.IService;

public abstract class ServiceAPI implements IService<DTOAPI> {

	@Override
	public DTOAPI getById(DTOAPI object) {
		throw new ServiceException( "This method has not implementation. It needs to be implemented by the concrete class" );
	}

	@Override
	public List<DTOAPI> getAll() {
		throw new ServiceException( "This method has not implementation. It needs to be implemented by the concrete class" );
	}

	@Override
	public int save(DTOAPI object) {
		throw new ServiceException( "This method has not implementation. It needs to be implemented by the concrete class" );
	}

	@Override
	public boolean update(DTOAPI object) {
		throw new ServiceException( "This method has not implementation. It needs to be implemented by the concrete class" );
	}

	@Override
	public boolean delete(DTOAPI object) {
		throw new ServiceException( "This method has not implementation. It needs to be implemented by the concrete class" );
	}
	
	@Override
	public Mappeable map( Mappeable source, Mappeable destination ) {
		return new ModelMapper().map( source, destination.getClass() );
	}

}