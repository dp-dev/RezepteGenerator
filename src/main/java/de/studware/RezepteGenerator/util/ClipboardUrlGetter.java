package de.studware.RezepteGenerator.util;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipboardUrlGetter {
	
	public static String getURL(EventLog log) {
		try {
			log.addEvent(log, "Get content from clipboard");
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			String input = clipboard.getData(DataFlavor.stringFlavor).toString();
			return input;
		} catch (HeadlessException | UnsupportedFlavorException | IOException e1) {
			log.addEvent(log, "Exception occured: " + e1.getMessage());
			e1.printStackTrace();
			return null;
		}
	}
	
}
