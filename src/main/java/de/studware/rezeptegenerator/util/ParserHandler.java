package de.studware.rezeptegenerator.util;

import de.studware.rezeptegenerator.config.ConfigHandler;
import de.studware.rezeptegenerator.data.RecipeData;
import de.studware.rezeptegenerator.parser.AbstractParser;
import de.studware.rezeptegenerator.parser.FoodWithLoveParser;
import de.studware.rezeptegenerator.parser.KuechenfeeParser;
import de.studware.rezeptegenerator.parser.RezepteWeltParser;
import de.studware.rezeptegenerator.pdf.PDFCreator;

public class ParserHandler implements Runnable {
	private EventLog log;
	private RecipeData rezeptdaten;
	private ConfigHandler config;
	
	public ParserHandler(EventLog log, ConfigHandler config) {
		this.log = log;
		this.config = config;
	}
	
	@Override
	public void run() {
		AbstractParser parser = null;
		PDFCreator creator = null;
		String urlpath = ClipboardUrlGetter.getURL(log);
		
		checkIfNullAndExit(urlpath);
		rezeptdaten = new RecipeData(urlpath);
		parser = chooseParser();
		if (parser != null) {
			checkIfNullAndExit(parser.toString());
			creator = new PDFCreator(log, rezeptdaten, config);
			creator.createFolder();
			creator.createFile();
		} else {
			throw new UnsupportedOperationException("No parser was found");
		}
	}
	
	private void checkIfNullAndExit(String text) {
		if (text == null) {
			log.addEvent(this, "Problem encountered: null value!");
			Thread.currentThread().interrupt();
		}
	}
	
	private AbstractParser chooseParser() {
		String urlpath = rezeptdaten.getUrlpath();
		if (urlpath.contains("rezeptwelt.de")) {
			log.addEvent(this, "Found url from Rezeptewelt");
			return new RezepteWeltParser(log, rezeptdaten);
		} else if (urlpath.contains("foodwithlove.de")) {
			log.addEvent(this, "Found url from Foodwithlove");
			return new FoodWithLoveParser(log, rezeptdaten);
		} else if (urlpath.contains("danis-treue-kuechenfee.de")) {
			log.addEvent(this, "Found url from DanisTreueKüchenfee");
			return new KuechenfeeParser(log, rezeptdaten);
		}
		log.addEvent(this, "Error didn't find a correct parser");
		Thread.currentThread().interrupt();
		return null;
	}
	
}
