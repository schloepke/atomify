/*
 * Copyright (c) 2009 Stephan Schloepke and innoQ Deutschland GmbH
 *
 * Stephan Schloepke: http://www.schloepke.de/
 * innoQ Deutschland GmbH: http://www.innoq.com/
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
package org.atomify.model.publishing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.net.URI;
import java.util.Locale;

import org.atomify.model.common.AtomLanguage;
import org.atomify.model.syndication.AtomEntry;
import org.jbasics.xml.types.XmlSpaceType;
import org.junit.Test;

public class AtomPubAcceptTest {

	@Test
	public void testSimpleBuilding() {
		AtomPubAcceptBuilder builder = AtomPubAccept.newBuilder();
		AtomPubAccept test = builder.setAcceptMediaRange(AtomEntry.MEDIA_TYPE).build();
		assertEquals(AtomEntry.MEDIA_TYPE.toString(), test.getAcceptMediaRange().toString());
		AtomPubAccept test2 = builder.build();
		assertSame(test, test2);
		builder.reset();
		test = builder.setAcceptMediaRange("text/*").build();
		test2 = builder.build();
		assertNotSame(test, test2);
		assertEquals(test, test2);
		builder.reset();
		test = builder.build();
		assertEquals(null, test.getAcceptMediaRange());
	}

	@Test
	public void testBuildingWithCommonAttributes() {
		URI base = URI.create("http://example.com/");
		AtomPubAcceptBuilder builder = AtomPubAccept.newBuilder();
		AtomPubAccept test = builder.setXmlBase(base).setXmlLang(AtomLanguage.valueOf(Locale.GERMAN)).setXmlSpace(
				XmlSpaceType.PRESERVED).setAcceptMediaRange(AtomEntry.MEDIA_TYPE).build();
		assertEquals(AtomEntry.MEDIA_TYPE.toString(), test.getAcceptMediaRange().toString());
		assertEquals(base, test.getXmlBase());
		assertEquals(XmlSpaceType.PRESERVED, test.getXmlSpace());
		assertEquals(Locale.GERMAN, test.getXmlLang().toLocale());
	}
	
	@Test
	public void testEqualsAndHashCode() {
		URI base = URI.create("http://example.com/");
		AtomPubAcceptBuilder builder = AtomPubAccept.newBuilder();
		AtomPubAccept test = builder.setXmlBase(base).setXmlLang(AtomLanguage.valueOf(Locale.GERMAN)).setXmlSpace(
				XmlSpaceType.PRESERVED).setAcceptMediaRange(AtomEntry.MEDIA_TYPE).build();
		AtomPubAccept testTwo = builder.build();
		assertNotSame(test, testTwo);
		assertEquals(test, testTwo);
		assertEquals(test.hashCode(), testTwo.hashCode());
		testTwo = builder.setXmlBase(null).build();
		assertNotSame(test, testTwo);
		assertFalse(test.equals(testTwo));
		assertFalse(test.hashCode() == testTwo.hashCode());
		assertEquals(test, test);
	}

}
