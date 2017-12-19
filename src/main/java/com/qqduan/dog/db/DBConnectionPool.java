package com.qqduan.dog.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBConnectionPool {
	private ComboPooledDataSource ds = new ComboPooledDataSource();

	private static DBConnectionPool instance;

	public static DBConnectionPool instance(String url, String username, String password, DBDriver driver) {
		if (instance == null) {
			instance = new DBConnectionPool(url, username, password, driver);
		}
		return instance;
	}

	private DBConnectionPool(String url, String username, String password, DBDriver driver) {
		ds.setJdbcUrl(url);
		ds.setUser(username);
		ds.setPassword(password);
		try {
			ds.setDriverClass(driver.getName());
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

		ds.setAcquireIncrement(5);
		ds.setInitialPoolSize(20);
		ds.setMinPoolSize(2);
		ds.setMaxPoolSize(50);
	}

	public Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
