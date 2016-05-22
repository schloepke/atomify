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

import java.net.URI;

import javax.xml.namespace.QName;

import org.atomify.model.AtomConstants;
import org.atomify.model.AtomContractConstraint;
import org.jbasics.net.mediatype.MediaType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class AtomContentLink extends AtomContent {
	private final URI source;
	private final MediaType mediaType;

	public AtomContentLink(URI source, MediaType mediatType) {
		this.source = AtomContractConstraint.notNull("source", source);
		this.mediaType = mediatType;
	}

	@Override
	public boolean isMediaType(MediaType type) {
		return this.mediaType != null ? this.mediaType.isMediaTypeMatching(type) : false;
	}

	public URI getSource() {
		return this.source;
	}

	public MediaType getMediaType() {
		return this.mediaType;
	}

	public String getType() {
		return this.mediaType == null ? null : this.mediaType.toString();
	}

	// FIXME: Write a much better way of serialization

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		addAttribute(attributes, SRC_QNAME, this.source.toString());
		if (this.mediaType != null) {
			addAttribute(attributes, TYPE_QNAME, this.mediaType.toString());
		}
		String namespace = AtomConstants.ATOM_NS_URI;
		String local = "content";
		String qName = AtomConstants.ATOM_NS_PREFIX + ":" + local;
		handler.startElement(namespace, local, qName, attributes);
		handler.endElement(namespace, local, qName);
	}

	protected final static QName SRC_QNAME = new QName("src");
	
}
