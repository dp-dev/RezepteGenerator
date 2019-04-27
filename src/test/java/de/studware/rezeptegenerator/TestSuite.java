package de.studware.rezeptegenerator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.studware.rezeptegenerator.config.ConfigHandlerTest;
import de.studware.rezeptegenerator.data.ErrorMessageTest;
import de.studware.rezeptegenerator.data.QueueStatusTest;
import de.studware.rezeptegenerator.data.RecipeDataTest;
import de.studware.rezeptegenerator.display.MainScreenTest;
import de.studware.rezeptegenerator.util.ClipboardGetterTest;

@RunWith(Suite.class)
@SuiteClasses({ //
		MainScreenTest.class, //
		ConfigHandlerTest.class, //
		RecipeDataTest.class, //
		ErrorMessageTest.class, //
		QueueStatusTest.class, //
		ClipboardGetterTest.class })

public class TestSuite {
}
