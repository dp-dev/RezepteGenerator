package de.studware.rezeptegenerator.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.studware.rezeptegenerator.data.RecipeData;

public class KuechenfeeParser extends AbstractParser{

	public KuechenfeeParser(RecipeData rezeptdaten) {
		super(rezeptdaten);
	}

	@Override
	public void parseDocument() {
		// Get Titel
		Element entireTitle = doc.getElementsByClass("post-title").first();
		rezeptdaten.setRecipeTitle(entireTitle.text());

		// Get Zutaten
		Element entireIngredients = doc.getElementsByClass("pane").first();
		Elements ingredients = entireIngredients.getElementsByTag("li");
		for (Element ingredient : ingredients) {
			rezeptdaten.addIngredientsToList(ingredient.text());
		}
		
		// Get Zubereitung
		Element entireSteps = doc.getElementsByClass("pane").last();
		Elements steps = entireSteps.getElementsByTag("li");
		for (Element step : steps) {
			rezeptdaten.addInstructionStep(step.text());
		}
	}
	
}
