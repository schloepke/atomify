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
package org.atomify.model.publishing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.atomify.model.AtomConstants;
import org.atomify.model.extension.AtomExtension;
import org.jbasics.xml.types.XmlBooleanYesNoType;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class AtomPubControl implements AtomExtension {
	public static final QName EXTENSION_QNAME = new QName(AtomConstants.ATOM_PUB_NS_URI, "control");
	private final XmlBooleanYesNoType draft;
	private final List<AtomExtension> extensions;

	public AtomPubControl(XmlBooleanYesNoType draft, List<AtomExtension> extensions) {
		this.draft = draft;
		if (extensions == null || extensions.isEmpty()) {
			this.extensions = Collections.emptyList();
		} else {
			this.extensions = Collections.unmodifiableList(new ArrayList<AtomExtension>(extensions));
		}
	}

	public XmlBooleanYesNoType getDraft() {
		return this.draft;
	}

	public boolean isDraft() {
		return XmlBooleanYesNoType.YES == this.draft;
	}

	public QName getQualifiedName() {
		return EXTENSION_QNAME;
	}

	public boolean isSimpleContent() {
		return false;
	}

	public String getSimpleContent() {
		return null;
	}

	public Map<QName, String> getAttributes() {
		return Collections.emptyMap();
	}

	public List<AtomExtension> getComplexContent() {
		return this.extensions;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.draft == null) ? 0 : this.draft.hashCode());
		result = prime * result + ((this.extensions == null) ? 0 : this.extensions.hashCode());
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
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AtomPubControl)) {
			return false;
		}
		AtomPubControl other = (AtomPubControl) obj;
		if (this.draft == null) {
			if (other.draft != null) {
				return false;
			}
		} else if (!this.draft.equals(other.draft)) {
			return false;
		}
		if (this.extensions == null) {
			if (other.extensions != null) {
				return false;
			}
		} else if (!this.extensions.equals(other.extensions)) {
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
		StringBuilder builder = new StringBuilder();
		builder.append("AtomPubControl [draft=").append(this.draft).append(", extensions=").append(this.extensions).append("]");
		return builder.toString();
	}

	// --- FIXME: From here all is serialization. We Still need to think about a good way to do so.

	@SuppressWarnings("all")
	public void serialize(ContentHandler handler, AttributesImpl attributes) throws SAXException {
		if (attributes == null) {
			attributes = new AttributesImpl();
		} else {
			attributes.clear();
		}
		QName name = EXTENSION_QNAME;
		String namespace = name.getNamespaceURI();
		String local = name.getLocalPart();
		String qName = (name.getPrefix() != null && name.getPrefix().length() > 0 ? name.getPrefix() + ":" : "") + local;
		handler.startElement(namespace, local, qName, attributes);
		if (this.draft != null) {
			handler.startElement(AtomConstants.ATOM_PUB_NS_URI, "draft", "app:draft", attributes);
			char[] temp = this.draft.toXmlString().toCharArray();
			handler.characters(temp, 0, temp.length);
		}
		for (AtomExtension child : this.extensions) {
			child.serialize(handler, attributes);
		}
		handler.endElement(namespace, local, qName);
	}

}
