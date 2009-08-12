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

import org.atomify.model.AtomCommonAttributes;

/**
 * This is the atomTextConstruct. It is the abstract base class of {@link AtomPlainText} and
 * {@link AtomXHtmlText}.
 * 
 * @author Stephan Schloepke
 */
public abstract class AtomText extends AtomCommonAttributes {

	/**
	 * ENUM for the different types of text atom understands.
	 * <p>
	 * All ENUM values are lower case to reflect the exact text representation of the attribute in
	 * XML. This might change in the future.
	 * </p>
	 * 
	 * @author Stephan Schloepke
	 */
	public static enum Type {
		/**
		 * The plain text type.
		 */
		text,
		/**
		 * The HTML text type.
		 */
		html,
		/**
		 * The XHTML mixed object type.
		 */
		xhtml
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
		return Type.text == getType();
	}

	/**
	 * Returns true if the text construct is a HTML text construct.
	 * 
	 * @return True if the content is HTML.
	 */
	public boolean isHtmlType() {
		return Type.html == getType();
	}

	/**
	 * Returns true if the text construct is a plain XHTML content tree.
	 * 
	 * @return True if the content is a XHTML content tree.
	 */
	public boolean isXHtmlType() {
		return Type.xhtml == getType();
	}

}
