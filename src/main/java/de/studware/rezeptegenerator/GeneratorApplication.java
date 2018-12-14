package de.studware.rezeptegenerator;

import de.studware.rezeptegenerator.display.ScreenEvents;
import de.studware.rezeptegenerator.util.EventLog;

public class GeneratorApplication {
	private EventLog log;
	
	public static void main(String[] args) {
		new GeneratorApplication();
	}
	
	public GeneratorApplication() {
		log = new EventLog();
		log.addEvent(this, "Generator started");
		new ScreenEvents(log);
	}
	
}
