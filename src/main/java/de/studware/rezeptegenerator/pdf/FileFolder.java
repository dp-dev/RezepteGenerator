package de.studware.rezeptegenerator.pdf;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.studware.rezeptegenerator.config.ConfigHandler;
import de.studware.rezeptegenerator.data.RecipeData;

public class FileFolder {
	private static final Logger LOG = Logger.getLogger(FileFolder.class.getSimpleName());
	private final File folder;

	public FileFolder(ConfigHandler config) {
		this.folder = new File(config.getProperty("folder.name"));
	}

	public void createFolder() {
		if (!folder.exists()) {
			folder.mkdir();
			LOG.info("Created new folder for all the recipe");
		} else {
			LOG.info("Folder already exists");
		}
	}

	public String createFileName(RecipeData rezeptdaten) {
		LOG.info("Create a new filename");
		boolean check = true;
		String name = rezeptdaten.getRecipeTitle().replace("/", "").replace("\\", "").replace("|", "");
		name = name.replaceAll("\\s+", " ");
		String temp = name + ".pdf";
		check = new File(folder, temp).exists();
		if (check) {
			int no = 0;
			do {
				no++;
				temp = name + no + ".pdf";
				check = new File(folder, temp).exists();
				LOG.log(Level.FINE, "File exists - try new filename: {0}", temp);
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
