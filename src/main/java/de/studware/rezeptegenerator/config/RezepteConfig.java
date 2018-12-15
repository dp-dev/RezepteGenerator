package de.studware.rezeptegenerator.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RezepteConfig {
	private static final Logger logger = Logger.getLogger(RezepteConfig.class.getName());
	private Properties properties = null;
	
	public RezepteConfig() {
		String configPath = this.getClass().getClassLoader().getResource("rezeptegenerator.properties").getPath();
		properties = new Properties();
		try {
			properties.load(new FileInputStream(configPath));
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Propertiesfile could not be loaded", e);
		}
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
}
