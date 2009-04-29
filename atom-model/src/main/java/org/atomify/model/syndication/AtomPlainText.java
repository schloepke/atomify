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

/**
 * The Atom plain text construct.
 * <p>
 * The atom plain text construct is defined in section <a href="http://www.atompub.org/rfc4287.html#text.constructs">3.1 of the atom 1.0
 * specification</a> <blockquote> A Text construct contains human-readable text, usually in small quantities. The content of Text constructs is
 * Language-Sensitive. </blockquote> This instance covers only the plain text construct which is derived from the {@link AtomText} which represents
 * all atom text constructs. <blockquote>
 * 
 * <pre>
 * atomPlainTextConstruct =
 * 	atomCommonAttributes,
 * 	attribute type { &quot;text&quot; | &quot;html&quot; }?,
 * 	text
 * </pre>
 * 
 * </blockquote>
 * </p>
 * 
 * @author Stephan Schloepke
 */
public class AtomPlainText extends AtomText {
	/**
	 * <b>Optional:</b> type attribute true means type <em>html</em> false means <em>text</em>. If not specified <em>text</em> is default.
	 */
	private boolean html;
	/**
	 * <b>Required:</b> The text content cannot be empty.
	 */
	private String value;

	/**
	 * Create an empty plain text construct.
	 */
	public AtomPlainText() {
		this(false, null);
	}

	/**
	 * Create a plain text construct with the given content. The construct is of type text.
	 * 
	 * @param content The text content (any markup will be escaped)
	 */
	public AtomPlainText(final String content) {
		this(false, content);
	}

	/**
	 * Create a plain text construct with the given content. The construct is of type text or html.
	 * 
	 * @param html True if the content type should be html (still will be escaped).
	 * @param content The text content (any markup will be escaped)
	 */
	public AtomPlainText(final boolean html, final String content) {
		this.html = html;
		this.value = AtomContractConstraint.notNull("content", content);
	}

	/**
	 * Set if the content is considered HTML content rather than plain text.
	 * 
	 * @param html True to set the content type to HTML.
	 */
	public void setHtml(final boolean html) {
		this.html = html;
	}

	/**
	 * Set the text content (any mark up will be escaped on serializing).
	 * 
	 * @param value The content to set.
	 */
	public void setValue(final String value) {
		this.value = AtomContractConstraint.notNull("value", value);
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
	public Type getType() {
		return this.html ? Type.html : Type.text;
	}

}
