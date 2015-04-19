package com.photoshare.utility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.photoshare.db.HibernateUtil;

/**
 * @author Amol
 * 
 *         this class is used for initializing resources when the server starts
 * 
 */
public class PhotoshareStartup extends HttpServlet {
	@Override
	public void init() throws ServletException {
		super.init();

		// Creates db if the config is set to create else will perform the
		// operation accordingly
		// we will be performing this oepration here so it won't take much time
		// when a first db
		// operation is performed.
		HibernateUtil hibernateUtil = new HibernateUtil();
		hibernateUtil.getSessionFactory();

	}
}
