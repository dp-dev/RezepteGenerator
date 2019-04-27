package de.studware.rezeptegenerator.parser;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

import de.studware.rezeptegenerator.data.RecipeData;

public class FoodWithLoveParser extends AbstractParser {
	
	public FoodWithLoveParser(RecipeData rezeptdaten) {
		super(rezeptdaten);
	}
	
	@Override
	public void parseDocument() {
		// Get Title
		Element entireTitle = doc.getElementsByClass("entry-title").first();
		rezeptdaten.setRecipeTitle(removeHearts(entireTitle.text()));
		
		// Get ingredients & instuction steps
		Element entireIngredients = doc.getElementsByClass("pf-content").first();
		List<Element> list = entireIngredients.getElementsByTag("p");
		getIngredients(list);
		getInstructions(list);

	}

	private void getInstructions(List<Element> liste) {
		//Get instruction steps
		boolean addToList = false;
		for (Element element : liste) {
			if(element.text().contains("So geht") || element.text().contains("Manu ")) {
				addToList = !addToList;
				continue;
			}
			if(addToList) {
				String line = element.text();
				if(!line.isEmpty() && !line.startsWith(" ")) {
					rezeptdaten.addInstructionStep(removeHTML(line));
				}
			}
		}		
	}

	private void getIngredients(List<Element> liste) {
		boolean addToList = false;
		for (Element element : liste) {
			if(element.text().contains("So geht")) {
				return;
			}
			if(element.text().contains("Ihr benötigt")) {
				addToList = true;
				continue;
			}
			if(addToList) {
				extractIngredients(element.toString());
			}
		}		
	}

	private void extractIngredients(String text) {
		String line = text.replaceFirst("<p>", "");
		line = line.replace("</p>", "");
		String[] ingredientList = line.split("<br>");
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

	@Override
	public Whitelist getWhitelistForWebsite() {
		Whitelist whitelist = Whitelist.relaxed();
		whitelist.addAttributes("h1", "class");
		whitelist.addAttributes("div", "class");
		whitelist.removeTags("img");
		return whitelist;
	}
}
