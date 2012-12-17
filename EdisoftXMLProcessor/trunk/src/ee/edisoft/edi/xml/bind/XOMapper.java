package ee.edisoft.edi.xml.bind;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import org.eclipse.persistence.jaxb.JAXBContextFactory;	// to get rid of jaxb.properties
import org.eclipse.persistence.jaxb.JAXBContextProperties;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;

import ee.edisoft.edi.model.Invoice;

/**
 * This class represents a custom XML to Object mapper.
 * 
 * @author Levan Kekelidze
 * @version 0.3 Alpha
 */
public class XOMapper {
	
	private static final Logger logger = Logger.getLogger(XOMapper.class);
	
	private static JAXBContext context;
	private static Unmarshaller unmarshaller;
	private static XMLOutputter outputter = new XMLOutputter();
	
	static {
		try {
			InputStream bindings = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("xml-bindings.xml");
			Map<String, Object> properties = new HashMap<String, Object>(1);
			properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, bindings);
			
			context = JAXBContextFactory.createContext(new Class[] {Invoice.class}, properties);
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			logger.error("Error initializing XOMapper", e);
			throw new XOMappingException("Error initializing XOMapper", e);
		}
	}
	
	/**
	 * Maps XML Documents to {@link Invoice} objects using unmarshalling.
	 * 
	 * @param xmlDocList The map that holds {@link org.jdom2.Documents}
	 * 					 along with last last modified time in milliseconds	
	 * @return The list of {@link Invoice} objects, created using unmarshalling
	 * @throws XOMappingException if cannot map XML to Invoice object
	 */
	public static List<Invoice> mapToObject(Map<Document, Long> xmlDocList) throws XOMappingException {
		List<Invoice> invoices = new ArrayList<Invoice>();
		
		for (Map.Entry<Document, Long> entry: xmlDocList.entrySet()) {
			String xml = null;
			xml = outputter.outputString(entry.getKey());

			try {
				InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
				Invoice invoice = (Invoice) unmarshaller.unmarshal(is);
				invoice.setLastModified(entry.getValue());
				
				invoices.add(invoice);
			} catch (UnsupportedEncodingException | JAXBException e) {
				logger.error("Error mapping xml to Invoice object.", e);
				throw new XOMappingException("Error mapping xml to Invoice object.", e);
			}
		}
		
		return invoices;
	}
}
