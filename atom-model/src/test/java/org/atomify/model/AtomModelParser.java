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

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jbasics.parser.AnnotationScanner;
import org.jbasics.parser.BuilderContentHandler;
import org.jbasics.parser.ParsingInfo;
import org.junit.Test;

public class AtomModelParser {

	@Test
	public void testAnnotationScanner() {
		AnnotationScanner scanner = new AnnotationScanner();
		Map<QName, ParsingInfo> result = scanner.scan(AtomDocument.class);
		System.out.println(result);
	}

	@Test
	public void testAtomModelParsing() throws Exception {

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		SAXParser parser = factory.newSAXParser();

		BuilderContentHandler<AtomDocument> handler = new BuilderContentHandler<AtomDocument>(AtomDocument.class);
		parser.parse(getClass().getResource("publishing/atom-service-document.xml").toString(), handler);

		AtomDocument result = handler.getParsingResult();
		System.out.println(result);
	}

}
