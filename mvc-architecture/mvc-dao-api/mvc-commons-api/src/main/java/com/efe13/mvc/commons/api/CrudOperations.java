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
	
	public int save( T object );
	
	public boolean update( T object );
	
	public boolean delete( T object );

}