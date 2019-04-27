package de.studware.rezeptegenerator.data;

public class QueueStatus {
	private final int percentage;
	private final String message;
	private final String title;
	
	public QueueStatus(int percentage, String message, String title) {
		this.percentage = percentage;
		this.message = message;
		this.title = title;
	}
	
	public QueueStatus(int percentage, String message) {
		this(percentage, message, null);
	}
	
	public QueueStatus(int percentage) {
		this(percentage, null);
	}

	public int getPercentage() {
		return percentage;
	}

	public String getMessage() {
		return message;
	}
	
	public String getTitle() {
		return title;
	}
	
}
