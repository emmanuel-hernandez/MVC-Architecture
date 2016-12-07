package com.efe13.mvc.dao.api.impl.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	private static HibernateUtil instance = null;
	private static SessionFactory sessionFactory;

	private HibernateUtil(){
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
	        sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch( Exception ex ) {
			StandardServiceRegistryBuilder.destroy( registry );
			System.out.println( "HibernateUtil: " + ex.getMessage() );
			ex.printStackTrace();
		}
	}

	public static HibernateUtil getInstance(){
        if( instance == null ) {
            instance  = new HibernateUtil();
        }
        return instance;
	}

	public static SessionFactory getSessionFactory() {
        getInstance();
		return HibernateUtil.sessionFactory;
	}
}