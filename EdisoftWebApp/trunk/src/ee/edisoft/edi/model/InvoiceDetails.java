package ee.edisoft.edi.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * InvoiceDetails is a JavaBean class, that models
 * invoice document's details in the business domain.
 * 
 */
public class InvoiceDetails implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int lineNumber;
	
	private int supplierItemCode;
	
	private String itemDescription;
	
	private double invoiceQuantity;
	
	private BigDecimal invoiceUnitNetPrice;
	
	public String getItemDescription() {
		return itemDescription;
	}
	
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	
	public double getInvoiceQuantity() {
		return invoiceQuantity;
	}
	
	public void setInvoiceQuantity(double invoiceQuantity) {
		this.invoiceQuantity = invoiceQuantity;
	}
	
	public BigDecimal getInvoiceUnitNetPrice() {
		return invoiceUnitNetPrice;
	}
	
	public void setInvoiceUnitNetPrice(BigDecimal invoiceUnitNetPrice) {
		this.invoiceUnitNetPrice = invoiceUnitNetPrice;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getSupplierItemCode() {
		return supplierItemCode;
	}

	public void setSupplierItemCode(int supplierItemCode) {
		this.supplierItemCode = supplierItemCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(invoiceQuantity);
		
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((invoiceUnitNetPrice == null) ? 0 : invoiceUnitNetPrice.hashCode());
		result = prime * result + ((itemDescription == null) ? 0 : itemDescription.hashCode());
		result = prime * result + lineNumber;
		result = prime * result + supplierItemCode;
		
		return result;
	}

	@Override
	public boolean equals(Object invoiceDetails) {
		if (this == invoiceDetails)
			return true;
		if (invoiceDetails == null)
			return false;
		if (!(invoiceDetails instanceof InvoiceDetails))
			return false;
		
		InvoiceDetails other = (InvoiceDetails) invoiceDetails;
		if (Double.doubleToLongBits(invoiceQuantity) != Double.doubleToLongBits(other.invoiceQuantity))
			return false;
		
		if (invoiceUnitNetPrice == null) {
			if (other.invoiceUnitNetPrice != null)
				return false;
		} else if (!invoiceUnitNetPrice.equals(other.invoiceUnitNetPrice))
			return false;
		
		if (itemDescription == null) {
			if (other.itemDescription != null)
				return false;
		} else if (!itemDescription.equals(other.itemDescription))
			return false;
		
		if (lineNumber != other.lineNumber)
			return false;
		
		if (supplierItemCode != other.supplierItemCode)
			return false;
		
		return true;
	}
}