package de.studware.rezeptegenerator.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.studware.rezeptegenerator.Rezeptdaten;

public class EventLogTest {
	private EventLog eventlog;

	@Before
	public void initEventLog() {
		eventlog = new EventLog();
	}

	@Test
	public void testSetupEventlog() {
		assertEquals("Different class than EventLog found", EventLog.class, eventlog.getClass());
	}

	@Test
	public void testAddEvent() {
		Rezeptdaten daten = new Rezeptdaten("testurl");
		eventlog.addEvent(daten, "Testing");
		assertEquals("Number of Events does not match 1", eventlog.getAllEvents().size(), 1);
	}

}
