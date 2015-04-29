package com.photoshare.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.photoshare.db.HibernateUtil;

/**
 * @author Amol
 * 
 *         class containing methods for performing database operation common to
 *         all data object This is a generic class which needs a object type for
 *         performing db operation like insert, delete update etc
 * 
 * @param <T>
 *            : this can be any model object i.e. PhotoMeta, Album, Comment etc
 * @param <ID>
 *            : this will be the id of the object and in most cases this is of
 *            type integer but can change if required
 */
public class CommonDAO<T, ID extends Serializable> {

	private Session getSession() {
		HibernateUtil hibernateUtil = new HibernateUtil();
		SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
		return sessionFactory.openSession();
	}

	public void insert(T obj) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		session.save(obj);
		tx.commit();
	}

	public void update(T obj) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		session.update(obj);
		tx.commit();
	}

	public void delete(T obj) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		session.delete(obj);
		tx.commit();
	}

	public T getObject(Class<T> obj, ID id) {
		T result = (T) getSession().get(obj, id);
		return result;
	}

	public List<T> getObjectByQuery(String query, Map<String,?> nameValueBean ) {
		Query q = getSession().createQuery(query);
		
		for(String columnName : nameValueBean.keySet()){
			q.setParameter(columnName, nameValueBean.get(columnName));
		}
		
		return q.list();
	}

	public Criteria getCriteriaInstance(Class<T> obj) {
		return getSession().createCriteria(obj);
	}

}
