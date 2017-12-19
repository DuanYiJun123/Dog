package com.qqduan.dog.db;

public enum DBDriver {
	ORACLE_DRIVER("oracle.jdbc.driver.OracleDriver"), MYSQL_DRIVER("com.mysql.jdbc.Driver");

	private String name;

	private DBDriver(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
