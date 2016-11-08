package br.ufrj.dcc.thacilima.newsandyou.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author thacilima
 *
 */
public class DBUtil {
	public Connection conn = null;
	 
	public DBUtil() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String server = "localhost";
		//String server = "54.87.165.196";
		String url = "jdbc:mysql://"+server+":3306/newsandyou_db";
		conn = DriverManager.getConnection(url, "root", "12345");
		System.out.println("connection built");
	}
 
	/**
	 * Execute a sql statement that return a ResultSet
	 * @param sql
	 * @return The ResultSet for the query
	 * @throws SQLException
	 */
	public ResultSet runSql(String sql) throws SQLException {
		Statement sta = conn.createStatement();
		return sta.executeQuery(sql);
	}
 
	/**
	 * @param sql
	 * @return True if the first result is a ResultSet object; False if it is an update count or there are no results
	 * @throws SQLException
	 */
	public boolean runSql2(String sql) throws SQLException {
		Statement sta = conn.createStatement();
		return sta.execute(sql);
	}
 
	/**
	 * Execute a insert sql
	 * @param sql
	 * @return The ResultSet object of the inserted object
	 * @throws SQLException
	 */
	public ResultSet runInsertSql(String sql) throws SQLException {
		Statement sta = conn.createStatement();
		sta.execute(sql, Statement.RETURN_GENERATED_KEYS);
		return sta.getGeneratedKeys();
	}
	
	
	/**
	 * Works well for batch inserts, updates and deletes
	 * @param sql
	 * @return An integer with the number of the affected rows
	 * @throws SQLException
	 */
	public int runInsertUpdateDeleteSql(String sql) throws SQLException {
		Statement sta = conn.createStatement();
		return sta.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
	}
	
	@Override
	protected void finalize() throws Throwable {
		if (conn != null || !conn.isClosed()) {
			conn.close();
		}
	}
}

