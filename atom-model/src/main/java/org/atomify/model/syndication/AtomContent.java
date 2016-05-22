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

import javax.xml.namespace.QName;

import org.atomify.model.common.AtomCommonAttributes;
import org.jbasics.net.mediatype.MediaType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Listenbeschreibung
 * <p>
 * Detailierte Beschreibung
 * </p>
 * 
 * @author stephan
 */
public abstract class AtomContent extends AtomCommonAttributes {

	public static AtomContentBuilder newBuilder() {
		return AtomContentBuilder.newInstance();
	}

	public boolean isHtml() {
		return false;
	}

	public boolean isText() {
		return false;
	}

	public boolean isXHtml() {
		return false;
	}

	public boolean isMediaType(final MediaType type) {
		return false;
	}
	
	public boolean isBinary() {
		return false;
	}
	
	public boolean isXML() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || !(obj instanceof AtomContent)) {
			return false;
		}
		return true;
	}

	// FIXME: Write a much better way of serialization

	public abstract void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException;

	protected final static QName TYPE_QNAME = new QName("type");

}
