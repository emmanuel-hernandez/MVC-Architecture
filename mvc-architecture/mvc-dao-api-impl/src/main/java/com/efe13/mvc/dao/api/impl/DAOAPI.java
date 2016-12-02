package com.efe13.mvc.dao.api.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
			return getSession().createQuery( "FROM " + persistenteClass.getSimpleName() ).list();
			
		}
		catch( Exception ex ) {
			log.error( ex.getMessage(), ex );
			throw ex;
		}
	}

	@Override
	public Number save(EntityAPI object) throws HibernateException {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Number generatedId = 0;
		
		try {
			generatedId = (Number) session.save( object );
			tx.commit();
		}
		catch( Exception ex ) {
			log.error( ex.getMessage(), ex );
			tx.rollback();
			throw ex;
		}
		
		return generatedId;
	}

	@Override
	public Boolean update(EntityAPI object) throws HibernateException {
		Session session = getSession();
		Transaction tx = session.beginTransaction();

		try {
			session.update( object );
			tx.commit();
			return true;
		}
		catch( Exception ex ) {
			log.error( ex.getMessage(), ex );
			tx.rollback();
			throw ex;
		}
	}

	@Override
	public Boolean delete(EntityAPI object) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.delete( object );
			tx.commit();
			return true;
		}
		catch( Exception ex ) {
			log.error( ex.getMessage(), ex );
			tx.rollback();
			throw ex;
		}
	}
	
	protected final Session getSession() {
		return sessionFactory.openSession();
	}
	
	private final static SessionFactory createSessionFactory() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		
		try {
			return new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch( Exception ex ) {
			StandardServiceRegistryBuilder.destroy( registry );
			log.error( ex.getMessage(), ex );
			throw ex;
		}
	}
}