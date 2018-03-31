package de.studware.RezepteGenerator.util;

import de.studware.RezepteGenerator.Rezeptdaten;
import de.studware.RezepteGenerator.parser.AbstractParser;
import de.studware.RezepteGenerator.parser.KuechenfeeParser;
import de.studware.RezepteGenerator.parser.RezepteFoodWithLove;
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
		PDFCreator creater = null;
		String urlpath = ClipboardUrlGetter.getURL(log);
		if (urlpath == null) {
			Thread.currentThread().interrupt();
		} else {
			rezeptdaten = new Rezeptdaten(urlpath);
		}
		parser = chooseParser();
	}
	
	private AbstractParser chooseParser() {
		String urlpath = rezeptdaten.getUrlpath();
		if (urlpath.contains("rezeptwelt.de")) {
			log.addEvent(this, "Found url from Rezeptewelt");
			return new RezepteWeltParser(log, rezeptdaten);
		} else if (urlpath.contains("foodwithlove.de")) {
			log.addEvent(this, "Found url from Foodwithlove");
			return new RezepteFoodWithLove(log, rezeptdaten);
		} else if (urlpath.contains("danis-treue-kuechenfee.de")) {
			log.addEvent(this, "Found url from DanisTreueKüchenfee");
			return new KuechenfeeParser(log, rezeptdaten);
		}
		log.addEvent(this, "Error didn't find a correct parser");
		Thread.currentThread().interrupt();
		return null;
	}
	
}
