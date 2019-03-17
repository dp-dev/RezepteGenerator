package de.studware.rezeptegenerator.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;

import de.studware.rezeptegenerator.config.ConfigHandler;
import de.studware.rezeptegenerator.config.QueueStatus;
import de.studware.rezeptegenerator.data.ErrorMessage;
import de.studware.rezeptegenerator.data.RecipeData;
import de.studware.rezeptegenerator.parser.AbstractParser;
import de.studware.rezeptegenerator.parser.FoodWithLoveParser;
import de.studware.rezeptegenerator.parser.KuechenfeeParser;
import de.studware.rezeptegenerator.parser.RezeptWeltParser;
import de.studware.rezeptegenerator.pdf.PDFCreator;

public class ParserHandler implements Runnable {
	private static final Logger LOG = Logger.getLogger(ParserHandler.class.getSimpleName());
	private final ArrayBlockingQueue<QueueStatus> queue;
	private final ConfigHandler config;
	private RecipeData rezeptdaten;

	public ParserHandler(ArrayBlockingQueue<QueueStatus> queue, ConfigHandler config) {
		this.queue = queue;
		this.config = config;
	}

	@Override
	public void run() {
		try {
			String urlpath = ClipboardGetter.getURL();
			checkIfNullAndExit(urlpath, ErrorMessage.URL_NOT_CORRECT);
			queue.put(new QueueStatus(20));
			rezeptdaten = new RecipeData(urlpath);
			AbstractParser parser = chooseParser();
			if (parser != null) {
				queue.put(new QueueStatus(40));
				parser.getOnlineContent();
				checkIfNullAndExit(parser.getDocumentContent(), ErrorMessage.DOC_CONTENT_WRONG);
				parser.parseDocument();
				queue.put(new QueueStatus(60));
				PDFCreator creator = new PDFCreator(rezeptdaten, config);
				creator.createFolder();
				queue.put(new QueueStatus(80));
				boolean success = creator.createFile();
				if (success) {
					queue.put(new QueueStatus(100));
				} else {
					queue.put(new QueueStatus(-1, ErrorMessage.PDF_FAILURE.getMessage(),
							ErrorMessage.PDF_FAILURE.getTitle()));
				}
			} else {
				queue.put(new QueueStatus(-1, ErrorMessage.NO_PARSER.getMessage(), ErrorMessage.NO_PARSER.getTitle()));
				throw new UnsupportedOperationException("No parser was found");
			}
		} catch (UnsupportedOperationException | InterruptedException e) {
			LOG.severe("Thread was interrupted " + e);
			Thread.currentThread().interrupt();
		}
	}

	private void checkIfNullAndExit(String text, ErrorMessage error) throws InterruptedException {
		if (text == null) {
			LOG.severe("Problem encountered: null value!");
			queue.put(new QueueStatus(-1, error.getMessage(), error.getTitle()));
			throw new InterruptedException("Problem encountered: null value!");
		}
	}

	private AbstractParser chooseParser() {
		String urlpath = rezeptdaten.getUrlpath();
		if (urlpath.contains("rezeptwelt.de")) {
			LOG.info("Found url from Rezeptwelt");
			return new RezeptWeltParser(rezeptdaten);
		} else if (urlpath.contains("foodwithlove.de")) {
			LOG.info("Found url from Foodwithlove");
			return new FoodWithLoveParser(rezeptdaten);
		} else if (urlpath.contains("danis-treue-kuechenfee.de")) {
			LOG.info("Found url from DanisTreueKüchenfee");
			return new KuechenfeeParser(rezeptdaten);
		}
		LOG.severe("Error didn't find a correct parser");
		return null;
	}

}
