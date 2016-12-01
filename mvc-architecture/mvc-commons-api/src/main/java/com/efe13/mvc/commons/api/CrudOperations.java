/**
 * Co
 */
package com.efe13.mvc.commons.api;

import java.util.List;

/**
 * 
 * @author Emmanuel Hernández
 *
 * @param <T> Entity on which this operations are going to be performed
 */
public interface CrudOperations<T> {
	
	public T getById( T object );
	
	public List<T> getAll();
	
	public Number save( T object );
	
	public Boolean update( T object );
	
	public Boolean delete( T object );

}