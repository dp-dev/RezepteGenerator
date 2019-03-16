package de.studware.rezeptegenerator.parser;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import de.studware.rezeptegenerator.data.RecipeData;
import de.studware.rezeptegenerator.util.EventLog;

public class FoodWithLoveParser extends AbstractParser {
	
	public FoodWithLoveParser(EventLog log, RecipeData rezeptdaten) {
		super(log, rezeptdaten, true);
	}
	
	@Override
	public void parseDocument() {
		// Get Titel
		Element entireTitle = doc.getElementsByClass("entry-title").first();
		rezeptdaten.setRecipeTitle(removeHearts(entireTitle.text()));
		
		// Get Zutaten und Zubereitung
		Element entireIngredients = doc.getElementsByClass("pf-content").first();
		List<Element> liste = entireIngredients.getElementsByTag("p");
		int currentIndex = 0;
		for (int i = 0; i < liste.size(); i++) {
			currentIndex++;
			if(liste.get(i).text().contains("Ihr benötigt")) {
				break;
			}
		}
		extractIngredients(liste.get(currentIndex).toString());
		currentIndex++;
		for (int i = currentIndex; i < liste.size(); i++) {
			String line = liste.get(i).text();
			if(!line.isEmpty() && !line.startsWith(" ")) {
				rezeptdaten.addInstructionStep(removeHTML(liste.get(i).text()));
			}
		}
	}

	private void extractIngredients(String text) {
		String line = text.replaceFirst("<p>", "");
		line = line.replace("</p>", "");
		String[] ingredientList = line.split("<br> ");
		for (String ingredient : ingredientList) {
			rezeptdaten.addIngredientsToList(removeHTML(ingredient));
		}
	}	
	
	private String removeHTML(String textHTML) {
		Document doc = Jsoup.parse(textHTML);
		return removeHearts(doc.text()).trim();
	}
	
	private String removeHearts(String text) {
		return text.replaceAll("\u2661", "");
	}
}
