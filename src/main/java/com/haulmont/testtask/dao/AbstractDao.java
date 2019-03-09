package com.haulmont.testtask.dao;

import com.haulmont.testtask.connection.HibernateFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;

public class AbstractDao<EntityType, Id extends Serializable> {
    private Session session;
    private Transaction tx;

    public AbstractDao() {
        HibernateFactory.buildIfNeeded();
    }

    public void save(EntityType entity) {
        try {
            startOperation();
            session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
    }

    public void delete(EntityType entity) {
        try {
            startOperation();
            session.delete(entity);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
    }

    public EntityType find(Class<EntityType> clazz, Id id) {
        EntityType result = null;
        try {
            startOperation();
            result = session.get(clazz, id);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return result;
    }

    public List findAll(Class<EntityType> clazz) {
        List<EntityType> result = null;
        try {
            startOperation();
            Query query = session.createQuery("from " + clazz.getName());
            result = query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return result;
    }
    public EntityType update(EntityType entity) {
        try {
            startOperation();
            session.update(entity);
            tx.commit();
        } catch (HibernateException ex) {
            handleException(ex);
        } finally {
            HibernateFactory.close(session);
        }
        return entity;

    }

    private void handleException(HibernateException e) {
        HibernateFactory.rollback(tx);
        throw new RuntimeException(e);
    }

    private void startOperation() throws HibernateException {
        session = HibernateFactory.openSession();
        tx = session.beginTransaction();
    }
}
