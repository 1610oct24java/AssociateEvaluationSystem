package utilties;

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
		String url = "jdbc:oracle:thin:@associate-evaluation-system.cgbbs6xdwjwh.us-west-2.rds.amazonaws.com:1521:AES";
		String username = "aes_user";
		String password = "r3vatur3!";
		return DriverManager.getConnection(url, username, password);
	}
}
