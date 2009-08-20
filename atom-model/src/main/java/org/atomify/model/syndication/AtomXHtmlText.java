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
package org.atomify.model.syndication;

import javax.xml.namespace.QName;

import org.atomify.model.AtomContractConstraint;
import org.atomify.model.common.XhtmlDivElement;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Atom XHTML text construct.
 * <p>
 * The content consist of one XHTML:DIV element
 * </p>
 * 
 * @author Stephan Schloepke
 */
public class AtomXHtmlText extends AtomText {
	/**
	 * <b>Required:</b> xhtml:div element which is not part of the content.
	 * <p>
	 * TODO: Refactor to have the xml:div element only be rendered and not part of the model.
	 * </p>
	 */
	private final XhtmlDivElement content;

	/**
	 * Creates an XHTML text construct with the given xhtml:div element instance.
	 * 
	 * @param xhtmlDivElement The xhtml:div element
	 * @todo TODO: Need to refactor the element from {@link AbstractAtomExtension} to a real
	 *       xhtml:div element.
	 */
	public AtomXHtmlText(final XhtmlDivElement xhtmlDivElement) {
		this.content = AtomContractConstraint.notNull("xhtmlDivElement", xhtmlDivElement);
	}

	/**
	 * Returns the xhtml:div element.
	 * 
	 * @return The xhtml:div element.
	 */
	public XhtmlDivElement getContent() {
		return this.content;
	}

	@Override
	public Type getType() {
		return Type.XHTML;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.content == null) ? 0 : this.content.hashCode());
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
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof AtomXHtmlText)) {
			return false;
		}
		AtomXHtmlText other = (AtomXHtmlText) obj;
		if (this.content == null) {
			if (other.content != null) {
				return false;
			}
		} else if (!this.content.equals(other.content)) {
			return false;
		}
		return true;
	}

	// FIXME: Write a much better way of serialization

	public void serialize(QName name, ContentHandler handler, AttributesImpl attributes) throws SAXException {
		attributes = initCommonAttributes(attributes);
		addAttribute(attributes, TYPE_QNAME, getType().toXmlString());
		String namespace = name.getNamespaceURI();
		String local = name.getLocalPart();
		String qName = (name.getPrefix() != null && name.getPrefix().length() > 0 ? name.getPrefix() + ":" : "") + local;
		handler.startElement(namespace, local, qName, attributes);
		this.content.serialize(handler, attributes);
		handler.endElement(namespace, local, qName);
	}

}
