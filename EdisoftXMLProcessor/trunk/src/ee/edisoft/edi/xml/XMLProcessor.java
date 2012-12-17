package ee.edisoft.edi.xml;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.transform.XSLTransformer;

import ee.edisoft.edi.dao.DaoFactory;
import ee.edisoft.edi.dao.InvoiceDao;
import ee.edisoft.edi.model.Invoice;
import ee.edisoft.edi.util.PropertiesUtil;
import ee.edisoft.edi.xml.bind.XOMapper;

/**
 * This class represents custom XML processor, whose main purpose is to find XML
 * files in the predefined directory, transform them using XSLT file, populate
 * database with data, that was extracted from translated XML documents, and
 * watch for the creation and modification of XML documents to update database
 * records.
 * 
 * @author Levan Kekelidze
 * @version 0.3 Alpha
 */
public class XMLProcessor {

	private static final Logger logger = Logger.getLogger(XMLProcessor.class);
	private static final String CONFIG_FILE = "xml_processor.properties";

	private SAXBuilder builder = new SAXBuilder();
	private InvoiceDao invoiceDao = DaoFactory.getDaoFactory().getInvoiceDao();
	private Path xmlDirPath;
	private Path xsltDirPath;
	private WatchService watcher;
	private boolean processingEvents;
	
	/**
	 * Initializes XMLProcessor with data it needs for its work
	 */
	private void init() {
		PropertiesUtil propertiesUtil = new PropertiesUtil(CONFIG_FILE);

		xmlDirPath = Paths.get(propertiesUtil.getProperty("xml.file.dir"));
		xsltDirPath = Paths.get(propertiesUtil.getProperty("xslt.file.dir"));
		logger.info("XML Processor is initialized successfully.");
	}

	public XMLProcessor() {
		init();
	}

	/**
	 * Does initial processing of XML documents
	 */
	public void process() {
		// Getting XSLT file
		Path xsltFilePath = fetchXsltFilePath(xsltDirPath);
		if (xsltFilePath == null) {
			System.out.format("No xslt files found in directory %1s. Exiting ...", xsltDirPath);
			return;
		}

		// Getting the list of all XML files in specified directory
		List<Path> xmlFilePathList = buildXmlPathList(xmlDirPath);
		if (!xmlFilePathList.isEmpty()) {
			// Getting list of transformed XML documents
			Map<Document, Long> xmlDocList = buildXmlDocList(xmlFilePathList, xsltFilePath);

			// Mapping transformed XML to objects
			List<Invoice> invoices = XOMapper.mapToObject(xmlDocList);

			// Inserting data into database
			invoiceDao.create(invoices);
		}

		processEvents(xsltFilePath);
	}

	/**
	 * Acts as a directory watcher
	 * 
	 * @param xsltFilePath Path to XSLT file
	 */
	public void processEvents(Path xsltFilePath) throws XMLProcessingException {
		try {
			watcher = FileSystems.getDefault().newWatchService();
			xmlDirPath.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);
		} catch (IOException e) {
			logger.error("Error registering watch service.", e);
			throw new XMLProcessingException(e);
		}

		System.out.format("Monitoring directory \"%1s\" for XML documents.%n", xmlDirPath);
		System.out.println("(To exit press CTRL+C)\n");
		System.out.println("Generated events:\n-----------------");

		logger.info("Started processing events.");

		List<Path> createdFiles = new ArrayList<Path>();
		List<Path> modifiedFiles = new ArrayList<Path>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		WatchKey key = null;
		
		processingEvents = true;
		
		/***** Start of main event processing loop *****/
		while (true) {
			try {
				key = watcher.take();
			} catch (InterruptedException e) {
				logger.error("InterruptedException in method \"processEvents\"", e);
				break;
			} catch (ClosedWatchServiceException e) {
				logger.info("WatchService was closed.");
				break;
			}

			// retrieving all pending events for this watch key
			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();

				if (kind == OVERFLOW) {
					continue;
				}

				Path relativePath = (Path) event.context();
				Path fullPath = xmlDirPath.resolve(relativePath);

				if (kind == ENTRY_CREATE) {
					if (Files.isRegularFile(fullPath, NOFOLLOW_LINKS)) {
						createdFiles.add(fullPath);
						System.out.println(sdf.format(Calendar.getInstance().getTime()) + " CREATED: " + fullPath);
					}
				}
				/*
				 * NB! From Java SE 7 API specification: when a file in a
				 * watched directory is modified then it may result in a single
				 * ENTRY_MODIFY event in some implementations but several events
				 * in other implementations.
				 */
				if (kind == ENTRY_MODIFY) {
					if (Files.isRegularFile(fullPath, NOFOLLOW_LINKS)) {
						if (!createdFiles.contains(fullPath)) {
							modifiedFiles.add(fullPath);
							System.out.println(sdf.format(Calendar.getInstance().getTime()) + " MODIFIED: " + fullPath);
						}
					}
				}
			}

