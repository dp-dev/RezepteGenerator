package de.studware.rezeptegenerator.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.studware.rezeptegenerator.config.ConfigHandler;

public class ConfigHandlerTest {
	
	@Test
	public void loadPropertiesFile() {
		ConfigHandler config = new ConfigHandler();
		assertEquals("Propertie folder.name loading not successful", "Rezepte", config.getProperty("folder.name"));
	}

}
