package com.qqduan.dog.core;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.qqduan.dog.annotation.Column;
import com.qqduan.dog.annotation.Table;
import com.qqduan.dog.db.SqlConnectionPool;

public class StatementProcess {

	public static StatementProcess instance = new StatementProcess();

	public static StatementProcess newinstance() {
		return instance;
	}

	private StatementProcess() {
	}

	Connection getConnection(String source) {
		SqlConnectionPool pool = new SqlConnectionPool(source);
		Connection connection = pool.getConnection();
		try {
			if (!connection.isClosed()) {
				System.out.println("Connect successful!");
			} else {
				System.out.println("Connect failed!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void prepareStatement(Object obj, Class<?> clz) {
		int number = 0;
		Table table = (Table) clz.getAnnotation(Table.class);
		if (table == null) {
			try {
				throw new Exception("error,no Table annotion");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Field[] fields = clz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			if (field.isAnnotationPresent(Column.class)) {
				try {
					if (field.get(obj).getClass().equals(String.class)) {
						StringBuilder builder = new StringBuilder("INSERT INTO ");
						StringBuilder value = new StringBuilder("VALUES");
						builder.append(table.name());
						builder.append("(");
						value.append("(");
						number++;
						for (int l = 0; l < number; l++) {
							if (l != 0) {
								builder.append(",");
								value.append(",");
							}
							builder.append(field.getName());
							value.append("'");
							value.append(field.get(obj));
							value.append("'");
						}
						value.append(")");
						builder.append(")");
						try {
							PreparedStatement statement = null;
							statement = getConnection(Defines.resource)
									.prepareStatement(builder.toString() + value.toString());
							Defines.list.add(statement);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

					if (field.get(obj) instanceof String[]) {
						String[] arr = (String[]) field.get(obj);
						StringBuilder builder = new StringBuilder("INSERT INTO ");
						StringBuilder value = new StringBuilder("VALUES");
						builder.append(table.name());
						builder.append("(");
						builder.append(field.getName());
						builder.append(")");

						for (int j = 0; j < arr.length; j++) {
							if (j != 0) {
								value.append(",");
							}
							value.append("(");
							value.append("'");
							value.append(arr[j]);
							value.append("'");
							value.append(")");
						}
						try {
							PreparedStatement statement = null;
							statement = getConnection(Defines.resource)
									.prepareStatement(builder.toString() + value.toString());
							Defines.list.add(statement);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// public PreparedStatement mulityProcess(Object obj, Class<?> clz) {
	// Table table = (Table) clz.getAnnotation(Table.class);
	// Field[] fields = clz.getDeclaredFields();
	// if (table == null) {
	// try {
	// throw new Exception("error,no Table annotion");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// for (int i = 0; i < fields.length; i++) {
	// Field field = fields[i];
	// field.setAccessible(true);
	// try {
	// if (field.get(obj) instanceof String[]) {
	// PreparedStatement statement = null;
	// try {
	// statement = getConnection(Defines.resource).prepareStatement(
	// "INSERT INTO " + table.name() + "(" + field.getName() + ")" + "VALUES" +
	// "(?)");
	// } catch (SQLException e1) {
	// e1.printStackTrace();
	// }
	// String[] arr = (String[]) field.get(obj);
	// for (int j = 0; j < arr.length; j++) {
	// try {
	// statement.setString(1, arr[j]);
	// statement.addBatch();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// return statement;
	// }
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// }
	// }
	// return null;
	// }

	public PreparedStatement checkTable(Class<?> clz) {
		StringBuilder buildercheck = new StringBuilder("Create Table If Not Exists ");
		Table table = (Table) clz.getAnnotation(Table.class);
		if (table == null) {
			try {
				throw new Exception("error,no Table annotation");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		buildercheck.append(table.name());
		buildercheck.append("(");
		Field[] fields = clz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Column.class)) {
				if (i != 0) {
					buildercheck.append(",");
				}
				buildercheck.append("`");
				buildercheck.append(fields[i].getName());
				buildercheck.append("`");
				buildercheck.append(" ");
				buildercheck.append(DBtype.typemap.get(fields[i].getType()));
			}
		}
		buildercheck.append(")");
		PreparedStatement statement = null;
		try {
			statement = getConnection(Defines.resource).prepareStatement(buildercheck.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}
}
