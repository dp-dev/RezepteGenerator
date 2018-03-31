package de.studware.RezepteGenerator.util;

import de.studware.RezepteGenerator.Rezeptdaten;
import de.studware.RezepteGenerator.parser.AbstractParser;
import de.studware.RezepteGenerator.parser.KuechenfeeParser;
import de.studware.RezepteGenerator.parser.FoodWithLoveParser;
import de.studware.RezepteGenerator.parser.RezepteWeltParser;
import de.studware.RezepteGenerator.pdf.PDFCreator;

public class ParserHandler implements Runnable {
	EventLog log = null;
	Rezeptdaten rezeptdaten = null;
	
	public ParserHandler(EventLog log) {
		this.log = log;
	}
	
	@Override
	public void run() {
		AbstractParser parser = null;
		PDFCreator creator = null;
		String urlpath = ClipboardUrlGetter.getURL(log);
		
		checkIfNullAndExit(urlpath);
		rezeptdaten = new Rezeptdaten(urlpath);
		parser = chooseParser();
		checkIfNullAndExit(parser.toString());
		creator = new PDFCreator(log,rezeptdaten);
		creator.createFolder();
		creator.createFile();
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
