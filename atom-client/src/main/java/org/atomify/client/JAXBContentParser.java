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
package org.atomify.client;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;
import javax.xml.bind.util.JAXBResult;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import org.jbasics.enviroment.JVMEnviroment;

import org.atomify.model.syndication.AtomContent;
import org.atomify.model.syndication.AtomContentGenericXml;
import org.atomify.model.syndication.AtomEntry;

public class JAXBContentParser {

	public static <T> T parseContent(final AtomEntry entry, final Class<T> type) {
		return JAXBContentParser.parseContent(entry, type, null);
	}

	public static <T> T parseContent(final AtomEntry entry, final Class<T> type, final Templates template) {
		final AtomContent content = entry.getContent();
		if (content.isXML()) {
			try {
				final JAXBContext ctx = JAXBContext.newInstance(type);
				ContentHandler handler;
				if (template != null) {
					final Transformer t = template.newTransformer();
					JAXBResult result = new JAXBResult(ctx);
					SAXSource source = new SAXSource();
					handler = source.getXMLReader().getContentHandler();
				} else {
					final Unmarshaller um = ctx.createUnmarshaller();
					handler = um.getUnmarshallerHandler();
				}
				final AtomContentGenericXml xml = (AtomContentGenericXml) content;
				handler.startDocument();
				xml.getXmlContent().serialize(handler, new AttributesImpl());
				handler.endDocument();
				return type.cast(handler.getResult());
				
				
				Templates partnerTemplate = TransformerFactory.newInstance().newTemplates(
						new StreamSource(JVMEnviroment.getNotNullResource("/xslts/partner.xslt").openStream()));
				
				
			} catch (final SAXException e) {
				throw JAXBContentParser.createRuntimeException(e);
			} catch (final JAXBException e) {
				throw JAXBContentParser.createRuntimeException(e);
			} catch (final TransformerConfigurationException e) {
				throw JAXBContentParser.createRuntimeException(e);
			}
		}
		throw new IllegalArgumentException("Entry does not have any xml content");
	}

	private static RuntimeException createRuntimeException(final Throwable e) {
		final RuntimeException er = new RuntimeException("[" + e.getClass().getSimpleName() + "] " + e.getMessage());
		er.setStackTrace(e.getStackTrace());
		er.initCause(e);
		return er;
	}

}
