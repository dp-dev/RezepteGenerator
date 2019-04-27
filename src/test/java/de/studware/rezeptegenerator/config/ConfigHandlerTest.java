package de.studware.rezeptegenerator.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfigHandlerTest {
	
	@Test
	public void loadPropertiesFile() {
		ConfigHandler config = new ConfigHandler();
		assertEquals("Propertie folder.name loading not successful", "Rezepte", config.getProperty("folder.name"));
	}	

}
