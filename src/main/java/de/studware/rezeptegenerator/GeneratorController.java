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

import de.studware.rezeptegenerator.config.ConfigHandler;
import de.studware.rezeptegenerator.display.MainScreen;
import de.studware.rezeptegenerator.util.EventLog;
import de.studware.rezeptegenerator.util.ParserHandler;

public class GeneratorController implements ActionListener {
	private static final Logger LOG = Logger.getLogger(GeneratorController.class.getName());
	private EventLog eventLog;
	private MainScreen mainscreen;
	private ConfigHandler config;
	
	public static void main(String[] args) {
		new GeneratorController();
	}
	
	public GeneratorController() {
		this.eventLog = new EventLog();
		eventLog.addEvent(this, "Generator started");
		this.config = new ConfigHandler();
		this.mainscreen = new MainScreen(this);
		this.eventLog.addEvent(this, "Ready for screen actions");
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
			case "RESET_ALL":
				eventLog.addEvent(mainscreen, "Clear Textfield");
				mainscreen.resetTextArea();
				break;
			case "CLOSE":
				eventLog.addEvent(mainscreen, "Close window");
				mainscreen.getFrame().dispatchEvent(new WindowEvent(mainscreen.getFrame(), WindowEvent.WINDOW_CLOSING));
				break;
			case "REPORT_ERROR":
				eventLog.addEvent(mainscreen, "Open Error report");
				try {
					Desktop.getDesktop().browse(new URI(config.getProperty("github.issues")));
				} catch (IOException | URISyntaxException e) {
					LOG.log(Level.SEVERE, "Application could not open github website", e);
				}
				break;
			case "CREATE_PDF":
				eventLog.addEvent(mainscreen, "PDF creation started");
				startExecutionThread();
				mainscreen.showEvents(eventLog.getAllEvents());
				break;
			default:
				LOG.log(Level.INFO, "No Action found for action command: {0}", event.getActionCommand());
				break;
		}
	}
	
	public void addEventToLog(Object obj, String event) {
		eventLog.addEvent(obj, event);
	}
	
	private void startExecutionThread() {
		try {
			Thread thread = new Thread(new ParserHandler(eventLog, config));
			thread.start();
			thread.join();
		} catch (InterruptedException e) {
			LOG.log(Level.SEVERE, "Creation of pdf failed due to a problem with the thread", e);
			Thread.currentThread().interrupt();
		}
	}
	
}
