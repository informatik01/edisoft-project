package ee.edisoft.edi.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;


public class DataSourceConnectionManager implements ConnectionManager {

	private DataSource dataSource;
	
	public DataSourceConnectionManager(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

}
