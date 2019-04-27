package de.studware.rezeptegenerator.parser;

import java.net.MalformedURLException;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Whitelist;

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
			String content = WebRequestHandler.getOnlineContent(rezeptdaten.getUrlpath());
			cleanupContentAndProduceDoc(content);
		} catch (MalformedURLException e) {
			LOG.severe("Error with urlpath: " + e.getMessage());
		}
	}

	private void cleanupContentAndProduceDoc(String content) {
		content = Jsoup.clean(content, getWhitelistForWebsite());
		content = Parser.unescapeEntities(content, false);
		doc = Jsoup.parse(content);
		for (Element element : doc.select("*")) {
			if (element.childNodes().isEmpty() && element.isBlock() && element.text().replaceAll("\\s", "").isEmpty()) {
				element.remove();
			}
		}
		LOG.info("Cleanup of Document done");
	}

	public String getDocumentContent() {
		return this.doc.text();
	}

	public abstract void parseDocument();

	public abstract Whitelist getWhitelistForWebsite();

}
