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
import de.studware.rezeptegenerator.util.EventLog;

public class PDFCreator {
	private static final Logger logger = Logger.getLogger(PDFCreator.class.getName());
	private EventLog log = null;
	private Rezeptdaten rezeptdaten = null;
	private FileFolder util = null;
	private static String SOFTWARE_INFO = "JAVA RezepteCooker";

	private static Font DOCFONT_HEADING = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
	private static Font DOCFONT_SUBHEADING = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
	private static Font DOCFONT_NORMAL = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	private static Font DOCFONT_SMALL = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC);
	
	public PDFCreator(EventLog log, Rezeptdaten rezeptdaten) {
		this.log = log;
		this.rezeptdaten = rezeptdaten;
		util = new FileFolder(log, rezeptdaten);
	}
	
	public void createFolder() {
		util.createFolder();
	}
	
	public void createFile() {
		log.addEvent(this, "Create the new file");
		try {
			String filename = createFileName();
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(util.getFolder().getAbsolutePath() + "\\" + filename));
			document.open();
			addMetaInfos(document);
			addContent(document);
			document.close();
			// Open PDF file after creation
			Desktop.getDesktop().open(new File(util.getFolder().getAbsolutePath() + "\\" + filename));
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
		List list_Ingredients = new List(12);
		list_Ingredients.setListSymbol("\u2022");
		for (String ingredient : rezeptdaten.getIngredientsList()) {
			list_Ingredients.add(new ListItem(ingredient, DOCFONT_NORMAL));
		}
		document.add(list_Ingredients);
		
		document.add(new Paragraph("Zubereitung:", DOCFONT_SUBHEADING));
		List list_Instructions = new List(12);
		list_Instructions.setListSymbol("\u2022");
		for (String step : rezeptdaten.getInstructionSteps()) {
			list_Instructions.add(new ListItem(step, DOCFONT_NORMAL));
		}
		document.add(list_Instructions);
		log.addEvent(this, "Ingredients, Instructions added");

		if (rezeptdaten.getHelpingTools().size() > 0) {
			document.add(addEmptyLine(1));
			document.add(new Paragraph("Hilfsmittel:", DOCFONT_SUBHEADING));
			List list_HelpTools = new List(12);
			list_HelpTools.setListSymbol("\u2022");
			for (String tools : rezeptdaten.getHelpingTools()) {
				list_HelpTools.add(new ListItem(tools, DOCFONT_NORMAL));
			}
			document.add(list_HelpTools);
			log.addEvent(this, "Helping tools added");
		}
		
		if (rezeptdaten.getCookTimes().size() > 0) {
			document.add(addEmptyLine(1));
			document.add(new Paragraph("Kochzeit " + rezeptdaten.getCookTimes().get(0), DOCFONT_SUBHEADING));
			List list_Times = new List(12);
			list_Times.setListSymbol("\u2022");
			for (String time : rezeptdaten.getCookTimes()) {
				if (!time.equals(rezeptdaten.getCookTimes().get(0))) {
					list_Times.add(new ListItem(time, DOCFONT_NORMAL));
				}
			}
			document.add(list_Times);
			log.addEvent(this, "Cooking Times added");
		}
		
		if (rezeptdaten.getAdditionalInfos().size() > 0) {
			document.add(addEmptyLine(1));
			document.add(new Paragraph("Zusätzliche Infos", DOCFONT_SUBHEADING));
			List list_Infos = new List(12);
			list_Infos.setListSymbol("\u2022");
			for (String info : rezeptdaten.getAdditionalInfos()) {
				list_Infos.add(new ListItem(info, DOCFONT_NORMAL));
			}
			document.add(list_Infos);
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
