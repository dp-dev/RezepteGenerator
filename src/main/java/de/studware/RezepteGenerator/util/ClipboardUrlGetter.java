package de.studware.RezepteGenerator.util;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClipboardUrlGetter {
	
	public static String getURL(EventLog log) {
		Logger logger = Logger.getLogger(ClipboardUrlGetter.class.getName());
		try {
			log.addEvent(log, "Get content from clipboard");
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			String input = clipboard.getData(DataFlavor.stringFlavor).toString();
			return input;
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			log.addEvent(log, "Exception occured: " + e.getMessage());
			logger.log(Level.SEVERE, "Exception occured while getting content from the clipboard", e);
			return null;
		}
	}
	
}
