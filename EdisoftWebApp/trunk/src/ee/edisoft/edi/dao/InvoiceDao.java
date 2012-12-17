package ee.edisoft.edi.dao;

import java.util.List;

import ee.edisoft.edi.model.Invoice;

/**
 * This interface represents a contract for the Data Access Object. 
 * 
 * @author Levan Kekelidze
 * @version 0.2 Alpha
 *
 */
public interface InvoiceDao {
	
	/**
	 * Creates records, extracted from {@link Invoice} objects, 
	 * in both 'header' and 'details' tables.
	 * 
	 * @param docList The list of {@link Invoice} objects that serve as a data source
	 * @throws DaoException to hide implementation details
	 */
	public void create(List<Invoice> invoices) throws DaoException;
	
	/**
	 * Reads record with specified UID from 'header' table
	 * 
	 * @return Invoice object that encapsulates 'header' table record
	 * @throws DaoException to hide implementation details
	 */
	public Invoice read(String uid) throws DaoException;
	
	/**
	 * Reads records from 'header' table
	 * 
	 * @return The list of {@link Invoice} objects that encapsulate 'header' table records
	 * @throws DaoException to hide implementation details
	 */
	public List<Invoice> readAll() throws DaoException;	
	
	/**
	 * Updates records in 'header' table
	 * 
	 * @param docList The list of {@link Invoice} objects that serve as a data source
	 * @throws DaoException to hide implementation details
	 */
	public void update(List<Invoice> invoices) throws DaoException;

	/**
	 * Deletes a record with specified UID from 'heade'r and, as a result of cascading, 'details' tables
	 * @throws DaoException to hide implementation details
	 */
	public void delete(String uid) throws DaoException;
	
	/**
	 * Deletes all records from 'header' and, as a result of cascading, 'details' tables
	 * @throws DaoException to hide implementation details
	 */
	public void deleteAll() throws DaoException;

}
