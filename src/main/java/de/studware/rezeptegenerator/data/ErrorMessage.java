package de.studware.rezeptegenerator.data;

public enum ErrorMessage {
	
	URL_NOT_CORRECT("Das Format der URL stimmt leider nicht. Bitte kopiere diese erneut.", "URL Fehler"),
	DOC_CONTENT_WRONG("Das Dokument beinhaltet fehler, bitte melde diesen Fehler dem Entwickler", "Fehler im Dokument"),
	NO_PARSER("Diese Seite wird aktuell noch nicht vom Rezepte Generator unterstützt.\nBitte melde dies als Fehler dem Entwickler.", "Unstützung der Seite"),
	PDF_FAILURE("Es ist ein Fehler während der Erstellung des PDFs aufgetreten, bitte versuche es erneut.", "PDF Erstellungsfehler");
	
	private final String title;
	private final String message;
	
	ErrorMessage(String message, String title) {
		this.message = message;
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getMessage() {
		return message;
	}
}
