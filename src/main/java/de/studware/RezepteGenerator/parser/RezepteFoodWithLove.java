package de.studware.RezepteGenerator.parser;

import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import de.studware.RezepteGenerator.Rezeptdaten;
import de.studware.RezepteGenerator.util.EventLog;

public class RezepteFoodWithLove extends AbstractParser {
	
	public RezepteFoodWithLove(EventLog log, Rezeptdaten rezeptdaten) {
		super(log, rezeptdaten);
	}
	
	@Override
	public void parseDocument() {
		// Get Titel
		Element entireTitle = doc.getElementsByClass("post-title").first();
		rezeptdaten.setRezeptTitle(entireTitle.getElementsByClass("post-title").first().text().substring(0, entireTitle.getElementsByClass("post-title").first().text().length() - 2));
		
		// Get Zutaten und Zubereitung
		Element entireIngredients = doc.getElementsByClass("post-body").first();
		List<TextNode> liste = entireIngredients.textNodes();
		int emptyLines = 0;
		for (TextNode textNode : liste) {
			String beginningCut = textNode.text().substring(1);
			if (beginningCut.contains("\u2661")) {
				rezeptdaten.addIngredientsToList(removeHearts(beginningCut).substring(1));
				emptyLines = 0;
			} else if (!beginningCut.isEmpty()) {
				if ((emptyLines > 3) && (rezeptdaten.getIngredientsList().size() > 2)) {
					rezeptdaten.addInstructionStep(beginningCut);
				}
			} else {
				emptyLines++;
			}
		}
	}
	
	private String removeHearts(String text) {
		return text.replaceAll("\u2661", "");
	}
	
}
