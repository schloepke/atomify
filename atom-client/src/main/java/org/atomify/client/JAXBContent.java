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
import javax.xml.bind.Marshaller;

import org.atomify.model.AtomConstants;
import org.atomify.model.syndication.AtomContentXml;
import org.jbasics.checker.ContractCheck;
import org.jbasics.net.mediatype.MediaType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class JAXBContent extends AtomContentXml {
	private Object element;
	private JAXBContext ctx;

	public JAXBContent(Object element) {
		this(null, element);
	}

	public JAXBContent(JAXBContext ctx, Object element) {
		super(MediaType.APPLICATION_XML_TYPE);
		this.element = ContractCheck.mustNotBeNull(element, "element");
		if (ctx == null) {
			try {
				this.ctx = JAXBContext.newInstance(this.element.getClass());
			} catch (JAXBException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		} else {
			this.ctx = ctx;
		}
	}

	@Override
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		addAttribute(attributes, TYPE_QNAME, getMediaType().toString());
		String namespace = AtomConstants.ATOM_NS_URI;
		String local = "content";
		String qName = AtomConstants.ATOM_NS_PREFIX + ":" + local;
		handler.startElement(namespace, local, qName, attributes);
		try {
			Marshaller m = this.ctx.createMarshaller();
			m.marshal(this.element, handler);
		} catch (JAXBException e) {
			throw new SAXException(e);
		}
		handler.endElement(namespace, local, qName);
	}

}
