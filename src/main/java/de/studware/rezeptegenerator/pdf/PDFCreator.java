package de.studware.rezeptegenerator.pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import de.studware.rezeptegenerator.Rezeptdaten;
import de.studware.rezeptegenerator.config.RezepteConfig;
import de.studware.rezeptegenerator.util.EventLog;

public class PDFCreator {
	private static final Logger logger = Logger.getLogger(PDFCreator.class.getName());
	private static final String SOFTWARE_INFO = "JAVA RezepteCooker";
	private static final String SYMBOL_BULLET = "\u2022";
	private EventLog log = null;
	private Rezeptdaten rezeptdaten = null;
	private FileFolder util = null;


	private static final Font DOCFONT_HEADING = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
	private static final Font DOCFONT_SUBHEADING = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
	private static final Font DOCFONT_NORMAL = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	private static final Font DOCFONT_SMALL = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC);
	
	public PDFCreator(EventLog log, Rezeptdaten rezeptdaten, RezepteConfig config) {
		this.log = log;
		this.rezeptdaten = rezeptdaten;
		util = new FileFolder(log, config);
	}
	
	public void createFolder() {
		util.createFolder();
	}
	
	public void createFile() {
		log.addEvent(this, "Create the new file");
		try {
			String filename = createFileName();
			Document document = new Document();
			String filepath = util.getFolderPath() + filename;
			PdfWriter.getInstance(document, new FileOutputStream(filepath));
			document.open();
			addMetaInfos(document);
			addContent(document);
			document.close();
			// Open PDF file after creation
			Desktop.getDesktop().open(new File(filepath));
		} catch (DocumentException | IOException e) {
			log.addEvent(this, "Problem with the PDFWriter instance");
			logger.log(Level.SEVERE, "Problem occurred with the PDFWriter instance", e);
		}
	}

	private String createFileName() {
		log.addEvent(this, "Create a new filename");
		boolean check = true;
		String name = rezeptdaten.getRezeptTitle().replace("/", "").replace("\\", "");
		name = name.replaceAll("\\s+", " ");
		String temp = name + ".pdf";
		check = new File(util.getFolder(), temp).exists();
		if (check) {
			int no = 0;
			do {
				no++;
				temp = name + no + ".pdf";
				check = new File(util.getFolder(), temp).exists();
				log.addEvent(this, "File exists - try new filename: " + temp);
			} while (check);
		}
		return temp;
	}
	
	private void addMetaInfos(Document document) {
		log.addEvent(this, "MetaInfos will be added");
		document.addTitle(rezeptdaten.getRezeptTitle());
		document.addSubject(rezeptdaten.getUrlpath());
		document.addAuthor(SOFTWARE_INFO);
		document.addCreator(SOFTWARE_INFO);
	}

	private void addContent(Document document) throws DocumentException {
		log.addEvent(this, "Content will be added");
		document.add(new Paragraph(rezeptdaten.getRezeptTitle(), DOCFONT_HEADING));
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
		log.addEvent(this, "Ingredients, Instructions added");

		if (!rezeptdaten.getHelpingTools().isEmpty()) {
			document.add(addEmptyLine(1));
			document.add(new Paragraph("Hilfsmittel:", DOCFONT_SUBHEADING));
			List listHelpTools = new List(12);
			listHelpTools.setListSymbol(SYMBOL_BULLET);
			for (String tools : rezeptdaten.getHelpingTools()) {
				listHelpTools.add(new ListItem(tools, DOCFONT_NORMAL));
			}
			document.add(listHelpTools);
			log.addEvent(this, "Helping tools added");
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
			log.addEvent(this, "Cooking Times added");
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
			log.addEvent(this, "Additional Infos added");
		}
	}

	private Paragraph addEmptyLine(int number) {
		log.addEvent(this, "Empty line added to content");
		Paragraph paragraph = new Paragraph();
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
		return paragraph;
	}
	
}
