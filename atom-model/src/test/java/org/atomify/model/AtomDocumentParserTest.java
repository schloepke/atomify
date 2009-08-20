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
package org.atomify.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.atomify.model.publishing.AtomPubCategories;
import org.atomify.model.publishing.AtomPubService;
import org.atomify.model.syndication.AtomEntry;
import org.atomify.model.syndication.AtomFeed;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AtomDocumentParserTest {
	private static AtomDocumentParser parser;

	private String resource;
	private Class<? extends AtomDocument> expectedType;

	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] { // DocumentResouce, ExpectedDocumentClass
				{ "publishing/atom-service-document.xml", AtomPubService.class }, // AtomPubService
						{ "publishing/atom-categories-document.xml", AtomPubCategories.class }, // AtomPubCategories
						{ "syndication/atom-feed-document.xml", AtomFeed.class }, // AtomFeed
						{ "syndication/atom-entry-document.xml", AtomEntry.class } // AtomEntry
				});
	}

	public AtomDocumentParserTest(String resource, Class<? extends AtomDocument> expectedType) {
		assert resource != null && expectedType != null;
		this.resource = resource;
		this.expectedType = expectedType;
	}

	@BeforeClass
	public static void createParser() throws Exception {
		parser = new AtomDocumentParser();
	}

	@Test
	public void testParseAtomDocument() throws Exception {
		AtomDocument result = parser.parse(getClass().getResource(this.resource));
		assertNotNull(result);
		assertTrue(this.expectedType.isAssignableFrom(result.getClass()));
	}

}
