package de.studware.rezeptegenerator.util;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Logger;

public class ClipboardGetter {
	private static final Logger LOG = Logger.getLogger(Clipboard.class.getSimpleName());

	private ClipboardGetter() {
		throw new IllegalStateException("This is a utility class only");
	}

	public static String getURL() {
		LOG.info("Get content from clipboard");
		try {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			return clipboard.getData(DataFlavor.stringFlavor).toString();
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			LOG.severe("Error while getting content from the clipboard. Exception: " + e);
			return null;
		}
	}

}
