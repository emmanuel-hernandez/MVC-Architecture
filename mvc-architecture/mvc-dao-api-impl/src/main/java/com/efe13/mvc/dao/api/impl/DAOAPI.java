package com.efe13.mvc.dao.api.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.efe13.mvc.commons.api.exception.DAOException;
import com.efe13.mvc.dao.api.IDAO;
import com.efe13.mvc.model.api.impl.entity.EntityAPI;

public abstract class DAOAPI<T> implements IDAO<EntityAPI> {

	private final static SessionFactory sessionFactory = createSessionFactory();
	private final Class<T> persistenteClass;
	private final static Logger log = Logger.getLogger( DAOAPI.class );
	
	public DAOAPI() {
		persistenteClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass() ).getActualTypeArguments()[0];
	}

	@Override
	public EntityAPI getById(EntityAPI object) throws HibernateException, DAOException {
		throw new DAOException( "This method has not implementation" );
	}

	@Override
	public List<EntityAPI> getAll() {
		try {
			return getSession().createQuery( "FROM " + persistenteClass.getName() ).list();
		}
		catch( Exception ex ) {
			log.error( ex.getMessage() );
			throw ex;
		}
	}

	@Override
	public int save(EntityAPI object) throws HibernateException {
		return (Integer) getSession().save( object );
	}

	@Override
	public boolean update(EntityAPI object) throws HibernateException {
		try {
			getSession().update( object );
			return true;
		}
		catch( Exception ex ) {
			log.error( ex.getMessage() );
			throw ex;
		}
	}

	@Override
	public boolean delete(EntityAPI object) {
		try {
			getSession().delete( object );
			return true;
		}
		catch( Exception ex ) {
			log.error( ex.getMessage() );
			throw ex;
		}
	}
	
	protected Session getSession() {
		return sessionFactory.openSession();
	}
	
	private static SessionFactory createSessionFactory() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		
		try {
			return new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch( Exception ex ) {
			StandardServiceRegistryBuilder.destroy( registry );
			System.out.println( "EXCEPTION !!!!!!!" );
			ex.printStackTrace();
			//log.error( ex.getMessage() );
			
			return null;
		}
	}
}