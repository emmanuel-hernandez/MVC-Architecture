package com.efe13.mvc.dao.api.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.efe13.mvc.commons.api.enums.ActiveEnum;
import com.efe13.mvc.commons.api.exception.DAOException;
import com.efe13.mvc.commons.api.util.Utils;
import com.efe13.mvc.dao.api.IDAO;
import com.efe13.mvc.model.api.impl.entity.EntityAPI;
import com.efe13.mvc.model.api.impl.helper.QueryHelper;

public abstract class DAOAPI<T> implements IDAO<EntityAPI> {

	private SessionFactory sessionFactory;
	private final Class<T> criteriaClass;
	private final static Logger log = Logger.getLogger( DAOAPI.class );
	
	private String columnNameForActiveElement;
	private ActiveEnum activeEnum;
	
	public DAOAPI(String columnNameForActiveElement, ActiveEnum activeEnum) {
		this.columnNameForActiveElement = columnNameForActiveElement;
		this.activeEnum = activeEnum;
		
		createSessionFactory();
		criteriaClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass() ).getActualTypeArguments()[0];
	}
	
	@Override
	public Integer getTotalRecords() {
		try {
			return (Integer) getSession().createQuery( "COUNT(*) FROM " + criteriaClass.getSimpleName() ).uniqueResult();
		}
		catch( Exception ex ) {
			log.error( ex.getMessage(), ex );
			throw ex;
		}
		finally {
			closeSession();
		}
	}

	@Override
	public EntityAPI getById( EntityAPI object ) throws HibernateException, DAOException {
		try {
			Criteria criteria = getCriteria()
				.add( Restrictions.idEq( object.getId() ) )
				.add( Restrictions.eq( columnNameForActiveElement, activeEnum.getValue() ) );
			
			return (EntityAPI) criteria.uniqueResult();
		}
		finally {
			closeSession();
		}
	}
	
	@Override
	public <E> List<EntityAPI> getAll( E helper ) {
		try {
			if( !Utils.isNull( helper ) && !(helper instanceof QueryHelper) ) {
				throw new RuntimeException( "Query Helper expected!" );
			}
			QueryHelper queryHelper = (QueryHelper) helper;
			
			Criteria criteria = getCriteria()
				.add( Restrictions.eq( columnNameForActiveElement, activeEnum.getValue() ) );
			
			if( !Utils.isNull( helper ) ) {
				if( !Utils.isNull( queryHelper.getPaginationAPI() ) ) {
					criteria.setFirstResult( queryHelper.getPaginationAPI().getPage() );
					criteria.setMaxResults( queryHelper.getPaginationAPI().getPageSize() );
				}
				
				if( !Utils.isNull( queryHelper.getOrderAPI() ) ) {
					criteria.addOrder( (queryHelper.getOrderAPI().isAscending()) ?
										Order.asc( queryHelper.getOrderAPI().getField() ) :
										Order.desc( queryHelper.getOrderAPI().getField() ) );
				}
			}
			
			return criteria.list();
		}
		finally {
			closeSession();
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
		finally {
			closeSession();
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
		finally {
			closeSession();
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
		finally {
			closeSession();
		}
	}
	
	
	protected final void closeSession() {
		/*if( sessionFactory != null && !sessionFactory.isClosed() ) {
			sessionFactory.close();
		}*/
	}
		
	protected Criteria getCriteria() {
		return getCriteria( "_this" );
	}
	
	protected Criteria getCriteria( String alias ) {
		if( Utils.isEmpty( alias ) )
			return getSession().createCriteria( criteriaClass );
		
		return getSession().createCriteria( criteriaClass, alias );
	}
	
	private final Session getSession() {
		return sessionFactory.openSession();
	}
	
	private final void createSessionFactory() {
		if( sessionFactory == null ) {
			final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
			
			try {
				sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
			}
			catch( Exception ex ) {
				StandardServiceRegistryBuilder.destroy( registry );
				log.error( ex.getMessage(), ex );
				throw ex;
			}
		}
	}
}