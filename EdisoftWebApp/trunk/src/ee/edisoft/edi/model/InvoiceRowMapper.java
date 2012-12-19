package ee.edisoft.edi.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 * This class represents a custom database table row mapper.
 * Its purpose is to map rows returned as a result of a SQL query
 * to the appropriate {@link Invoice} object's fields.
 * 
 */
public class InvoiceRowMapper {

	public Invoice mapRow(ResultSet rs) throws SQLException {
		Invoice invoice = new Invoice();
		
		if (rs.next()) {
			invoice.setUid(rs.getString("uid"));
			invoice.setDocumentType(rs.getString("document_type"));
			invoice.setReceiverSystemType(rs.getString("receiver_system_type"));
			invoice.setDocumentNumber(rs.getLong("document_number"));
			invoice.setDocumentDate1(rs.getDate("document_date1"));
			invoice.setDocumentDate2(rs.getDate("document_date2"));
			invoice.setSenderILN(rs.getLong("sender_iln"));
			invoice.setSenderCodeByReceiver(rs.getLong("sender_code_by_receiver"));
			invoice.setSenderName(rs.getString("sender_name"));
			invoice.setSenderAddress(rs.getString("sender_address"));
			invoice.setReceiverILN(rs.getLong("receiver_iln"));
			invoice.setReceiverCodeByReceiver(rs.getLong("receiver_code_by_receiver"));
			invoice.setReceiverName(rs.getString("receiver_name"));
			invoice.setReceiverAddress(rs.getString("receiver_address"));
			invoice.setLastModified(rs.getLong("last_modified"));
		}
		
		return invoice;
	}
	
	public List<Invoice> mapRows(ResultSet rs) throws SQLException {
		
		List<Invoice> invoiceList = new ArrayList<Invoice>();
		
		while (rs.next()) {
			Invoice invoice = new Invoice();
			
			invoice.setUid(rs.getString("uid"));
			invoice.setDocumentType(rs.getString("document_type"));
			invoice.setReceiverSystemType(rs.getString("receiver_system_type"));
			invoice.setDocumentNumber(rs.getLong("document_number"));
			invoice.setDocumentDate1(rs.getDate("document_date1"));
			invoice.setDocumentDate2(rs.getDate("document_date2"));
			invoice.setSenderILN(rs.getLong("sender_iln"));
			invoice.setSenderCodeByReceiver(rs.getLong("sender_code_by_receiver"));
			invoice.setSenderName(rs.getString("sender_name"));
			invoice.setSenderAddress(rs.getString("sender_address"));
			invoice.setReceiverILN(rs.getLong("receiver_iln"));
			invoice.setReceiverCodeByReceiver(rs.getLong("receiver_code_by_receiver"));
			invoice.setReceiverName(rs.getString("receiver_name"));
			invoice.setReceiverAddress(rs.getString("receiver_address"));
			invoice.setLastModified(rs.getLong("last_modified"));
			
			invoiceList.add(invoice);
		}
		
		return invoiceList;
	}
}
