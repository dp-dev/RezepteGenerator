package de.studware.rezeptegenerator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class WebRequestHandler {
	private static final Logger LOG = Logger.getLogger(WebRequestHandler.class.getSimpleName());

	private WebRequestHandler() {
		throw new IllegalStateException("This is a utility class only");
	}

	public static String getOnlineContent(String urlpath) throws MalformedURLException {
		LOG.info("Tries to get content from the web");
		StringBuilder builder = new StringBuilder();

		URL url = new URL(urlpath);

		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
			String line;
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();
		} catch (IOException e) {
			LOG.severe("Exception while getting data from website " + e.getMessage());
			return null;
		}
	}

}
