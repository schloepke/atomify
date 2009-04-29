/**
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

import org.atomify.model.AtomContractConstraint;
import org.w3c.dom.Node;

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
	 * TODO: Refactor to have the xml:div element only be rendered and not part
	 * of the model.
	 * </p>
	 */
	private Node content;

	/**
	 * Creates an XHTML text construct with the given xhtml:div element
	 * instance.
	 * 
	 * @param xhtmlDivElement
	 *            The xhtml:div element
	 * @todo TODO: Need to refactor the element from {@link AnyElement} to a
	 *       real xhtml:div element.
	 */
	public AtomXHtmlText(final Node xhtmlDivElement) {
		this.content = AtomContractConstraint.notNull("xhtmlDivElement",
				xhtmlDivElement);
	}

	/**
	 * Set the xhtml:div element.
	 * 
	 * @param content
	 *            the content to set
	 */
	public void setContent(final Node content) {
		this.content = AtomContractConstraint.notNull("content", content);
	}

	/**
	 * Returns the xhtml:div element.
	 * 
	 * @return The xhtml:div element.
	 */
	public Node getContent() {
		return this.content;
	}

	@Override
	public Type getType() {
		return Type.xhtml;
	}

}