			Map<Document, Long> xmlDocCreateList = buildXmlDocList(createdFiles, xsltFilePath);
			if (!xmlDocCreateList.isEmpty()) {
				List<Invoice> invoiceCreateList = XOMapper.mapToObject(xmlDocCreateList);
				if (!invoiceCreateList.isEmpty()) {
					invoiceDao.create(invoiceCreateList);
				}
			}

			Map<Document, Long> xmlDocModifyList = buildXmlDocList(modifiedFiles, xsltFilePath);
			if (!xmlDocModifyList.isEmpty()) {
				List<Invoice> invoiceModifyList = XOMapper.mapToObject(xmlDocModifyList);
				if (!invoiceModifyList.isEmpty()) {
					invoiceDao.update(invoiceModifyList);
				}
			}

			createdFiles.clear();
			modifiedFiles.clear();

			boolean valid = key.reset();
			if (!valid) {
				try {
					watcher.close();
				} catch (IOException e) {
					logger.error("Error closing Watch service.", e);
				}
				break;
			}
		}
		/***** End of main event processing loop *****/

		processingEvents = false;
		logger.info("Finished processing events.");
	}

	/**
	 * Stops processing events
	 */
	public void stopProcessingEvents() {
		if (watcher != null) {
			try {
				watcher.close();
			} catch (IOException e) {
				logger.error("Error closing WatchService.", e);
			}
		}
	}

	/**
	 * Builds a list of XML files, located in predefined directory
	 * 
	 * @param xmlDirPath The directory where to look for XML files
	 * @return The list of found XML file paths
	 */
	public List<Path> buildXmlPathList(Path xmlDirPath) {
		List<Path> xmlFilePathList = new ArrayList<Path>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(xmlDirPath, "*.[xX][mM][lL]")) {
			for (Path entry : stream) {
				xmlFilePathList.add(entry);
			}
		} catch (NoSuchFileException | NotDirectoryException e) {
			System.err.format("No such directory: %s%n", xmlDirPath);
			System.err.format("Check paths in the %s file%n", CONFIG_FILE);
			if (logger.isEnabledFor(Level.ERROR)) {
				logger.error("No such directory: " + xmlDirPath, e);
			}
			System.exit(1);
		} catch (IOException | DirectoryIteratorException e) {
			System.err.println("Error collecting XML file names. Aborting ...");
			logger.error("Error collecting XML file names.", e);
			System.exit(1);
		}

		return xmlFilePathList;
	}

	/**
	 * Fetches a path to a XSLT file. Should be one XSLT file.
	 * 
	 * @param xsltDirPath The directory where to look for XSLT file
	 * @return The path to the found XSLT file or null if nothing found
	 */
	public Path fetchXsltFilePath(Path xsltDirPath) {
		File xsltDir = xsltDirPath.toFile();
		File[] files = xsltDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return (name.toLowerCase().endsWith(".xsl") || name.toLowerCase().endsWith(".xslt"));
			}
		});

		if (files.length == 0) {
			return null;
		}

		return files[0].toPath(); // should be the only XSLT according to the requirements
	}

	/**
	 * Builds a map, containing org.jdom2.Document objects (representing
	 * transformed XML documents) and last modified time in milliseconds for
	 * each original XML document.
	 * 
	 * @param xmlFilePathList The list of XML files' paths
	 * @param xsltFilePath The path to XSLT file that will be used for transformation
	 * @return The map with transformed XML documents along with their last modified time
	 */
	public Map<Document, Long> buildXmlDocList(List<Path> xmlFilePathList, Path xsltFilePath) {
		Map<Document, Long> xmlDocList = new HashMap<Document, Long>();
		try {
			XSLTransformer transformer = new XSLTransformer(xsltFilePath.toString());
			for (Path xmlFile : xmlFilePathList) {
				if (!xmlFile.toString().toLowerCase().endsWith(".xml")) {
					continue;
				}
				long lastModified = Files.getLastModifiedTime(xmlFile).toMillis();

				Document initialDoc = builder.build(xmlFile.toString());
				Document transformedDoc = transformer.transform(initialDoc);

				xmlDocList.put(transformedDoc, lastModified);
			}
		} catch (IOException | JDOMException e) {
			System.err.println("Error processing XML files: " + e.getMessage());
			logger.error("Error processing XML files.", e);
			System.exit(1);
		}

		return xmlDocList;
	}
	
	/**
	 * Check if XMLProcessor is processing events
	 */
	public boolean isProcessingEvents() {
		return processingEvents;
	}
}
