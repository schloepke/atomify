/*
 * Copyright (c) 2009-2016 Stephan Schloepke
 *
 * Stephan Schloepke: http://www.schloepke.de/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.atomify.model.syndication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.atomify.model.common.AtomLanguage;
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
