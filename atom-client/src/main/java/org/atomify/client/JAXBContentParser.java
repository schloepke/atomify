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
package org.atomify.client;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;

import org.atomify.model.syndication.AtomContent;
import org.atomify.model.syndication.AtomContentGenericXml;
import org.atomify.model.syndication.AtomEntry;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class JAXBContentParser {

	public static <T> T parseContent(AtomEntry entry, Class<T> type) {
		AtomContent content = entry.getContent();
		if (content.isXML()) {
			try {
				AtomContentGenericXml xml = (AtomContentGenericXml) content;
				JAXBContext ctx = JAXBContext.newInstance(type);
				Unmarshaller um = ctx.createUnmarshaller();
				UnmarshallerHandler handler = um.getUnmarshallerHandler();
				handler.startDocument();
				xml.getXmlContent().serialize(handler, new AttributesImpl());
				handler.endDocument();
				return type.cast(handler.getResult());
			} catch (SAXException e) {
				throw createRuntimeException(e);
			} catch (JAXBException e) {
				throw createRuntimeException(e);
			}
		}
		throw new IllegalArgumentException("Entry does not have any xml content");
	}

	private static RuntimeException createRuntimeException(Throwable e) {
		RuntimeException er = new RuntimeException("[" + e.getClass().getSimpleName() + "] " + e.getMessage());
		er.setStackTrace(e.getStackTrace());
		er.initCause(e);
		return er;
	}

}
