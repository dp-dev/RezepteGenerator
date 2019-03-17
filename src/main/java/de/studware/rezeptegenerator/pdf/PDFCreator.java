package de.studware.rezeptegenerator.pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import de.studware.rezeptegenerator.config.ConfigHandler;
import de.studware.rezeptegenerator.data.RecipeData;

public class PDFCreator {
	private static final Logger LOG = Logger.getLogger(PDFCreator.class.getName());
	private static final String SOFTWARE_INFO = "JAVA RezepteCooker";
	private static final String SYMBOL_BULLET = "\u2022";
	private final RecipeData rezeptdaten;
	private final FileFolder util;

	private static final Font DOCFONT_HEADING = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
	private static final Font DOCFONT_SUBHEADING = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
	private static final Font DOCFONT_NORMAL = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	private static final Font DOCFONT_SMALL = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC);

	public PDFCreator(RecipeData rezeptdaten, ConfigHandler config) {
		this.rezeptdaten = rezeptdaten;
		util = new FileFolder(config);
	}

	public void createFolder() {
		util.createFolder();
	}

	public boolean createFile() {
		LOG.info("Create the new file");
		try {
			String filename = util.createFileName(rezeptdaten);
			Document document = new Document();
			String filepath = util.getFolderPath() + filename;
			PdfWriter.getInstance(document, new FileOutputStream(filepath));
			document.open();
			addMetaInfos(document);
			addContent(document);
			document.close();
			Desktop.getDesktop().open(new File(filepath));
			return true;
		} catch (DocumentException | IOException e) {
			LOG.severe("Problem occurred with the PDFWriter instance" + e.getMessage());
			return false;
		}
	}

	private void addMetaInfos(Document document) {
		LOG.info("MetaInfos will be added");
		document.addTitle(rezeptdaten.getRecipeTitle());
		document.addSubject(rezeptdaten.getUrlpath());
		document.addAuthor(SOFTWARE_INFO);
		document.addCreator(SOFTWARE_INFO);
	}

	private void addContent(Document document) throws DocumentException {
		LOG.info("Content will be added");
		document.add(new Paragraph(rezeptdaten.getRecipeTitle(), DOCFONT_HEADING));
		document.add(new Paragraph(rezeptdaten.getUrlpath(), DOCFONT_SMALL));
		document.add(addEmptyLine(1));

		document.add(new Paragraph("Zutaten:", DOCFONT_SUBHEADING));
		List listIngredients = new List(12);
		listIngredients.setListSymbol(SYMBOL_BULLET);
		for (String ingredient : rezeptdaten.getIngredientsList()) {
			listIngredients.add(new ListItem(ingredient, DOCFONT_NORMAL));
		}
		document.add(listIngredients);

		document.add(new Paragraph("Zubereitung:", DOCFONT_SUBHEADING));
		List listInstructions = new List(12);
		listInstructions.setListSymbol(SYMBOL_BULLET);
		for (String step : rezeptdaten.getInstructionSteps()) {
			listInstructions.add(new ListItem(step, DOCFONT_NORMAL));
		}
		document.add(listInstructions);
		LOG.info("Ingredients, Instructions added");

		if (!rezeptdaten.getHelpingTools().isEmpty()) {
			document.add(addEmptyLine(1));
			document.add(new Paragraph("Hilfsmittel:", DOCFONT_SUBHEADING));
			List listHelpTools = new List(12);
			listHelpTools.setListSymbol(SYMBOL_BULLET);
			for (String tools : rezeptdaten.getHelpingTools()) {
				listHelpTools.add(new ListItem(tools, DOCFONT_NORMAL));
			}
			document.add(listHelpTools);
			LOG.info("Helping tools added");
		}

		if (!rezeptdaten.getCookTimes().isEmpty()) {
			document.add(addEmptyLine(1));
			document.add(new Paragraph("Kochzeit " + rezeptdaten.getCookTimes().get(0), DOCFONT_SUBHEADING));
			List listTimes = new List(12);
			listTimes.setListSymbol(SYMBOL_BULLET);
			for (String time : rezeptdaten.getCookTimes()) {
				if (!time.equals(rezeptdaten.getCookTimes().get(0))) {
					listTimes.add(new ListItem(time, DOCFONT_NORMAL));
				}
			}
			document.add(listTimes);
			LOG.info("Cooking Times added");
		}

		if (!rezeptdaten.getAdditionalInfos().isEmpty()) {
			document.add(addEmptyLine(1));
			document.add(new Paragraph("Zusätzliche Infos", DOCFONT_SUBHEADING));
			List listInfos = new List(12);
			listInfos.setListSymbol(SYMBOL_BULLET);
			for (String info : rezeptdaten.getAdditionalInfos()) {
				listInfos.add(new ListItem(info, DOCFONT_NORMAL));
			}
			document.add(listInfos);
			LOG.info("Additional Infos added");
		}
	}

	private Paragraph addEmptyLine(int number) {
		LOG.info("Empty line added to content");
		Paragraph paragraph = new Paragraph();
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
		return paragraph;
	}

}
