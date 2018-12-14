package de.studware.rezeptegenerator.util;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClipboardUrlGetter {
	
	private ClipboardUrlGetter() {
		throw new IllegalStateException("This is a utility class only");
	}
	
	public static String getURL(EventLog log) {
		final Logger logger = Logger.getLogger(ClipboardUrlGetter.class.getName());
		try {
			log.addEvent(log, "Get content from clipboard");
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			return clipboard.getData(DataFlavor.stringFlavor).toString();
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			log.addEvent(log, "Exception occured: " + e.getMessage());
			logger.log(Level.SEVERE, "Exception occured while getting content from the clipboard", e);
			return null;
		}
	}
	
}
