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
package org.atomify.model.extension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.atomify.model.AtomContractConstraint;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * A simple extension has no attributes and only a text content.
 * 
 * @author Stephan Schloepke
 */
public class AtomSimpleExtension implements AtomExtension {
	/**
	 * <b>Required:</b> The fully qualified name of the extension element. Must not be the atom name
	 * space.
	 */
	private final QName extensionName;

	/**
	 * <b>Required:</b> text content of the simple extension.
	 */
	private final String value;

	/**
	 * Creates a simple extension with the name and content.
	 * 
	 * @param extensionName The name of the extension (must not be null).
	 * @param value The content of the extension.
	 */
	public AtomSimpleExtension(final QName extensionName, final String value) {
		this.extensionName = AtomContractConstraint.notNull("extensionName", extensionName);
		this.value = value;
	}

	/**
	 * Returns the fully qualified name of the extension.
	 * 
	 * @return The fully qualified name.
	 */
	public QName getQualifiedName() {
		return this.extensionName;
	}

	public boolean isSimpleContent() {
		return false;
	}

	public String getSimpleContent() {
		return this.value;
	}

	public Map<QName, String> getAttributes() {
		return Collections.emptyMap();
	}

	public List<AtomForeignMarkup> getComplexContent() {
		return Collections.emptyList();
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.extensionName.hashCode();
		result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof AtomSimpleExtension)) {
			return false;
		}
		AtomSimpleExtension other = (AtomSimpleExtension) obj;
		if (!this.extensionName.equals(other.extensionName)) {
			return false;
		}
		if (this.value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!this.value.equals(other.value)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder().append("AtomSimpleExtension [extensionName=").append(this.extensionName).append(
				", value=").append(this.value).append("]").toString();
	}

	// --- FIXME: From here all is serialization. We Still need to think about a good way to do so.

	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		if (attributes == null) {
			attributes = new AttributesImpl();
		} else {
			attributes.clear();
		}
		String namespace = this.extensionName.getNamespaceURI();
		String local = this.extensionName.getLocalPart();
		String qName = (this.extensionName.getPrefix() != null && this.extensionName.getPrefix().length() > 0 ? this.extensionName.getPrefix() + ":" : "") + local;
		handler.startElement(namespace, local, qName, attributes);
		char[] data = this.value.toCharArray();
		handler.characters(data, 0, data.length);
		handler.endElement(namespace, local, qName);
	}

}
