package com.qqduan.dog.core;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.qqduan.dog.annotation.Table;
import com.qqduan.dog.interfac.ItemDeal;
import com.qqduan.dog.interfac.TableDeal;

public class MySqlManager implements ItemDeal, TableDeal {

	@Override
	public boolean addItem(Object obj) {
		Class<? extends Object> clz = obj.getClass();
		if (clz.isAnnotationPresent(Table.class)) {
			StatementProcess newinstance = StatementProcess.newinstance();
			PreparedStatement statementchecktable = newinstance.checkTable(clz);
			newinstance.prepareStatement(obj, clz);
			try {
				if (statementchecktable.execute()) {
					System.out.println("check success");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			for (PreparedStatement p : Defines.list) {
				try {
					p.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		Defines.list.clear();
		return true;
	}

	@Override
	public boolean deleteItem(Object obj) {

		return false;
	}

	@Override
	public boolean editItem(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object queryItem(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteTable(String tableName) {

		return false;
	}

	@Override
	public boolean queryTable(String tableName) {
		StringBuilder builder = new StringBuilder("SHOW TABLES LIKE ");
		builder.append("'%");
		builder.append(tableName);
		builder.append("%'");
		StatementProcess newinstance = StatementProcess.newinstance();
		Connection connection = newinstance.getConnection(Defines.resource);
		try {
			PreparedStatement statement = connection.prepareStatement(builder.toString());
			ResultSet executeQuery = statement.executeQuery();
			if (!executeQuery.next()) {
				System.out.println("No table" + tableName);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(tableName+" exists!");
		return true;
	}

}
