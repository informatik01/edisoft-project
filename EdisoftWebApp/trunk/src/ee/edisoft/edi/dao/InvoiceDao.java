package ee.edisoft.edi.dao;

import java.util.List;

import ee.edisoft.edi.model.Invoice;

/**
 * Interface to abstract access to the data source for the invoice documents.
 * Using Data Access Object pattern.
 */
public interface InvoiceDao {
	
	/**
	 * Creates invoice related records for the specified invoices in the appropriate data source.
	 * 
	 * @param invoices The list of {@link Invoice} objects for which to create records
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public void create(List<Invoice> invoices) throws DaoException;
	
	/**
	 * Reads an invoice related record with specified UID from the appropriate data source.
	 * 
	 * @param uid The UID of the invoice whose data to read
	 * @return Invoice object that encapsulates the appropriate data source record
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public Invoice read(String uid) throws DaoException;
	
	/**
	 * Reads all invoice related records from the appropriate data source.
	 * 
	 * @return List of all {@link Invoice} objects whose records are stored in the data source
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public List<Invoice> readAll() throws DaoException;	
	
	/**
	 * Updates invoice related records in the appropriate data source.
	 * 
	 * @param invoices The list of {@link Invoice} objects whose records need an update
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public void update(List<Invoice> invoices) throws DaoException;

	/**
	 * Deletes an invoice related record with specified UID from the appropriate data source.
	 * 
	 * @param uid The UID of the invoice whose record to delete
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public void delete(String uid) throws DaoException;
	
	/**
	 * Deletes all invoice related records from the appropriate data source.
	 * 
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public void deleteAll() throws DaoException;

}
