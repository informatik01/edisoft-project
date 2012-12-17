package ee.edisoft.edi.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 * This class represents a custom database table row mapper.
 * Its main purpose to map rows returned as query result
 * to the appropriate {@link InvoiceDetails} object's fields.
 * 
 * @author Levan Kekelidze
 * @version 0.2 Alpha
 */
public class InvoiceDetailsRowMapper {

	public List<InvoiceDetails> mapRow(ResultSet rs) throws SQLException {

		List<InvoiceDetails> detailsList = new ArrayList<InvoiceDetails>();
		
		while (rs.next()) {
			InvoiceDetails details = new InvoiceDetails();
			
			details.setLineNumber(rs.getInt("line_number"));
			details.setSupplierItemCode(rs.getInt("supplier_item_code"));
			details.setItemDescription(rs.getString("item_description"));
			details.setInvoiceQuantity(rs.getDouble("invoice_quantity"));
			details.setInvoiceUnitNetPrice(rs.getBigDecimal("invoice_unit_net_price"));
			
			detailsList.add(details);
		}

		return detailsList;
	}
}
