package de.studware.rezeptegenerator.data;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.studware.rezeptegenerator.data.RecipeData;

public class RecipeDataTest {
	private RecipeData rezeptdaten;
	private static final String URL = "testurl";

	@Before
	public void initRezeptdaten() {
		rezeptdaten = new RecipeData(URL);
	}

	@Test
	public void testCheckUrl() {
		assertEquals(URL, rezeptdaten.getUrlpath());
	}

	@Test
	public void testCheckRezeptTitle() {
		final String TITLE = "RezTitle";
		rezeptdaten.setRecipeTitle(TITLE);
		assertEquals(TITLE, rezeptdaten.getRecipeTitle());
	}

	@Test
	public void testCheckIngredientsList() {
		final String INGREDIENT = "Sugar";
		rezeptdaten.addIngredientsToList(INGREDIENT);
		List<String> list = rezeptdaten.getIngredientsList();
		assertEquals(1, list.size());
		assertEquals(INGREDIENT, list.get(0));
	}

	@Test
	public void testCheckInstructionSteps() {
		final String STEP1 = "Mix first";
		final String STEP2 = "Heat now";		
		rezeptdaten.addInstructionStep(STEP1);
		rezeptdaten.addInstructionStep(STEP2);
		List<String> list = rezeptdaten.getInstructionSteps();
		assertEquals(2, list.size());
		assertEquals(STEP1, list.get(0));
		assertEquals(STEP2, list.get(1));
	}

	@Test
	public void testCheckHelpingTools() {
		final String TOOL = "Fork";
		rezeptdaten.addHelpingTool(TOOL);
		List<String> list = rezeptdaten.getHelpingTools();
		assertEquals(1, list.size());
		assertEquals(TOOL, list.get(0));
	}

	@Test
	public void testCheckCookTime() {
		final String TIME = "9 min";
		rezeptdaten.addCookTime(TIME);
		List<String> list = rezeptdaten.getCookTimes();
		assertEquals(1, list.size());
		assertEquals(TIME, list.get(0));
	}

	@Test
	public void testCheckAdditionalInfos() {
		final String INFO = "Cook slowly";
		rezeptdaten.addAdditionalInfo(INFO);
		List<String> list = rezeptdaten.getAdditionalInfos();
		assertEquals(1, list.size());
		assertEquals(INFO, list.get(0));
	}

}
