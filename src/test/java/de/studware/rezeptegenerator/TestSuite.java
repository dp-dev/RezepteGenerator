package de.studware.rezeptegenerator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.studware.rezeptegenerator.util.EventLogTest;

@RunWith(Suite.class)
@SuiteClasses({ EventLogTest.class, RezeptDatenTest.class })
public class TestSuite {
}
