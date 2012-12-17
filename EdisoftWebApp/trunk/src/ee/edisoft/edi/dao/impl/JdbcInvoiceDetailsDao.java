package ee.edisoft.edi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ee.edisoft.edi.dao.DaoException;
import ee.edisoft.edi.dao.InvoiceDetailsDao;
import ee.edisoft.edi.dao.util.ConnectionManager;
import ee.edisoft.edi.dao.util.JdbcUtil;
import ee.edisoft.edi.model.Invoice;
import ee.edisoft.edi.model.InvoiceDetails;
import ee.edisoft.edi.model.InvoiceDetailsRowMapper;
import ee.edisoft.edi.util.PropertiesUtil;

public class JdbcInvoiceDetailsDao implements InvoiceDetailsDao {

	private static final Logger logger = Logger.getLogger(JdbcInvoiceDetailsDao.class);

	private static final String SQL_QUERIES_RESOURCE = "details_sql.properties";

	private static final String INSERT_SQL;
	private static final String SELECT_SQL;
	private static final String DELETE_SQL;

	private ConnectionManager connectionManager;

	static {
		PropertiesUtil sqlResource = new PropertiesUtil(SQL_QUERIES_RESOURCE);

		INSERT_SQL = sqlResource.getProperty("insert");
		SELECT_SQL = sqlResource.getProperty("select");
		DELETE_SQL = sqlResource.getProperty("delete");
	}

	public JdbcInvoiceDetailsDao(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	@Override
	public void create(List<Invoice> invoices) throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = connectionManager.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(INSERT_SQL);

			int batchSize = 100;
			int count = 0;
			for (Invoice invoice : invoices) {
				for (InvoiceDetails details : invoice.getDetails()) {
					ps.setInt(1, details.getLineNumber());
					ps.setInt(2, details.getSupplierItemCode());
					ps.setString(3, details.getItemDescription());
					ps.setDouble(4, details.getInvoiceQuantity());
					ps.setBigDecimal(5, details.getInvoiceUnitNetPrice());
					ps.setString(6, invoice.getUid());

					ps.addBatch();

					if (++count % batchSize == 0) {
						ps.executeBatch();
					}
				}
			}
			ps.executeBatch();
			con.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(con);
			DaoException.logAndThrow(logger, "Error while creating 'details' table records.", e);
		} finally {
			JdbcUtil.setAutoCommit(con, true);
			JdbcUtil.close(ps, con);
		}
	}

	@Override
	public List<InvoiceDetails> read(String uid) throws DaoException {
		List<InvoiceDetails> invoiceDetailsList = new ArrayList<InvoiceDetails>();
		InvoiceDetailsRowMapper rowMapper = new InvoiceDetailsRowMapper();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = connectionManager.getConnection();
			ps = con.prepareStatement(SELECT_SQL);
			ps.setString(1, uid);
			rs = ps.executeQuery();

			invoiceDetailsList = rowMapper.mapRow(rs);
		} catch (SQLException e) {
			DaoException.logAndThrow(logger,
					"Error while reading 'details' table records.", e);
		} finally {
			JdbcUtil.close(rs, ps, con);
		}

		return invoiceDetailsList;
	}

	@Override
	public void delete(List<String> invoiceUidList) throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = connectionManager.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(DELETE_SQL);

			int batchSize = 100;
			int count = 0;
			for (String uid : invoiceUidList) {
				ps.setString(1, uid);
				ps.addBatch();

				if (++count % batchSize == 0) {
					ps.executeBatch();
				}
			}
			ps.executeBatch();
			con.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(con);
			DaoException.logAndThrow(logger, "Error while deleting 'details' table records.", e);
		} finally {
			JdbcUtil.setAutoCommit(con, true);
			JdbcUtil.close(ps, con);
		}
	}

}
