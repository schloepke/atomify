package org.atomify.model.syndication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Test;

public class AtomLanguageTest {

	@Test
	public void testValueOfString() {
		AtomLanguage test = AtomLanguage.valueOf("de");
		assertEquals("de", test.getLanguage());
		assertEquals("de", test.toString());
		test = AtomLanguage.valueOf("de-DE");
		assertEquals("de-DE", test.getLanguage());
		assertEquals("de-DE", test.toString());
		// Testing the value for "reset to default"
		test = AtomLanguage.valueOf("");
		assertEquals("", test.getLanguage());
		assertEquals("", test.toString());
	}

	@Test
	public void testIllegalValue() {
		try {
			AtomLanguage.valueOf((String) null);
			fail("Null value call did not raise exception");
		} catch (IllegalArgumentException e) {
			// correct behavior
		}
		try {
			AtomLanguage.valueOf("de_1973");
			fail("Illegal value call did not raise exception");
		} catch (IllegalArgumentException e) {
			// correct behavior
		}
	}

	@Test
	public void testValueOfLocale() {
		Locale fullLocale = new Locale("de", "DE", "KOE");
		assertEquals(fullLocale, AtomLanguage.valueOf(fullLocale).toLocale());
		assertEquals(Locale.GERMAN, AtomLanguage.valueOf(Locale.GERMAN).toLocale());
		assertEquals(Locale.GERMANY, AtomLanguage.valueOf(Locale.GERMANY).toLocale());
		assertEquals(Locale.CANADA, AtomLanguage.valueOf(Locale.CANADA).toLocale());
		assertEquals(Locale.UK, AtomLanguage.valueOf(Locale.UK).toLocale());
		assertEquals(Locale.US, AtomLanguage.valueOf(Locale.US).toLocale());
	}

	@Test
	public void testIsValidLanguage() {
		assertTrue(AtomLanguage.isValidLanguage("de-DE"));
		assertFalse(AtomLanguage.isValidLanguage("de_DE"));
	}

	@Test
	public void testHashCodeAndEquals() {
		AtomLanguage testOne = AtomLanguage.valueOf("de");
		AtomLanguage testTwo = AtomLanguage.valueOf("fr");
		AtomLanguage testThree = AtomLanguage.valueOf("de");
		assertNotSame(testOne, testThree);
		assertEquals(testOne, testThree);
		assertEquals(testOne, testOne);
		assertFalse(testOne.equals(testTwo));
		assertFalse(testOne.equals("de"));
		assertEquals(testOne.hashCode(), testThree.hashCode());
	}
}
