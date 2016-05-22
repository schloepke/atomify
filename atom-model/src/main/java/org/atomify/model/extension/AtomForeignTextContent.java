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
package org.atomify.model.extension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.atomify.model.AtomContractConstraint;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class AtomForeignTextContent implements AtomForeignMarkup {
	private final String text;

	public AtomForeignTextContent(String text) {
		this.text = AtomContractConstraint.notNull("text", text);
	}

	public Map<QName, String> getAttributes() {
		return Collections.emptyMap();
	}

	public List<AtomForeignMarkup> getComplexContent() {
		return Collections.emptyList();
	}

	public QName getQualifiedName() {
		return null;
	}

	public boolean isSimpleContent() {
		return true;
	}

	public String getSimpleContent() {
		return this.text;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder("AtomForeignTextContent [text=").append(this.text).append("]").toString();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.text.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || !(obj instanceof AtomForeignTextContent)) {
			return false;
		}
		return this.text.equals(((AtomForeignTextContent) obj).text);
	}

	// --- FIXME: From here all is serialization. We Still need to think about a good way to do so.

	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		char[] temp = this.text.toCharArray();
		handler.characters(temp, 0, temp.length);
	}

}
