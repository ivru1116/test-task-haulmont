package com.haulmont.testtask.connection;

import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.entity.Pacient;
import com.haulmont.testtask.entity.Recipe;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateFactory {
    private static SessionFactory sessionFactory;

    /**
     * Constructs a new Singleton SessionFactory
     * @return
     * @throws HibernateException
     */
    public static SessionFactory buildSessionFactory() throws HibernateException {
        if (sessionFactory != null) {
            closeFactory();
        }
        return configureSessionFactory();
    }

    /**
     * Builds a SessionFactory, if it hasn't been already.
     */
    public static SessionFactory buildIfNeeded() {
        if (sessionFactory != null) {
            return sessionFactory;
        }
        try {
            return configureSessionFactory();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    public static Session openSession() throws HibernateException {
        buildIfNeeded();
        return sessionFactory.openSession();
    }

    public static void closeFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (HibernateException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    public static void close(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    public static void rollback(Transaction tx) {
        try {
            if (tx != null) {
                tx.rollback();
            }
        } catch (HibernateException ignored) {
            ignored.printStackTrace();
        }
    }
    /**
     *
     * @return
     * @throws HibernateException
     */
    private static SessionFactory configureSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(Doctor.class);
        configuration.addAnnotatedClass(Pacient.class);
        configuration.addAnnotatedClass(Recipe.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

}
