package com.revature.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "aesjohn";
		String password = "revature";
		return DriverManager.getConnection(url, username, password);
	}
}
