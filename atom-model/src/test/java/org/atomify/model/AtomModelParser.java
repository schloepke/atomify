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

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jbasics.parser.BuilderContentHandler;
import org.junit.Before;
import org.junit.Test;

public class AtomModelParser {
	private SAXParser parser;
	
	
	@Before
	public void createSaxParser() throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		this.parser = factory.newSAXParser();
	}
	
	@Test
	public void testAtomServiceDocumentParsing() throws Exception {
		BuilderContentHandler<AtomDocument> handler = new BuilderContentHandler<AtomDocument>(AtomDocument.class);
		this.parser.parse(getClass().getResource("publishing/atom-service-document.xml").toString(), handler);
		AtomDocument result = handler.getParsingResult();
		System.out.println(result);
	}

	@Test
	public void testAtomCategoriesDocumentParsing() throws Exception {
		BuilderContentHandler<AtomDocument> handler = new BuilderContentHandler<AtomDocument>(AtomDocument.class);
		this.parser.parse(getClass().getResource("publishing/atom-categories-document.xml").toString(), handler);
		AtomDocument result = handler.getParsingResult();
		System.out.println(result);
	}
	@Test
	public void testAtomFeedDocumentParsing() throws Exception {
		BuilderContentHandler<AtomDocument> handler = new BuilderContentHandler<AtomDocument>(AtomDocument.class);
		this.parser.parse(getClass().getResource("syndication/atom-feed-document.xml").toString(), handler);
		AtomDocument result = handler.getParsingResult();
		System.out.println(result);
	}

}
