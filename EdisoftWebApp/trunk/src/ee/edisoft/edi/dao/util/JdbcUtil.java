package ee.edisoft.edi.dao.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class that helps reduce JDBC related boilerplate code.
 *
 */
public class JdbcUtil {
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.err.println("Error closing ResultSet: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				System.err.println("Error closing Statement: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.err.println("Error closing Connection: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public static void close(ResultSet rs, Statement st) {
		closeResultSet(rs);
		closeStatement(st);
	}
	
	public static void close(Statement st, Connection con) {
		closeStatement(st);
		closeConnection(con);
	}
	
	public static void close(ResultSet rs, Statement st, Connection con) {
		closeResultSet(rs);
		closeStatement(st);
		closeConnection(con);
	}
	
	public static void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				System.err.println("Error executing rollback on the connection.");
				e.printStackTrace();
			}
		}
	}
	
	public static void setAutoCommit(Connection con, boolean autoCommit) {
		if (con != null) {
			try {
				con.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				System.err.println("Error setting auto-commit mode.");
				e.printStackTrace();
			}
		}
	}
}
