package com.photoshare.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.photoshare.utility.Constants;

public class DBConnection {

	public Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();

		try {

			prop.load(new FileInputStream("config.properties"));

			prop.getProperty("database.name");

			Class.forName("com.mysql.jdbc.Driver");

			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(Constants.DB_URL,
					prop.getProperty("database.username"),
					prop.getProperty("database.password"));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
