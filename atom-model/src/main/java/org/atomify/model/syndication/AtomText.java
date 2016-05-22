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
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * This is the atomTextConstruct. It is the abstract base class of {@link AtomPlainText} and
 * {@link AtomXHtmlText}.
 * 
 * @author Stephan Schloepke
 */
public abstract class AtomText extends AtomCommonAttributes {

	/**
	 * ENUM for the different types of text atom understands.
	 * 
	 * @author Stephan Schloepke
	 */
	public static enum Type {
		/**
		 * The plain text type.
		 */
		TEXT("text"),
		/**
		 * The HTML text type.
		 */
		HTML("html"),
		/**
		 * The XHTML mixed object type.
		 */
		XHTML("xhtml");

		private String xmlRepresentation;

		private Type(String xmlRepresentation) {
			assert xmlRepresentation != null;
			this.xmlRepresentation = xmlRepresentation;
		}

		public String toXmlString() {
			return this.xmlRepresentation;
		}

		public static Type xmlValueOf(String xmlRepresentation) {
			if (xmlRepresentation == null) {
				throw new IllegalArgumentException("Null parameter: xmlRepresentation");
			}
			if (TEXT.xmlRepresentation.equalsIgnoreCase(xmlRepresentation)) {
				return TEXT;
			} else if (HTML.xmlRepresentation.equalsIgnoreCase(xmlRepresentation)) {
				return HTML;
			} else if (XHTML.xmlRepresentation.equalsIgnoreCase(xmlRepresentation)) {
				return XHTML;
			} else {
				throw new IllegalArgumentException("Not a valid AtomText.Type xml representation constant "
						+ xmlRepresentation);
			}
		}

	}

	/**
	 * Returns an {@link AtomTextBuilder} to build atom text constructs.
	 * 
	 * @return The empty {@link AtomTextBuilder}
	 */
	public static AtomTextBuilder newBuilder() {
		return AtomTextBuilder.newInstance();
	}

	/**
	 * Returns the type of the {@link AtomText}. This is implemented by the derived text construct.
	 * 
	 * @return The type of the concrete implementation.
	 */
	public abstract Type getType();

	/**
	 * Returns true if the text construct is a plain text construct with non mark up content.
	 * 
	 * @return True if the content is plain text.
	 */
	public boolean isTextType() {
		return Type.TEXT == getType();
	}

	/**
	 * Returns true if the text construct is a HTML text construct.
	 * 
	 * @return True if the content is HTML.
	 */
	public boolean isHtmlType() {
		return Type.HTML == getType();
	}

	/**
	 * Returns true if the text construct is a plain XHTML content tree.
	 * 
	 * @return True if the content is a XHTML content tree.
	 */
	public boolean isXHtmlType() {
		return Type.XHTML == getType();
	}
	
	// FIXME: Write a much better way of serialization
	
	public abstract void serialize(QName name, ContentHandler handler, AttributesImpl attributes) throws SAXException;
	protected final static QName TYPE_QNAME = new QName("type");

}
