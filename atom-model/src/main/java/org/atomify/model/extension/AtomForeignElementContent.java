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

public class AtomForeignElementContent implements AtomForeignMarkup {
	private final QName qualifiedName;
	private final Map<QName, String> attributes;
	private final List<AtomForeignMarkup> childrean;

	public AtomForeignElementContent(QName name, Map<QName, String> attributes, List<AtomForeignMarkup> childrean) {
		this.qualifiedName = AtomContractConstraint.notNull("name", name);
		if (attributes != null && attributes.size() > 0) {
			this.attributes = Collections.unmodifiableMap(new HashMap<QName, String>(attributes));
		} else {
			this.attributes = Collections.emptyMap();
		}
		if (childrean != null && childrean.size() > 0) {
			this.childrean = Collections.unmodifiableList(new ArrayList<AtomForeignMarkup>(childrean));
		} else {
			this.childrean = Collections.emptyList();
		}
	}

	public Map<QName, String> getAttributes() {
		return this.attributes;
	}

	public List<AtomForeignMarkup> getComplexContent() {
		return this.childrean;
	}

	public QName getQualifiedName() {
		return this.qualifiedName;
	}

	public String getSimpleContent() {
		return null;
	}

	public boolean isSimpleContent() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.qualifiedName.hashCode();
		result = prime * result + this.attributes.hashCode();
		result = prime * result + this.childrean.hashCode();
		return result;
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
		if (obj == null || !(obj instanceof AtomForeignElementContent)) {
			return false;
		}
		AtomForeignElementContent other = (AtomForeignElementContent) obj;
		return this.qualifiedName.equals(other.qualifiedName) && this.attributes.equals(other.attributes) && this.childrean.equals(other.childrean);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder().append("AtomForeignElementContent [qualifiedName=").append(this.qualifiedName).append(", attributes=").append(
				this.attributes).append(", childrean=").append(this.childrean).append("]").toString();
	}

	// --- FIXME: From here all is serialization. We Still need to think about a good way to do so.

	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		if (attributes == null) {
			attributes = new AttributesImpl();
		} else {
			attributes.clear();
		}
		for (Map.Entry<QName, String> attr : this.attributes.entrySet()) {
			QName name = attr.getKey();
			attributes.addAttribute(name.getNamespaceURI(), name.getLocalPart(), (name.getPrefix() != null && name.getPrefix().length() > 0 ? name
					.getPrefix()
					+ ":" : "")
					+ name.getLocalPart(), "CDATA", attr.getValue().toString());
		}
		QName name = this.qualifiedName;
		String namespace = name.getNamespaceURI();
		String local = name.getLocalPart();
		String qName = (name.getPrefix() != null && name.getPrefix().length() > 0 ? name.getPrefix() + ":" : "") + local;
		handler.startElement(namespace, local, qName, attributes);
		for (AtomForeignMarkup child : this.childrean) {
			child.serialize(handler, attributes);
		}
		handler.endElement(namespace, local, qName);
	}

}
