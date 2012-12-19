package ee.edisoft.edi.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * {@link ConnectionManager} implementation that uses {@link DriverManager}
 * for getting a {@link Connection}.
 */
public class DriverManagerConnectionManager implements ConnectionManager {

	private String url;
	private String user;
	private String password;
	
	public DriverManagerConnectionManager(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

}
