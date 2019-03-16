package de.studware.rezeptegenerator.parser;

import de.studware.rezeptegenerator.data.RecipeData;
import de.studware.rezeptegenerator.util.EventLog;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class AbstractParser {
	private static final Logger logger = Logger.getLogger(AbstractParser.class.getName());
	protected EventLog log = null;
	protected RecipeData rezeptdaten = null;
	protected Document doc = null;
	
	public AbstractParser(EventLog log, RecipeData rezeptdaten, boolean validate) {
		this.log = log;
		this.rezeptdaten = rezeptdaten;
		if (getOnlineContent(validate)) {
			log.addEvent(this, "Parses online document");
			parseDocument();
			log.addEvent(this, "Finished parsing");
		} else {
			Thread.currentThread().interrupt();
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean getOnlineContent(boolean validate) {
		log.addEvent(this, "Tries to get content from the web");
		try {
			doc = Jsoup.connect(rezeptdaten.getUrlpath()).timeout(10000).validateTLSCertificates(validate).get();
			return true;
		} catch (IOException e) {
			log.addEvent(this, "Problem with getting content from web");
			logger.log(Level.SEVERE, "Problem encountered while getting content from the website", e);
		}
		return false;
	}
	
	public abstract void parseDocument();
	
}
