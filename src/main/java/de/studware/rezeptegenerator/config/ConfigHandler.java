package de.studware.rezeptegenerator.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigHandler {
	private static final Logger LOG = Logger.getLogger(ConfigHandler.class.getName());
	private Properties properties = null;
	
	public ConfigHandler() {
		String configPath = this.getClass().getClassLoader().getResource("generator.properties").getPath();
		properties = new Properties();
		try {
			properties.load(new FileInputStream(configPath));
			LOG.log(Level.INFO, "Properties file loaded successfully");
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Properties file could not be loaded", e);
		}
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
}
