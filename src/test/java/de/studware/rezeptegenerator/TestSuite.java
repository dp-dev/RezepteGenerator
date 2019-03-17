package de.studware.rezeptegenerator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.studware.rezeptegenerator.util.ClipboardGetterTest;
import de.studware.rezeptegenerator.util.ConfigHandlerTest;

@RunWith(Suite.class)
@SuiteClasses({ ConfigHandlerTest.class, RezeptDatenTest.class, ClipboardGetterTest.class })
public class TestSuite {
}
