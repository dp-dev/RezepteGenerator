package de.studware.rezeptegenerator.pdf;

import java.io.File;

import de.studware.rezeptegenerator.Rezeptdaten;
import de.studware.rezeptegenerator.config.RezepteConfig;
import de.studware.rezeptegenerator.util.EventLog;

public class FileFolder {
	private EventLog log;
	private File folder;
	
	
	public FileFolder(EventLog log, RezepteConfig config) {
		this.log = log;
		this.folder = new File(config.getProperty("folder.name"));
	}

	public void createFolder() {
		if (!folder.exists()) {
			folder.mkdir();
			log.addEvent(this, "Created new folder for all the recipe");
		} else {
			log.addEvent(this, "Folder already exists");
		}
	}
	
	public String createFileName(Rezeptdaten rezeptdaten) {
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
	
	public String getFolderPath() {
		return folder.getAbsolutePath() + "\\";
	}
	
	public File getFolder() {
		return folder;
	}
	
}
