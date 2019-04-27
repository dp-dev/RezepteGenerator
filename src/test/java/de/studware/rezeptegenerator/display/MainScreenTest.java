package de.studware.rezeptegenerator.display;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.studware.rezeptegenerator.GeneratorController;

public class MainScreenTest {
	GeneratorController controller;
	
	@Before
	public void setUpController() {
		controller = new GeneratorController();
	}

	@Test
	public void generateGeneratorController() {
		assertEquals("Rezepte Generator", controller.getMainscreen().getFrame().getTitle());
	}
	
	@Test
	public void setProgressBar() {
		controller.getMainscreen().setProgressStatusTo(10);
		assertEquals(10, controller.getMainscreen().getProgressBarValue());
	}

}
