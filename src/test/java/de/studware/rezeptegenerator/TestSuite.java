package de.studware.rezeptegenerator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.studware.rezeptegenerator.data.RecipeDataTest;
import de.studware.rezeptegenerator.util.ClipboardGetterTest;
import de.studware.rezeptegenerator.util.ConfigHandlerTest;

@RunWith(Suite.class)
@SuiteClasses({ GeneratorControllerTest.class, ConfigHandlerTest.class, RecipeDataTest.class, ClipboardGetterTest.class })
public class TestSuite {
}
