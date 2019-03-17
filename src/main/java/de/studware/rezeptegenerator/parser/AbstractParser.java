package de.studware.rezeptegenerator.parser;

import java.net.MalformedURLException;
import java.util.logging.Logger;

import org.jsoup.nodes.Document;

import de.studware.rezeptegenerator.data.RecipeData;
import de.studware.rezeptegenerator.util.WebRequestHandler;

public abstract class AbstractParser {
	private static final Logger LOG = Logger.getLogger(AbstractParser.class.getName());
	protected RecipeData rezeptdaten;
	protected Document doc = null;

	public AbstractParser(RecipeData rezeptdaten) {
		this.rezeptdaten = rezeptdaten;
	}

	public void getOnlineContent() {
		LOG.info("Tries to get content from the web");
		try {
			doc = WebRequestHandler.getOnlineContent(rezeptdaten.getUrlpath());
		} catch (MalformedURLException e) {
			LOG.severe("Error with urlpath: " + e.getMessage());
		}
	}
	
	public String getDocumentContent() {
		return this.doc.text();
	}

	public abstract void parseDocument();

}
