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

import org.atomify.model.AtomConstants;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class AtomContentPlainText extends AtomContent {
	/**
	 * <b>Optional:</b> type attribute true means type <em>html</em> false means <em>text</em>. If
	 * not specified <em>text</em> is default.
	 */
	private final boolean html;
	/**
	 * <b>Required:</b> The text content cannot be empty.
	 */
	private final String value;

	/**
	 * Create an empty plain text construct.
	 */
	public AtomContentPlainText() {
		this(false, null);
	}

	/**
	 * Create a plain text construct with the given content. The construct is of type text.
	 * 
	 * @param content The text content (any markup will be escaped)
	 */
	public AtomContentPlainText(final String content) {
		this(false, content);
	}

	/**
	 * Create a plain text construct with the given content. The construct is of type text or html.
	 * 
	 * @param html True if the content type should be html (still will be escaped).
	 * @param content The text content (any markup will be escaped)
	 */
	public AtomContentPlainText(final boolean html, final String content) {
		this.html = html;
		this.value = content;
	}

	/**
	 * Returns the text content.
	 * 
	 * @return The text content.
	 */
	public String getValue() {
		return this.value;
	}

	@Override
	public boolean isHtml() {
		return this.html;
	}

	@Override
	public boolean isText() {
		return !this.html;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.html ? 1231 : 1237);
		result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (!(obj instanceof AtomContentPlainText)) return false;
		AtomContentPlainText other = (AtomContentPlainText) obj;
		if (this.html != other.html) return false;
		if (this.value == null) {
			if (other.value != null) return false;
		} else if (!this.value.equals(other.value)) return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder().append("AtomContentPlainText [html=").append(this.html).append(", value=").append(this.value).append(", ").append(
				super.toString()).append("]").toString();
	}

	// FIXME: Write a much better way of serialization

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		addAttribute(attributes, TYPE_QNAME, this.html ? "html" : "text");
		String namespace = AtomConstants.ATOM_NS_URI;
		String local = "content";
		String qName = AtomConstants.ATOM_NS_PREFIX + ":" + local;
		handler.startElement(namespace, local, qName, attributes);
		char[] data = this.value.toCharArray();
		handler.characters(data, 0, data.length);
		handler.endElement(namespace, local, qName);
	}

}
