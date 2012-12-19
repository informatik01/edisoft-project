package ee.edisoft.edi.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This is a utility class to ease retrieving data from properties files.
 * 
 */
public class PropertiesUtil {
	
	private static final Properties properties = new Properties();
	private String configFileName;

	public PropertiesUtil(String configFileName) throws ConfigurationException {
		
		this.configFileName = configFileName;
		// Getting input stream platform independent way
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFileName);
		if (inputStream == null) {
			throw new ConfigurationException("No properties file with the name \"" +
											configFileName + "\" found on the class path.");
		}
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new ConfigurationException("Could not load properties file \"" + configFileName + "\".");
		}
	}
	
	public String getProperty(String key) {
		
		String property = properties.getProperty(key);
		if (property == null || property.trim().length() == 0) {
			throw new ConfigurationException("Property '" + key + "' is missing in configuration file '" + 
											configFileName + "'." );
		}
		
		return property;
	}
}
