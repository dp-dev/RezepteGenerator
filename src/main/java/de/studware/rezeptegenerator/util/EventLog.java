package de.studware.rezeptegenerator.util;

import java.util.ArrayList;
import java.util.List;

public class EventLog {
	private ArrayList<String> events;
	private final static String SEPERATOR = " |> ";
	
	public EventLog() {
		events = new ArrayList<>();
	}
	
	public List<String> getAllEvents() {
		return events;
	}
	
	public void addEvent(Object obj, String event) {
		StringBuilder builder = new StringBuilder();
		builder.append(DateTime.getDateTime());
		builder.append(SEPERATOR);
		builder.append(obj.getClass().getSimpleName());
		builder.append(SEPERATOR);
		builder.append(event);
		events.add(builder.toString());
	}
}
