package de.studware.RezepteGenerator.util;

import java.util.ArrayList;

public class EventLog {
	private ArrayList<String> events;
	private String SEPERATOR = " |> ";
	
	public EventLog() {
		events = new ArrayList<>();
	}
	
	public ArrayList<String> getAllEvents() {
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
	
	public void printAllEventsConsole() {
		for (String event : events) {
			System.out.println(event);
		}
		System.out.println("--");
	}
}
