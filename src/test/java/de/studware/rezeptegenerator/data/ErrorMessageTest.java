package de.studware.rezeptegenerator.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ErrorMessageTest {
	
	@Test
	public void checkErrorMessageTitle() {
		assertEquals("URL Fehler", ErrorMessage.URL_NOT_CORRECT.getTitle());
	}
	
	@Test
	public void checkErrorMessageMessage() {
		assertEquals("Diese Seite wird aktuell noch nicht vom Rezepte Generator unterstützt.\nBitte melde dies als Fehler dem Entwickler.", ErrorMessage.NO_PARSER.getMessage());
	}

}
