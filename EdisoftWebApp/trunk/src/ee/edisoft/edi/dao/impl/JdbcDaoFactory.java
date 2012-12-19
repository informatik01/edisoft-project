package ee.edisoft.edi.dao.impl;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ee.edisoft.edi.dao.DaoFactory;
import ee.edisoft.edi.dao.InvoiceDao;
import ee.edisoft.edi.dao.InvoiceDetailsDao;
import ee.edisoft.edi.dao.UserDao;
import ee.edisoft.edi.dao.util.ConnectionManager;
import ee.edisoft.edi.dao.util.DataSourceConnectionManager;
import ee.edisoft.edi.dao.util.DriverManagerConnectionManager;
import ee.edisoft.edi.util.ConfigurationException;

/**
 * {@link DaoFactory} implementation that utilizes JDBC
 *
 */
public class JdbcDaoFactory extends DaoFactory {

	private static final Logger logger = Logger.getLogger(JdbcDaoFactory.class);
	private static ConnectionManager connectionManager;
	
	static {
		String datasourceConnectionMethod = config.getProperty("datasource.connection.method");
		
		if (datasourceConnectionMethod.equalsIgnoreCase("DriverManager")) {
			logger.info("DriverManager will be used for getting connections to database.");
			String url = config.getProperty("jdbc.url");
			String user = config.getProperty("jdbc.username");
			String password = config.getProperty("jdbc.password");
			
			connectionManager = new DriverManagerConnectionManager(url, user, password);
		} else if (datasourceConnectionMethod.equalsIgnoreCase("DataSource")) {
			logger.info("DataSource will be used for getting connections to database.");
			String dataSourceName = config.getProperty("datasource.name");
			DataSource dataSource = null;
			try {
				dataSource = (DataSource) new InitialContext().lookup(dataSourceName);
			} catch (NamingException e) {
				throw new ConfigurationException("DataSource with name " + dataSourceName + " is missing in JNDI");
			}
			
			connectionManager = new DataSourceConnectionManager(dataSource);
		}
	}

	@Override
	public InvoiceDao getInvoiceDao() {
		return new JdbcInvoiceDao(connectionManager);
	}

	@Override
	public InvoiceDetailsDao getInvoiceDetailsDao() {
		return new JdbcInvoiceDetailsDao(connectionManager);
	}

	@Override
	public UserDao getUserDao() {
		return new JdbcUserDao(connectionManager);
	}
}
