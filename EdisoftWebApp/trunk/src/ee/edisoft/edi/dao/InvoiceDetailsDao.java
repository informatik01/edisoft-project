package ee.edisoft.edi.dao;

import java.util.List;

import ee.edisoft.edi.model.Invoice;
import ee.edisoft.edi.model.InvoiceDetails;

/**
 * Interface to abstract access to the data source for the invoice details.
 * Using Data Access Object pattern.
 */
public interface InvoiceDetailsDao {

	/**
	 * Creates invoice details related records for the specified invoices in the appropriate data source. 
	 * 
	 * @param docList The list of {@link Invoice} objects for which to create details records
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public void create(List<Invoice> invoices) throws DaoException;
	
	/**
	 * Reads invoice details related records with the specified UID from the the related data source.
	 * 
	 * @param uid The UID of the invoice whose details to read
	 * @return The list of {@link InvoiceDetails} objects whose records are stored in the data source
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public List<InvoiceDetails> read(String uid) throws DaoException;
		
	/**
	 * Deletes details records that relate to particular invoices
	 * 
	 * @param invoiceUids UIDs of the invoices whose details to delete
	 * @throws DaoException if something goes wrong at Data Access layer
	 */
	public void delete(List<String> invoiceUids) throws DaoException;
	
}
