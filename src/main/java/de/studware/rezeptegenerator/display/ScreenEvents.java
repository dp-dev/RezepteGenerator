package de.studware.rezeptegenerator.display;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.studware.rezeptegenerator.config.RezepteConfig;
import de.studware.rezeptegenerator.util.EventLog;
import de.studware.rezeptegenerator.util.ParserHandler;

public class ScreenEvents implements ActionListener {
	private static final Logger logger = Logger.getLogger(ScreenEvents.class.getName());
	private EventLog log;
	private MainScreen mainscreen;
	private RezepteConfig config;
	
	public ScreenEvents(EventLog log, RezepteConfig config) {
		this.log = log;
		this.config = config;
		this.mainscreen = new MainScreen(this);
		this.log.addEvent(this, "Ready for screen actions");
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
			case "RESET_ALL":
				log.addEvent(mainscreen, "Clear Textfield");
				mainscreen.resetTextArea();
				break;
			case "CLOSE":
				log.addEvent(mainscreen, "Close window");
				mainscreen.getFrame().dispatchEvent(new WindowEvent(mainscreen.getFrame(), WindowEvent.WINDOW_CLOSING));
				break;
			case "REPORT_ERROR":
				log.addEvent(mainscreen, "Open Error report");
				try {
					Desktop.getDesktop().browse(new URI(config.getProperty("github.issues")));
				} catch (IOException | URISyntaxException e) {
					logger.log(Level.SEVERE, "Application could not open github website", e);
				}
				break;
			case "CREATE_PDF":
				log.addEvent(mainscreen, "PDF creation started");
				startExecutionThread();
				mainscreen.showEvents(log.getAllEvents());
				break;
			default:
				logger.log(Level.INFO, "No Action found for action command: {0}", event.getActionCommand());
				break;
		}
	}
	
	public void addEventToLog(Object obj, String event) {
		log.addEvent(obj, event);
	}
	
	private void startExecutionThread() {
		try {
			Thread thread = new Thread(new ParserHandler(log, config));
			thread.start();
			thread.join();
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, "Creation of pdf failed due to a problem with the thread", e);
			Thread.currentThread().interrupt();
		}
	}
	
}
