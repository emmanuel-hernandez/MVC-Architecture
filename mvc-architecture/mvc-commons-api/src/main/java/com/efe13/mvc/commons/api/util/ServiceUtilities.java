package com.efe13.mvc.commons.api.util;

import com.efe13.mvc.commons.api.enums.UpdateEnum;
import com.efe13.mvc.commons.api.exception.ValidationException;
import com.efe13.mvc.model.api.impl.helper.QueryHelper;

public abstract class ServiceUtilities<T> {

	public abstract void validateDTO(T dto, UpdateEnum isUpdate) throws ValidationException;

	public abstract T sanitizeDTO(T dto) throws ValidationException;
	
	public QueryHelper getQueryHelper( long collectionSize, QueryHelper queryHelper ) {
		long totalPages = (long) Math.ceil( collectionSize / queryHelper.getPaginationAPI().getPageSize() );
		
		queryHelper.getPaginationAPI().setCollectionSize( (int)collectionSize );
		queryHelper.getPaginationAPI().setTotalPages( (int)totalPages );
		
		return queryHelper;
	}
}