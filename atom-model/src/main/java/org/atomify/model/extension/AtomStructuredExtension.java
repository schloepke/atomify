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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.atomify.model.AtomContractConstraint;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * A complex and structured atom extension. Must not be of the atom name space. Must at least
 * contain one attribute or any sub element.
 * 
 * @author Stephan Schloepke
 */
public class AtomStructuredExtension implements AtomExtension {
	/**
	 * <b>Required:</b> The fully qualified name of the extension element. Must not be the atom name
	 * space.
	 */
	private final QName extensionName;
	/**
	 * <b>Optional:</b> The attributes of the extension element.
	 */
	private final Map<QName, String> attributes;
	/**
	 * <b>Optional:</b> the mixed content.
	 */
	private final List<AtomForeignMarkup> childrean;

	/**
	 * Creates a structure extension with the given name.
	 * 
	 * @param extensionName The name of the extension (must not be null).
	 */
	public AtomStructuredExtension(final QName extensionName, Map<QName, String> attributes,
			List<AtomForeignMarkup> chlidrean) {
		this.extensionName = AtomContractConstraint.notNull("extensionName", extensionName);
		if (attributes == null || attributes.isEmpty()) {
			this.attributes = Collections.emptyMap();
		} else {
			this.attributes = Collections.unmodifiableMap(new HashMap<QName, String>(attributes));
		}
		if (chlidrean == null || chlidrean.isEmpty()) {
			this.childrean = Collections.emptyList();
		} else {
			this.childrean = Collections.unmodifiableList(new ArrayList<AtomForeignMarkup>(chlidrean));
		}
	}

	public QName getQualifiedName() {
		return this.extensionName;
	}

	public boolean isSimpleContent() {
		return false;
	}

	public String getSimpleContent() {
		return null;
	}

	public Map<QName, String> getAttributes() {
		return this.attributes;
	}

	public List<AtomForeignMarkup> getComplexContent() {
		return this.childrean;
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.attributes == null) ? 0 : this.attributes.hashCode());
		result = prime * result + ((this.childrean == null) ? 0 : this.childrean.hashCode());
		result = prime * result + ((this.extensionName == null) ? 0 : this.extensionName.hashCode());
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
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AtomStructuredExtension)) {
			return false;
		}
		AtomStructuredExtension other = (AtomStructuredExtension) obj;
		if (this.attributes == null) {
			if (other.attributes != null) {
				return false;
			}
		} else if (!this.attributes.equals(other.attributes)) {
			return false;
		}
		if (this.childrean == null) {
			if (other.childrean != null) {
				return false;
			}
		} else if (!this.childrean.equals(other.childrean)) {
			return false;
		}
		if (this.extensionName == null) {
			if (other.extensionName != null) {
				return false;
			}
		} else if (!this.extensionName.equals(other.extensionName)) {
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
		return new StringBuilder().append("AtomStructuredExtension [extensionName=").append(this.extensionName).append(
				", attributes=").append(this.attributes).append(", chlidrean=").append(this.childrean).append("]")
				.toString();
	}

	// --- FIXME: From here all is serialization. We Still need to think about a good way to do so.

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		if (attributes == null) {
			attributes = new AttributesImpl();
		} else {
			attributes.clear();
		}
		for (Map.Entry<QName, String> attr : this.attributes.entrySet()) {
			QName name = attr.getKey();
			attributes.addAttribute(name.getNamespaceURI(), name.getLocalPart(), (name.getPrefix() != null
					&& name.getPrefix().length() > 0 ? name.getPrefix() + ":" : "")
					+ name.getLocalPart(), "CDATA", attr.getValue().toString());
		}
		QName name = this.extensionName;
		String namespace = name.getNamespaceURI();
		String local = name.getLocalPart();
		String qName = (name.getPrefix() != null && name.getPrefix().length() > 0 ? name
				.getPrefix()
				+ ":"
				: "")
				+ local;
		handler.startElement(namespace, local, qName, attributes);
		for(AtomForeignMarkup child : this.childrean) {
			child.serialize(handler, attributes);
		}
		handler.endElement(namespace, local, qName);
	}

}
