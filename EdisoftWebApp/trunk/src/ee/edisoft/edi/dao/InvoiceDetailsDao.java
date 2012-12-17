package ee.edisoft.edi.dao;

import java.util.List;

import ee.edisoft.edi.model.Invoice;
import ee.edisoft.edi.model.InvoiceDetails;

public interface InvoiceDetailsDao {

	/**
	 * Creates Invoice Details, extracted from Invoice objects in the 'details' table. 
	 * 
	 * @param docList The list of {@link Invoice} objects that serve as a data source
	 * @throws DaoException to hide implementation details
	 */
	public void create(List<Invoice> invoices) throws DaoException;
	
	/**
	 * Reads records with the specified UID from the 'details' table
	 * 
	 * @param documentUID The document UID to identify particular records
	 * @return The list of {@link InvoiceDetails} that encapsulate the 'details' table records
	 * @throws DaoException to hide implementation details
	 */
	public List<InvoiceDetails> read(String uid) throws DaoException;
		
	/**
	 * Deletes records in the 'details' table that relate to a particular Invoice 
	 * 
	 * @param documentUID The document UID to identify particular records
	 * @throws DaoException to hide implementation details
	 */
	public void delete(List<String> invoiceUidList) throws DaoException;
	
}
