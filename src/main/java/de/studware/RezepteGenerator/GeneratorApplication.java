package de.studware.RezepteGenerator;

import de.studware.RezepteGenerator.display.ScreenEvents;
import de.studware.RezepteGenerator.util.EventLog;

public class GeneratorApplication {
	EventLog log;
	
	public static void main(String[] args) {
		new GeneratorApplication();
	}
	
	public GeneratorApplication() {
		log = new EventLog();
		log.addEvent(this, "Generator started");
		new ScreenEvents(log);
	}
	
}
