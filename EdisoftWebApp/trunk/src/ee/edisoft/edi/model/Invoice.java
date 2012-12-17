package ee.edisoft.edi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Invoice is a JavaBean class, that models
 * Invoice documents in the business domain.
 * Its purpose is to encapsulate data extracted from XML documents.
 * It is also used as a Object-To-XML binding object and as a DTO.
 * 
 * @author Levan Kekelidze
 * @version 0.3 Alpha
 */

public class Invoice implements Serializable {
	/*
	 * Using EclipseLink project's MOXy as JAXB provider,
	 * because it has a very useful extension: XPath
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private String uid;
	
	private String documentType;
	
	private String receiverSystemType;
	
	private long documentNumber;
	
	private Date documentDate1;
	
	private Date documentDate2;

	private long senderILN;
	
	private long senderCodeByReceiver;
	
	private String senderName;
	
	private String senderAddress;
	
	private long receiverILN;
	
	private long receiverCodeByReceiver;
	
	private String receiverName;
	
	private String receiverAddress;
	
	private List<InvoiceDetails> details;

	private long lastModified;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getReceiverSystemType() {
		return receiverSystemType;
	}

	public void setReceiverSystemType(String receiverSystemType) {
		this.receiverSystemType = receiverSystemType;
	}

	public long getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(long documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Date getDocumentDate1() {
		return documentDate1;
	}

	public void setDocumentDate1(Date documentDate1) {
		this.documentDate1 = documentDate1;
	}

	public Date getDocumentDate2() {
		return documentDate2;
	}

	public void setDocumentDate2(Date documentDate2) {
		this.documentDate2 = documentDate2;
	}

	public long getSenderILN() {
		return senderILN;
	}

	public void setSenderILN(long senderILN) {
		this.senderILN = senderILN;
	}

	public long getSenderCodeByReceiver() {
		return senderCodeByReceiver;
	}

	public void setSenderCodeByReceiver(long senderCodeByReceiver) {
		this.senderCodeByReceiver = senderCodeByReceiver;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public long getReceiverILN() {
		return receiverILN;
	}

	public void setReceiverILN(long receiverILN) {
		this.receiverILN = receiverILN;
	}

	public long getReceiverCodeByReceiver() {
		return receiverCodeByReceiver;
	}

	public void setReceiverCodeByReceiver(long receiverCodeByReceiver) {
		this.receiverCodeByReceiver = receiverCodeByReceiver;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public List<InvoiceDetails> getDetails() {
		return details;
	}

	public void setDetails(List<InvoiceDetails> details) {
		this.details = details;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object invoice) {
		if (this == invoice)
			return true;
		if (invoice == null)
			return false;
		if (!(invoice instanceof Invoice))
			return false;
		
		Invoice other = (Invoice) invoice;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		
		return true;
	}
}
