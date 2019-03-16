package de.studware.rezeptegenerator;

import java.util.ArrayList;
import java.util.List;

public class Rezeptdaten {
	private final String urlpath;
	private String rezeptTitle;
	private ArrayList<String> ingredientsList = new ArrayList<>();
	private ArrayList<String> instructionSteps = new ArrayList<>();
	private ArrayList<String> helpingTools = new ArrayList<>();
	private ArrayList<String> cookTimes = new ArrayList<>();
	private ArrayList<String> additionalInfos = new ArrayList<>();
	
	public Rezeptdaten(String urlpath) {
		this.urlpath = urlpath;
	}
	
	public String getUrlpath() {
		return urlpath;
	}
	
	public String getRezeptTitle() {
		return rezeptTitle;
	}
	
	public void setRezeptTitle(String rezeptTitle) {
		this.rezeptTitle = rezeptTitle;
	}
	
	public List<String> getIngredientsList() {
		return ingredientsList;
	}
	
	public void addIngredientsToList(String ingredient) {
		this.ingredientsList.add(ingredient);
	}
	
	public List<String> getInstructionSteps() {
		return instructionSteps;
	}
	
	public void addInstructionStep(String step) {
		this.instructionSteps.add(step);
	}
	
	public List<String> getHelpingTools() {
		return helpingTools;
	}
	
	public void addHelpingTool(String helpingTool) {
		this.helpingTools.add(helpingTool);
	}
	
	public List<String> getCookTimes() {
		return cookTimes;
	}
	
	public void addCookTime(String cookTime) {
		this.cookTimes.add(cookTime);
	}
	
	public List<String> getAdditionalInfos() {
		return additionalInfos;
	}
	
	public void addAdditionalInfo(String additionalInfo) {
		this.additionalInfos.add(additionalInfo);
	}
}
