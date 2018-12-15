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
	public void test1_checkUrl() {
		assertTrue(rezeptdaten.getUrlpath().equals("testUrl"));
	}
	
	@Test
	public void test2_checkRezeptTitle() {
		rezeptdaten.setRezeptTitle("RezTitle");
		assertTrue(rezeptdaten.getRezeptTitle().equals("RezTitle"));
	}
	
	@Test
	public void test3_checkIngredientsList() {
		rezeptdaten.addIngredientsToList("Sugar");
		List<String> list = rezeptdaten.getIngredientsList();
		assertTrue(list.size() == 1);
		assertTrue(list.get(0).equals("Sugar"));
	}
	
	@Test
	public void test4_checkInstructionSteps() {
		rezeptdaten.addInstructionStep("Mix first");
		rezeptdaten.addInstructionStep("Heat now");
		List<String> list = rezeptdaten.getInstructionSteps();
		assertTrue(list.size() == 2);
		assertTrue(list.get(0).equals("Mix first"));
	}
	
	@Test
	public void test5_checkHelpingTools() {
		rezeptdaten.addHelpingTool("Fork");
		List<String> list = rezeptdaten.getHelpingTools();
		assertTrue(list.size() == 1);
		assertTrue(list.get(0).equals("Fork"));
	}
	
	@Test
	public void test6_checkCookTime() {
		rezeptdaten.addCookTime("9 min");
		List<String> list = rezeptdaten.getCookTimes();
		assertTrue(list.size() == 1);
		assertTrue(list.get(0).equals("9 min"));
	}
	
	@Test
	public void test7_checkAdditionalInfos() {
		rezeptdaten.addAdditionalInfo("None");
		List<String> list = rezeptdaten.getAdditionalInfos();
		assertTrue(list.size() == 1);
		assertTrue(list.get(0).equals("None"));
	}
	

	
}
