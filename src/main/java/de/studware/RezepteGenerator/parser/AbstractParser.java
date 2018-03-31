package de.studware.RezepteGenerator.parser;

import de.studware.RezepteGenerator.Rezeptdaten;
import de.studware.RezepteGenerator.util.EventLog;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class AbstractParser {
	EventLog log = null;
	Rezeptdaten rezeptdaten = null;
	Document doc = null;
	
	public AbstractParser(EventLog log, Rezeptdaten rezeptdaten) {
		this.log = log;
		this.rezeptdaten = rezeptdaten;
		if (getOnlineContent()) {
			log.addEvent(this, "Parses online document");
			parseDocument();
			log.addEvent(this, "Finished parsing");
		} else {
			Thread.currentThread().interrupt();
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean getOnlineContent() {
		log.addEvent(this, "Tries to get content from the web");
		try {
			doc = Jsoup.connect(rezeptdaten.getUrlpath()).timeout(10000).validateTLSCertificates(false).get();
			return true;
		} catch (IOException e) {
			log.addEvent(this, "Problem with getting content from web");
			e.printStackTrace();
		}
		return false;
	}
	
	public abstract void parseDocument();
	
}
