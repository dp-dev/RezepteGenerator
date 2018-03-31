package de.studware.RezepteGenerator.pdf;

import java.io.File;

import de.studware.RezepteGenerator.Rezeptdaten;
import de.studware.RezepteGenerator.util.EventLog;

public class FileFolder {
	private EventLog log = null;
	private Rezeptdaten rezeptdaten = null;
	private File folder = new File("Rezepte");
	
	
	public FileFolder(EventLog log, Rezeptdaten rezeptdaten) {
		this.log = log;
		this.rezeptdaten = rezeptdaten;
	}

	public void createFolder() {
		if (!folder.exists()) {
			folder.mkdir();
			log.addEvent(this, "Created new folder for all the recipe");
		} else {
			log.addEvent(this, "Folder already exists");
		}
	}
	
	public String createFileName() {
		log.addEvent(this, "Create a new filename");
		boolean check = true;
		String name = rezeptdaten.getRezeptTitle().replace("/", "").replace("\\", "");
		name = name.replaceAll("\\s+", " ");
		String temp = name + ".pdf";
		check = new File(folder, temp).exists();
		if (check) {
			int no = 0;
			do {
				no++;
				temp = name + no + ".pdf";
				check = new File(folder, temp).exists();
				log.addEvent(this, "File exists - try new filename: " + temp);
			} while (check);
		}
		return temp;
	}
	
	public File getFolder() {
		return folder;
	}
	
}
