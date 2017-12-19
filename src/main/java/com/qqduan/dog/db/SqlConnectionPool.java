package com.qqduan.dog.db;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class SqlConnectionPool {
	DataSource bs = null;

	public SqlConnectionPool(String resource) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(resource), "utf-8"));
			Properties pro = new Properties();
			pro.load(in);
			bs = BasicDataSourceFactory.createDataSource(pro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			return bs.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
