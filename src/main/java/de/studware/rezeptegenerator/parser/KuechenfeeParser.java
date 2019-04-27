package de.studware.rezeptegenerator.parser;

import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import de.studware.rezeptegenerator.data.RecipeData;

public class KuechenfeeParser extends AbstractParser {

	public KuechenfeeParser(RecipeData rezeptdaten) {
		super(rezeptdaten);
	}

	@Override
	public void parseDocument() {
		parseTitleAndIngredients();
		parseInstructions();
	}

	@Override
	public Whitelist getWhitelistForWebsite() {
		Whitelist whitelist = Whitelist.relaxed();
		whitelist.removeTags("span");
		whitelist.addAttributes("h1", "class");
		whitelist.addAttributes("div", "class");
		whitelist.removeAttributes("a", "href");
		return whitelist;
	}

	private void parseTitleAndIngredients() {
		// Get Title
		Element entireTitle = doc.getElementsByClass("post-title").first();
		rezeptdaten.setRecipeTitle(entireTitle.text());

		// Get Ingredients
		Element entireIngredients = doc.getElementsByClass("pane").first();
		Elements ingredients = entireIngredients.getElementsByTag("li");
		for (Element ingredient : ingredients) {
			rezeptdaten.addIngredientsToList(ingredient.text());
		}
	}

	private void parseInstructions() {
		// Get instruction steps
		Element entireSteps = doc.getElementsByClass("pane").last();
		Elements steps = entireSteps.getElementsByTag("li");
		for (Element step : steps) {
			rezeptdaten.addInstructionStep(step.text());
		}
	}

}
