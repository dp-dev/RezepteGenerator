package de.studware.rezeptegenerator;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RezeptDatenTest {
	private Rezeptdaten rezeptdaten;

	@Before
	public void initRezeptdaten() {
		rezeptdaten = new Rezeptdaten("testUrl");
	}

	@Test
	public void testCheckUrl() {
		assertEquals(rezeptdaten.getUrlpath(), "testUrl");
	}

	@Test
	public void testCheckRezeptTitle() {
		rezeptdaten.setRezeptTitle("RezTitle");
		assertEquals(rezeptdaten.getRezeptTitle(), "RezTitle");
	}

	@Test
	public void testCheckIngredientsList() {
		rezeptdaten.addIngredientsToList("Sugar");
		List<String> list = rezeptdaten.getIngredientsList();
		assertEquals(list.size(), 1);
		assertEquals(list.get(0), "Sugar");
	}

	@Test
	public void testCheckInstructionSteps() {
		rezeptdaten.addInstructionStep("Mix first");
		rezeptdaten.addInstructionStep("Heat now");
		List<String> list = rezeptdaten.getInstructionSteps();
		assertEquals(list.size(), 2);
		assertEquals(list.get(0), "Mix first");
		assertEquals(list.get(1), "Heat now");
	}

	@Test
	public void testCheckHelpingTools() {
		rezeptdaten.addHelpingTool("Fork");
		List<String> list = rezeptdaten.getHelpingTools();
		assertEquals(list.size(), 1);
		assertEquals(list.get(0), "Fork");
	}

	@Test
	public void testCheckCookTime() {
		rezeptdaten.addCookTime("9 min");
		List<String> list = rezeptdaten.getCookTimes();
		assertEquals(list.size(), 1);
		assertEquals(list.get(0), "9 min");
	}

	@Test
	public void testCheckAdditionalInfos() {
		rezeptdaten.addAdditionalInfo("None");
		List<String> list = rezeptdaten.getAdditionalInfos();
		assertEquals(list.size(), 1);
		assertEquals(list.get(0), "None");
	}

}
