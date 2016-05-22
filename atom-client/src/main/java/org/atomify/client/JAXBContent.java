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
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import org.jbasics.checker.ContractCheck;
import org.jbasics.net.mediatype.MediaType;
import org.jbasics.utilities.DataUtilities;

import org.atomify.model.AtomConstants;
import org.atomify.model.common.AtomCommonAttributes;
import org.atomify.model.syndication.AtomContent;
import org.atomify.model.syndication.AtomContentXml;

public class JAXBContent extends AtomContentXml {
	private final Object element;
	private JAXBContext ctx;

	public JAXBContent(final Object element) {
		this(null, element, null);
	}

	public JAXBContent(final Object element, final MediaType mediaType) {
		this(null, element, mediaType);
	}

	public JAXBContent(final JAXBContext ctx, final Object element) {
		this(ctx, element, null);
	}

	public JAXBContent(final JAXBContext ctx, final Object element, final MediaType mediaType) {
		super(DataUtilities.coalesce(mediaType, MediaType.APPLICATION_XML_TYPE));
		this.element = ContractCheck.mustNotBeNull(element, "element");
		if (ctx == null) {
			try {
				if (element instanceof JAXBElement<?>) {
					this.ctx = JAXBContext.newInstance(((JAXBElement<?>) element).getDeclaredType());
				} else {
					this.ctx = JAXBContext.newInstance(this.element.getClass());
				}
			} catch (JAXBException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		} else {
			this.ctx = ctx;
		}
	}

	@Override
	public void serialize(final ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		AtomCommonAttributes.addAttribute(attributes, AtomContent.TYPE_QNAME, getMediaType().toString());
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
