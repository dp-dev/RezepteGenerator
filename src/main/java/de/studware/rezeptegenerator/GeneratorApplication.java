package de.studware.rezeptegenerator;

import de.studware.rezeptegenerator.config.RezepteConfig;
import de.studware.rezeptegenerator.display.ScreenEvents;
import de.studware.rezeptegenerator.util.EventLog;

public class GeneratorApplication {

	
	public static void main(String[] args) {
		new GeneratorApplication();
	}
	
	public GeneratorApplication() {
		EventLog log = new EventLog();
		log.addEvent(this, "Generator started");
		RezepteConfig config = new RezepteConfig();
		new ScreenEvents(log, config);
	}
	
}
