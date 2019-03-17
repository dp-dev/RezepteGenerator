package de.studware.rezeptegenerator;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.studware.rezeptegenerator.config.BackgroundHandler;
import de.studware.rezeptegenerator.config.ConfigHandler;
import de.studware.rezeptegenerator.display.MainScreen;

public class GeneratorController implements ActionListener {
	private static final Logger LOG = Logger.getLogger(GeneratorController.class.getName());
	private final MainScreen mainscreen;
	private final ConfigHandler config;

	public static void main(String[] args) {
		new GeneratorController();
	}

	public GeneratorController() {
		this.config = new ConfigHandler();
		this.mainscreen = new MainScreen("Rezepte Generator");
		this.mainscreen.setActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "CLOSE":
			LOG.info("Close window");
			mainscreen.getFrame().dispatchEvent(new WindowEvent(mainscreen.getFrame(), WindowEvent.WINDOW_CLOSING));
			break;
		case "REPORT_ERROR":
			LOG.info("Open Error report");
			try {
				Desktop.getDesktop().browse(new URI(config.getProperty("github.issues")));
			} catch (IOException | URISyntaxException e) {
				LOG.log(Level.SEVERE, "Application could not open github website", e);
			}
			break;
		case "CREATE_PDF":
			LOG.info("PDF creation started");
			Thread thread = new Thread(new BackgroundHandler(this, config));
			thread.start();
			break;
		default:
			LOG.severe("No Action found for action command: " + event.getActionCommand());
			break;
		}
	}
	
	public MainScreen getMainscreen() {
		return mainscreen;
	}	

}
