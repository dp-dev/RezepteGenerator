package de.studware.rezeptegenerator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.studware.rezeptegenerator.util.EventLog;

public class EventLogTest {
	private EventLog eventlog;
	
	@Before
	public void initEventLog() {
		eventlog = new EventLog();
	}
	
	@Test
	public void test1_SetupEventlog() {
		assertEquals(EventLog.class, eventlog.getClass());
	}
	
	@Test
	public void test2_addEvent() {
		Rezeptdaten daten = new Rezeptdaten("testurl");
		eventlog.addEvent(daten, "Testing");
		int noEntries = eventlog.getAllEvents().size();
		assertTrue("Found "+ noEntries + "instead of 1", noEntries == 1);		
	}
	
}
