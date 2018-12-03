package common;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnUtil {
	private static Context init = null;
	private static DataSource ds = null;
	static {
		try{
			init = (Context) new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/myOracle");
			}catch(Exception e) {
				System.out.println("Connection 실패");
		}
	}
	public static Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
}
