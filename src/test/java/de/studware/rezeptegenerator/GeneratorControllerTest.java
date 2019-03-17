package de.studware.rezeptegenerator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GeneratorControllerTest {

	@Test
	public void generateGeneratorController() {
		GeneratorController controller = new GeneratorController();
		assertEquals("Rezepte Generator", controller.getMainscreen().getFrame().getTitle());
	}

}
