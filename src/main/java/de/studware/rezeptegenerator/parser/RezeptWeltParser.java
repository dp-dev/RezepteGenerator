package de.studware.rezeptegenerator.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.studware.rezeptegenerator.data.RecipeData;

public class RezeptWeltParser extends AbstractParser {

	public RezeptWeltParser(RecipeData rezeptdaten) {
		super(rezeptdaten);
	}

	private void checkStepsforImages(Elements steps) {
		for (Element element : steps) {
			Elements images = element.select("img");
			Element neu = null;
			for (Element elementWithImage : images) {
				String newElement = element.toString().replace(elementWithImage.toString(),
						getImageName(elementWithImage));
				neu = element.html(newElement);
			}
			String ausgabe = "";
			String text = "";
			if (!images.isEmpty()) {
				if (neu != null) {
					text = neu.text().replaceAll("\u00a0", " ");
					ausgabe = text.replace(".", ". ");
				}
			} else {
				if (!element.text().isEmpty()) {
					text = element.text().replaceAll("\u00a0", " ");
					ausgabe = text.replace(".", ". ");
				}
			}
			if (!ausgabe.equals("") && !ausgabe.equals(" ")) {
				ausgabe = ausgabe.replaceAll("\\s+", " ");
				rezeptdaten.addInstructionStep(ausgabe);
			}
		}
	}

	private String getImageName(Element element) {
		Element entireElement = element.getElementsByAttribute("title").first();
		if (entireElement != null) {
			String title = entireElement.attr("title");
			if (!title.contains("emoticons")) {
				switch (title) {
				case "Mixtopf geschlossen":
				case "Closed lid":
					title = " Mixtopf";
					break;
				case "Sanftrührstufe":
				case "Gentle stir setting":
					title = " Sanftrührstufe";
					break;
				case "Linkslauf":
				case "Counter-clockwise operation":
					title = " Linkslauf";
					break;
				case "Knetstufe":
				case "Dough mode":
					title = " Knetstufe";
					break;
				default:
					title = " 'SYMBOL NAME NOT FOUND >> '" + title + "'";
					break;
				}
			} else {
				title = " ";
			}
			return title;
		} else {
			return "";
		}
	}

	@Override
	public void parseDocument() {
		// Get Titel
		Element entireTitle = doc.getElementsByClass("recipe-title-heading").first();
		rezeptdaten.setRecipeTitle(entireTitle.getElementsByTag("a").first().text());

		// Get Zutaten
		Element entireIngredients = doc.getElementsByClass("ingredients").first();
		Elements ingredients = entireIngredients.getElementsByTag("li");
		for (Element ingredient : ingredients) {
			rezeptdaten.addIngredientsToList(ingredient.text());
		}

		// Get Zubereitung
		Element entireSteps = doc.getElementsByClass("steps").first();
		Elements steps = entireSteps.getElementsByTag("p");
		if (steps.isEmpty()) {
			steps = entireSteps.getElementsByTag("li");
		}
		checkStepsforImages(steps);

		// Get Hilfsmittel
		Element helpSection = doc.getElementsByClass("accessories-list").first();
		if (helpSection != null) {
			Elements helpToolnames = helpSection.getElementsByClass("media-heading");
			for (Element element : helpToolnames) {
				rezeptdaten.addHelpingTool(element.text());
			}
		}

		// Get Sonstige Infos TM5/TM31 oder Zeiten
		Element additionalSection = doc.getElementsByClass("additional-info").first();
		Elements additionalInfos = additionalSection.getElementsByClass("media-body");
		for (Element element : additionalInfos) {
			if (element.text().contains("min")) {
				String[] parts = element.text().split("min");
				for (int i = 0; i < parts.length; i++) {
					rezeptdaten.addCookTime(parts[i].trim() + " Minuten");
				}
			} else if (element.toString().contains("smallText")) {
				StringBuilder builder = new StringBuilder();
				builder.append(element.getElementsByClass("smallText").first().text());
				builder.append(": ");
				builder.append(element.getElementsByClass("media-heading").first().text());
				rezeptdaten.addAdditionalInfo(builder.toString());
			}
		}
	}
}