package de.studware.RezepteGenerator.display;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import de.studware.RezepteGenerator.util.EventLog;
import de.studware.RezepteGenerator.util.ParserHandler;

public class ScreenEvents implements ActionListener {
	private EventLog log;
	private MainScreen mainscreen;
	
	public ScreenEvents(EventLog log) {
		this.log = log;
		this.log.addEvent(this, "Ready for screen actions");
		mainscreen = new MainScreen(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "RESET_ALL":
				log.addEvent(mainscreen, "Clear Textfield");
				mainscreen.resetTextArea();
				break;
			case "CLOSE":
				log.addEvent(mainscreen, "Close window");
				mainscreen.dispatchEvent(new WindowEvent(mainscreen, WindowEvent.WINDOW_CLOSING));
				break;
			case "REPORT_ERROR":
				log.addEvent(mainscreen, "Open Error report");
				try {
					Desktop.getDesktop().browse(new URI("https://github.com/dp-dev/RezepteGenerator/issues"));
				} catch (IOException | URISyntaxException ex) {
					ex.printStackTrace();
				}
				break;
			case "CREATE_PDF":
				log.addEvent(mainscreen, "PDF creation started");
				try {
					Thread t = new Thread(new ParserHandler(log));
					t.start();
					t.join();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				mainscreen.showEvents(log.getAllEvents());
				break;
		}
	}
	
	public void addEventToLog(Object obj, String event) {
		log.addEvent(obj, event);
	}
	
}
