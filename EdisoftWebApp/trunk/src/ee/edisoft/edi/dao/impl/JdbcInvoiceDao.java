package ee.edisoft.edi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import ee.edisoft.edi.dao.DaoException;
import ee.edisoft.edi.dao.DaoFactory;
import ee.edisoft.edi.dao.InvoiceDao;
import ee.edisoft.edi.dao.InvoiceDetailsDao;
import ee.edisoft.edi.dao.util.ConnectionManager;
import ee.edisoft.edi.dao.util.JdbcUtil;
import ee.edisoft.edi.model.Invoice;
import ee.edisoft.edi.model.InvoiceRowMapper;
import ee.edisoft.edi.util.PropertiesUtil;

/**
 * This class represents a JDBC implementation
 * for the {@link InvoiceDAO} interface.
 * 
 */
public class JdbcInvoiceDao implements InvoiceDao {

	private static final Logger logger = Logger.getLogger(JdbcInvoiceDao.class);

	private static final String SQL_QUERIES_RESOURCE = "header_sql.properties";

	private static final String INSERT_SQL;
	private static final String CHECK_UID_SQL;
	private static final String SELECT_UID_MODIFIED_SQL;
	private static final String SELECT_ALL_UID_SQL;
	private static final String SELECT_ALL_SQL;
	private static final String UPDATE_SQL;
	private static final String DELETE_SQL;
	private static final String DELETE_ALL_SQL;

	static {
		PropertiesUtil sqlResource = new PropertiesUtil(SQL_QUERIES_RESOURCE);

		INSERT_SQL = sqlResource.getProperty("insert");
		CHECK_UID_SQL = sqlResource.getProperty("check.uid");
		SELECT_UID_MODIFIED_SQL = sqlResource.getProperty("select.uid.modified");
		SELECT_ALL_UID_SQL = sqlResource.getProperty("select.all.uid");
		SELECT_ALL_SQL = sqlResource.getProperty("select.all");
		UPDATE_SQL = sqlResource.getProperty("update");
		DELETE_SQL = sqlResource.getProperty("delete");
		DELETE_ALL_SQL = sqlResource.getProperty("delete.all");
	}

	private InvoiceDetailsDao invoiceDetailsDao = DaoFactory.getDaoFactory().getInvoiceDetailsDao();
	
	private ConnectionManager connectionManager;
	
	public JdbcInvoiceDao(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	@Override
	public void create(List<Invoice> invoices) throws DaoException {
		List<Invoice> updateInvoiceList = new ArrayList<Invoice>();
		
		Connection con = null;
		PreparedStatement insertHeader = null;
		PreparedStatement checkDuplicate = null;
		ResultSet rs = null;
		try {
			con = connectionManager.getConnection();
			con.setAutoCommit(false);

			insertHeader = con.prepareStatement(INSERT_SQL);
			checkDuplicate = con.prepareStatement(SELECT_UID_MODIFIED_SQL);

			int batchSize = 100;
			int count = 0;
			for (Iterator<Invoice> it = invoices.iterator(); it.hasNext();) {
				Invoice invoice = it.next();
				
				/***** Checking for duplicate records *****/
				checkDuplicate.setString(1, invoice.getUid());
				rs = checkDuplicate.executeQuery();
				if (isRecordPresent(rs, invoice, updateInvoiceList)) {
					it.remove();
					continue;
				}
				/***** end of checking for duplicate records *****/

				insertHeader.setString(1, invoice.getUid());
				insertHeader.setString(2, invoice.getDocumentType());
				insertHeader.setString(3, invoice.getReceiverSystemType());
				insertHeader.setLong(4, invoice.getDocumentNumber());
				insertHeader.setDate(5, new java.sql.Date(invoice.getDocumentDate1().getTime()));
				insertHeader.setDate(6, new java.sql.Date(invoice.getDocumentDate2().getTime()));
				insertHeader.setLong(7, invoice.getSenderILN());
				insertHeader.setLong(8, invoice.getSenderCodeByReceiver());
				insertHeader.setString(9, invoice.getSenderName());
				insertHeader.setString(10, invoice.getSenderAddress());
				insertHeader.setLong(11, invoice.getReceiverILN());
				insertHeader.setLong(12, invoice.getReceiverCodeByReceiver());
				insertHeader.setString(13, invoice.getReceiverName());
				insertHeader.setString(14, invoice.getReceiverAddress());
				insertHeader.setLong(15, invoice.getLastModified());

				insertHeader.addBatch();

				if (++count % batchSize == 0) {
					insertHeader.executeBatch();
				}
			}
			if (count > 0) {
				insertHeader.executeBatch();
				con.commit();
			}
		} catch (SQLException e) {
			JdbcUtil.rollback(con);
			DaoException.logAndThrow(logger, "Error creating records in table 'header'.", e);
		} finally {
			JdbcUtil.setAutoCommit(con, true);
			JdbcUtil.close(rs, checkDuplicate);
			JdbcUtil.close(insertHeader, con);
		}

		invoiceDetailsDao.create(invoices);
		
		if (!updateInvoiceList.isEmpty()) {
			update(updateInvoiceList);
		}
	}

	@Override
	public Invoice read(String uid) throws DaoException {
		Invoice invoice = null;
		InvoiceRowMapper rowMapper = new InvoiceRowMapper();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = connectionManager.getConnection();
			ps = con.prepareStatement(SELECT_ALL_UID_SQL);
			ps.setString(1, uid);
			rs = ps.executeQuery();
			
			invoice = rowMapper.mapRow(rs);	
			invoice.setDetails(invoiceDetailsDao.read(uid));
		} catch (SQLException e) {
			DaoException.logAndThrow(logger, "Error while reading the 'header' table record with uid " + uid, e);
		} finally {
			JdbcUtil.close(rs, ps, con);
		}
		
		return invoice;
	}
	
