package de.studware.rezeptegenerator.util;

import static org.junit.Assert.assertEquals;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import org.junit.Test;

public class ClipboardGetterTest {
	private static final String URL = "https://testing.com";

	@Test
	public void checkCorrectClipboardContent() {
		StringSelection selection = new StringSelection(URL);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
		assertEquals(URL, ClipboardGetter.getURL());
	}

}
