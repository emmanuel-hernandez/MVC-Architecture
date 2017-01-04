package com.efe13.mvc.dao.api.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import com.efe13.mvc.commons.api.enums.ActiveEnum;
import com.efe13.mvc.commons.api.exception.DAOException;
import com.efe13.mvc.commons.api.util.Utils;
import com.efe13.mvc.dao.api.IDAO;
import com.efe13.mvc.dao.api.impl.util.HibernateUtil;
import com.efe13.mvc.model.api.impl.entity.EntityAPI;
import com.efe13.mvc.model.api.impl.helper.QueryHelper;

public abstract class DAOAPI<T> implements IDAO<EntityAPI> {

	private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private final Class<T> criteriaClass;
	private final static Logger log = Logger.getLogger( DAOAPI.class );
	
	private String columnNameForActiveElement;
	private ActiveEnum activeEnum;
	
	public DAOAPI(String columnNameForActiveElement, ActiveEnum activeEnum) {
		this.columnNameForActiveElement = columnNameForActiveElement;
		this.activeEnum = activeEnum;
		
		//createSessionFactory();
		criteriaClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass() ).getActualTypeArguments()[0];
	}
	
	@Override
	public long getTableCount() {
		try {
			return (long) getCriteria()
								.setProjection( Projections.rowCount() )
								.add( Restrictions.eq( columnNameForActiveElement, activeEnum.getValue() ) ).uniqueResult();
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
			
			//Check for QueryHelper
			if( !Utils.isNull( helper ) ) {
				
				//Check for PaginationAPI
				if( !Utils.isNull( queryHelper.getPaginationAPI() ) ) {
					int firstResult = (queryHelper.getPaginationAPI().getCurrentPage() - 1) * queryHelper.getPaginationAPI().getPageSize();
					criteria.setFirstResult( firstResult );
					criteria.setMaxResults( queryHelper.getPaginationAPI().getPageSize() );
				}
				
				//Check for OrderAPI
				if( !Utils.isNull( queryHelper.getOrderAPI() ) ) {
					String field = queryHelper.getOrderAPI().getField();
					if( !Utils.isNull( field ) ) {
						if( field.contains( "." ) ) {
							String[] parts = field.split( "\\." );
							criteria.createAlias( parts[0], parts[0], JoinType.INNER_JOIN );
						}
						
						criteria.addOrder( (queryHelper.getOrderAPI().isAscending()) ?
											Order.asc( field ) :
											Order.desc( field ) );
					}
				}

				//Check for FilterAPI
				if( !Utils.isNull( queryHelper.getFilterAPI() ) ) {
					if( !Utils.isNull( queryHelper.getFilterAPI().getFilter() ) ) {
						Disjunction or = Restrictions.disjunction();
						for( Entry<String, String> entry : queryHelper.getFilterAPI().getFilter().entrySet() ) {
							if( !Utils.isEmpty( entry.getValue() ) ) {
								or.add( Restrictions.ilike( entry.getKey(), "%" + entry.getValue() + "%" ) );
							}
						}
						criteria.add( or );
					}
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
	
	
	/*
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
	*/
}