	@Override
	public List<Invoice> readAll() throws DaoException {
		List<Invoice> invoices = null;
		InvoiceRowMapper rowMapper = new InvoiceRowMapper();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = connectionManager.getConnection();
			ps = con.prepareStatement(SELECT_ALL_SQL);
			rs = ps.executeQuery();
			
			invoices = rowMapper.mapRows(rs);
			for (Invoice invoice: invoices) {
				invoice.setDetails(invoiceDetailsDao.read(invoice.getUid()));
			}
		} catch (SQLException e) {
			DaoException.logAndThrow(logger, "Error while reading the 'header' table records.", e);
		} finally {
			JdbcUtil.close(rs, ps, con);
		}

		return invoices;
	}

	@Override
	public void update(List<Invoice> invoices) throws DaoException {
		List<String> invoiceUidsToDelete = new ArrayList<String>();

		Connection con = null;
		PreparedStatement updateHeader = null;
		PreparedStatement checkUid = null;
		ResultSet rs = null;
		try {
			con = connectionManager.getConnection();
			con.setAutoCommit(false);
			updateHeader = con.prepareStatement(UPDATE_SQL);
			checkUid = con.prepareStatement(CHECK_UID_SQL);

			int batchSize = 100;
			int count = 0;
			for (Iterator<Invoice> it = invoices.iterator(); it.hasNext();) {
				Invoice invoice = it.next();

				/*
				 * Checking if record with the same UID already exists
				 * (when updateInvoiceRecords was invoked after WatchEvent.Kind<> == ENTRY_MODIFY)
				 * If there's no invoice with given UID, then probably UID was changed, so it's not an update.
				 * UID must not be changed inside watched folder. If UID has to be corrected,
				 * either move document outside watched folder, correct UID and move the document back,
				 * or make corrections to UID while XMLProcessor is not working.
				 */
				checkUid.setString(1, invoice.getUid());
				rs = checkUid.executeQuery();
				if (!rs.next()) {
					it.remove();
					continue;
				}

				updateHeader.setString(1, invoice.getDocumentType());
				updateHeader.setString(2, invoice.getReceiverSystemType());
				updateHeader.setLong(3, invoice.getDocumentNumber());
				updateHeader.setDate(4, new java.sql.Date(invoice.getDocumentDate1().getTime()));
				updateHeader.setDate(5, new java.sql.Date(invoice.getDocumentDate2().getTime()));
				updateHeader.setLong(6, invoice.getSenderILN());
				updateHeader.setLong(7, invoice.getSenderCodeByReceiver());
				updateHeader.setString(8, invoice.getSenderName());
				updateHeader.setString(9, invoice.getSenderAddress());
				updateHeader.setLong(10, invoice.getReceiverILN());
				updateHeader.setLong(11, invoice.getReceiverCodeByReceiver());
				updateHeader.setString(12, invoice.getReceiverName());
				updateHeader.setString(13, invoice.getReceiverAddress());
				updateHeader.setLong(14, invoice.getLastModified());
				updateHeader.setString(15, invoice.getUid());

				updateHeader.addBatch();

				invoiceUidsToDelete.add(invoice.getUid());

				if (++count % batchSize == 0) {
					updateHeader.executeBatch();
				}
			}

			if (count > 0) {
				updateHeader.executeBatch();
				con.commit();
			}
		} catch (SQLException e) {
			JdbcUtil.rollback(con);
			DaoException.logAndThrow(logger, "Error while updating the 'header' table records.", e);
		} finally {
			JdbcUtil.setAutoCommit(con, true);
			JdbcUtil.close(rs, checkUid);
			JdbcUtil.close(updateHeader, con);
		}

		if (!invoices.isEmpty()) {
			invoiceDetailsDao.delete(invoiceUidsToDelete);
			invoiceDetailsDao.create(invoices);
		}
	}

	@Override
	public void delete(String uid) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = connectionManager.getConnection();
			ps = con.prepareStatement(DELETE_SQL);
			ps.setString(1, uid);
			ps.executeUpdate();
		} catch (SQLException e) {
			DaoException.logAndThrow(logger, "Error deleting the 'header' table record.", e);
		} finally {
			JdbcUtil.close(ps, con);
		}
	}
	
	@Override
	public void deleteAll() throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = connectionManager.getConnection();
			ps = con.prepareStatement(DELETE_ALL_SQL);
			ps.execute();
		} catch (SQLException e) {
			DaoException.logAndThrow(logger, "Error while deleting the 'header' table records.", e);
		} finally {
			JdbcUtil.close(ps, con);
		}
	}

	/**
	 * Helper method to check if a record with the same UID is already present in the database
	 * 
	 * @param rs ResultSet of the "SELECT_UID_MODIFIED_SQL" query
	 * @param invoice Invoice that needs to be checked for presence in the database
	 * @param updateInvoiceList if record needs to be updated, add current Invoice to this list
	 * @return <code>true<code> if a record with the same UID is already present in the database
	 * @throws SQLException if a database access error occurs
	 * 			or this method is called on a closed result set
	 */
	private boolean isRecordPresent(ResultSet rs, Invoice invoice, List<Invoice> updateInvoiceList)
			throws SQLException {
		if (rs.next()) { // if record with the same UID is already present in the database
			if (invoice.getLastModified() != rs.getLong("last_modified")) {
				// record with the same UID is present but needs an update
				updateInvoiceList.add(invoice);
			}
			return true;
		} else { // if record with the same UID is not present in the database
			return false;
		}
	}
}
