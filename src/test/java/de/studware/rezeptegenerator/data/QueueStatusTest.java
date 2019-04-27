package de.studware.rezeptegenerator.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QueueStatusTest {
	
	@Test
	public void checkQueueStatusPlain() {
		QueueStatus status = new QueueStatus(10);
		assertEquals(10, status.getPercentage());
		assertEquals(null, status.getMessage());
		assertEquals(null, status.getTitle());
	}
	
	@Test
	public void checkQueueStatusMessage() {
		QueueStatus status = new QueueStatus(10, "My message");
		assertEquals(10, status.getPercentage());
		assertEquals("My message", status.getMessage());
		assertEquals(null, status.getTitle());
	}
	
	
	@Test
	public void checkQueueStatusMessageTitle() {
		QueueStatus status = new QueueStatus(10, "Message", "My title");
		assertEquals(10, status.getPercentage());
		assertEquals("Message", status.getMessage());
		assertEquals("My title", status.getTitle());
	}

}
