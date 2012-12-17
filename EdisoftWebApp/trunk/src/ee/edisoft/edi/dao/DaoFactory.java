package ee.edisoft.edi.dao;

import ee.edisoft.edi.dao.impl.JdbcDaoFactory;
import ee.edisoft.edi.util.PropertiesUtil;

public abstract class DaoFactory {

	private static final String CONFIG_FILE = "jdbc.properties";
	protected static PropertiesUtil config = new PropertiesUtil(CONFIG_FILE);

	public static DaoFactory getDaoFactory() {
		DaoFactory daoFactory = null;
		String datasourceAccessType = config.getProperty("datasource.access.type");

		if (datasourceAccessType.equals("jdbc")) {
			daoFactory = new JdbcDaoFactory();
		}
		// check for more Data Source Access types (Hibernate, JPA etc) as needed

		return daoFactory;
	}

	public abstract InvoiceDao getInvoiceDao();

	public abstract InvoiceDetailsDao getInvoiceDetailsDao();

	public abstract UserDao getUserDao();

	// add more DAO getters as needed
}
