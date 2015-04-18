package com.photoshare.db;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	public Session getSession() {
		Configuration cfg = new Configuration();
		cfg.configure();

		return cfg.buildSessionFactory().getCurrentSession();
	}
	
}
